package zefryuuko.chat.client.gui;

import zefryuuko.chat.client.ClientMain;
import zefryuuko.chat.client.Client;
import zefryuuko.chat.commdata.InitHandshakeData;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * A panel used for authentication.
 * Shown on app startup
 */
public class ConnectPanel extends JPanel
{
    private GridBagConstraints c = new GridBagConstraints();
    private JPanel pnlConnectionInfo = new JPanel();
    private JLabel lblServerAddress = new JLabel();
    private JLabel lblServerPassword = new JLabel();
    private JLabel lblUsername = new JLabel();
    private JTextField txtServerAddress = new JTextField();
    private JPasswordField passServerPassword = new JPasswordField();
    private JTextField txtUsername = new JTextField();
    private JButton btnAddToList = new JButton();
    private JButton btnRemoveFromList = new JButton();
    private JButton btnConnect = new JButton();
    private JPanel pnlSavedLogin = new JPanel();
    private JLabel lblSavedLogin = new JLabel();
    private JScrollPane spaneSavedLogin = new JScrollPane();
    private JTable tblSavedLogin = new JTable();
    private String[] tblSavedLoginColumns = new String[] {"Host", "Username"};
    private String[][] savedLoginData;
    private String[][] savedLoginDataTbl;
    private int connectionStatus = 0;

    public ConnectPanel()
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;
        this.setBackground(new Color(54, 57, 62));

        // Object properties
        pnlConnectionInfo.setSize(new Dimension(500, 300));
        pnlConnectionInfo.setLayout(new GridBagLayout());
        pnlConnectionInfo.setBackground(new Color(44, 47, 51));
        lblServerAddress.setText("HOST                                                     ");
        lblServerAddress.setForeground(Color.WHITE);
        lblServerAddress.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        lblServerPassword.setText("PASSWORD                                         ");
        lblServerPassword.setForeground(Color.WHITE);
        lblServerPassword.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        lblUsername.setText("USERNAME                                         ");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        txtServerAddress.setPreferredSize(new Dimension(200, 35));
        passServerPassword.setPreferredSize(new Dimension(200, 35));
        txtUsername.setPreferredSize(new Dimension(200, 35));
        btnConnect.setText("Connect");
        btnConnect.setPreferredSize(new Dimension(200, 35));
        btnConnect.addActionListener(new btnConnectActionlistener());
        btnAddToList.setText("Save to list");
        btnAddToList.addActionListener(e -> saveLoginInfo());
        btnRemoveFromList.setText("Remove from list");
        btnRemoveFromList.addActionListener(e -> removeLoginInfo());
        btnRemoveFromList.setEnabled(false);
        pnlSavedLogin.setPreferredSize(new Dimension(400, 100));
        pnlSavedLogin.setLayout(new GridBagLayout());
        pnlSavedLogin.setBackground(new Color(44, 47, 51));
        lblSavedLogin.setText("SAVED LOGINS                                                                                                      ");
        lblSavedLogin.setForeground(Color.WHITE);
        lblSavedLogin.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        spaneSavedLogin.getViewport().setBackground(new Color(44, 47, 51));
        spaneSavedLogin.setBorder(null);
        generateTable();


        // Panel objects
        // -- Login information
        c.insets = new Insets(15,15,1,15);
        pnlConnectionInfo.add(lblServerAddress, c); c.gridy++;
        c.insets = new Insets(0,15,5,15);
        pnlConnectionInfo.add(txtServerAddress, c); c.gridy++;
        c.insets = new Insets(0,15,1,15);
        pnlConnectionInfo.add(lblServerPassword, c); c.gridy++;
        c.insets = new Insets(0,15,5,15);
        pnlConnectionInfo.add(passServerPassword, c); c.gridy++;
        c.insets = new Insets(0,15,1,15);
        pnlConnectionInfo.add(lblUsername, c); c.gridy++;
        c.insets = new Insets(0,15,5,15);
        pnlConnectionInfo.add(txtUsername, c); c.gridy++;
        c.insets = new Insets(0,15,15,15);
        pnlConnectionInfo.add(btnConnect, c); c.gridx++;
        pnlConnectionInfo.add(btnAddToList, c); c.gridx++;
        pnlConnectionInfo.add(btnRemoveFromList, c);
        // -- Saved login info
        c.gridx = c.gridy = 0;
        c.insets = new Insets(0,0,5,0);
        pnlSavedLogin.add(lblSavedLogin, c); c.gridy++;
        c.weighty = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.BOTH;
        spaneSavedLogin.setViewportView(tblSavedLogin);
        pnlSavedLogin.add(spaneSavedLogin, c);
        c.gridx = 1; c.gridy = 0;
        c.gridheight = 6;
        c.gridwidth = 2;
        c.weighty = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(15,0,1,15);
        pnlConnectionInfo.add(pnlSavedLogin, c);
        // -- Add main panel to frame
        c.gridx = c.gridy = 0;
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NORTH;
        c.gridheight = c.gridwidth = 1;
        this.add(pnlConnectionInfo, c);
    }

    /**
     * Checks if the TextField input is valid.
     * @return True if valid
     */
    private boolean isValidInput()
    {
        if (txtServerAddress.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Server address cannot be empty.", "", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (txtUsername.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.", "", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (!txtUsername.getText().replaceAll("[^A-Za-z0-9_.]", "aa").equals(txtUsername.getText()))
        {
            JOptionPane.showMessageDialog(null, "Username can only contain letter, number, underscore and period.", "", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Creates a table containing saved login info to be shown to user.
     */
    private void generateTable()
    {
        savedLoginData = getSavedLogins();
        savedLoginDataTbl = getSavedLoginTableData(savedLoginData);
        tblSavedLogin = new JTable(savedLoginDataTbl, tblSavedLoginColumns)
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }
        };
        tblSavedLogin.setBackground(new Color(44, 47, 51));
        tblSavedLogin.setForeground(Color.WHITE);
        tblSavedLogin.getTableHeader().setBackground(new Color(44, 47, 51));
        tblSavedLogin.getTableHeader().setForeground(Color.WHITE);
        tblSavedLogin.setShowGrid(false);
        tblSavedLogin.getSelectionModel().addListSelectionListener(new tblSavedLoginListSelectionListener());
        spaneSavedLogin.setViewportView(tblSavedLogin);
        spaneSavedLogin.revalidate();
    }

    /**
     * Saves login information for the current TextFields' content
     */
    private void saveLoginInfo()
    {
        if (!isValidInput()) return;
        ArrayList<String> hosts = new ArrayList();
        for (String[] hostUsernamePair : savedLoginDataTbl)
            hosts.add(hostUsernamePair[0]);
        if (hosts.contains(txtServerAddress.getText()))
        {
            JOptionPane.showMessageDialog(null, "The host you want to save is already saved in the list.", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String path = String.format("appdata/saved-logins/%s@%s.properties", txtUsername.getText(), txtServerAddress.getText());
        Properties newProperties = new Properties();

        try
        {
            Writer inputStream = new FileWriter(path);
            newProperties.setProperty("host_address", txtServerAddress.getText());
            newProperties.setProperty("host_password", new String(passServerPassword.getPassword()));
            newProperties.setProperty("client_username", txtUsername.getText());
            newProperties.store(inputStream, "Client login data");
            generateTable();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Failed to save login info.\nDetails: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Removes selected login info from the list.
     */
    private void removeLoginInfo()
    {
        int index = tblSavedLogin.getSelectedRow();
        String pathToRemove = String.format("appdata/saved-logins/%s@%s.properties", savedLoginDataTbl[index][1], savedLoginDataTbl[index][0]);
        if (Utilities.fileExists(pathToRemove))
        {
            Utilities.deleteFile(pathToRemove);
            generateTable();
            btnRemoveFromList.setEnabled(false);
        }
        else
            JOptionPane.showMessageDialog(null, "Failed to delete login info.\nDetails: " + pathToRemove + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Gets saved login information stored in appdata/saved-logins folder
     * @return A 2D array that stores the login information saved.
     */
    private String[][] getSavedLogins()
    {
        String[][] output = {};

        // Detect appdata/saved-logins folder
        if (!Utilities.dirExists("appdata/saved-logins"))
        {
            Utilities.makeDir("appdata/saved-logins");
            return output;
        }

        File[] loginFiles = Utilities.listFiles("appdata/saved-logins", ".properties");
        output = new String[loginFiles.length][3];
        for (int i = 0; i < loginFiles.length; i++)
        {
            File loginFile = loginFiles[i];
            Properties login = new Properties();
            try
            {
                InputStream inputStream = new FileInputStream(loginFile);
                if (inputStream == null) continue;
                login.load(inputStream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                continue;
            }

            String host_addr = login.getProperty("host_address");
            String host_pass = login.getProperty("host_password");
            String client_usr = login.getProperty("client_username");
            output[i] = new String[] {host_addr, host_pass, client_usr};
        }

        return output;
    }

    /**
     * Converts login information data for the table by removing password field
     * @param data The login information data obtained from getSavedLogins
     * @return A 2D array that stores the login information without the password
     */
    private String[][] getSavedLoginTableData(String[][] data)
    {
        String[][] newData = new String[data.length][2];

        for (int i = 0; i < data.length; i++)
        {
            newData[i] = new String[] {data[i][0], data[i][2]};
        }

        return newData;
    }

    /**
     * An action listener for btnConnect click event.
     */
    private class btnConnectActionlistener  implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (isValidInput())
            {
                btnConnect.setEnabled(false);
                btnConnect.setText("Connecting");
                connectThread connectThread = new connectThread();
                connectThread.start();
            }
        }

        /**
         * A thread to prevent GUI locking when connection is being made.
         */
        private class connectThread extends Thread
        {
            public void run()
            {
                try
                {
                    // Try to connect to server
                    Client client = new Client(txtServerAddress.getText(), 5550);
                    client.connect();
                    connectionStatus = 0;

                    // Authenticate
                    InitHandshakeData initHandshakeData = new InitHandshakeData(txtUsername.getText(), new String(passServerPassword.getPassword()));
                    client.sendString(Utilities.objSerialize(initHandshakeData));
                    while (connectionStatus == 0)
                    {
                        Thread.sleep(10);
                    }
                    if (connectionStatus == 2)
                    {
                        JOptionPane.showMessageDialog(null, "Incorrect server password.", "", JOptionPane.INFORMATION_MESSAGE);
                        client.stop();
                        btnConnect.setEnabled(true);
                        btnConnect.setText("Connect");
                        return;
                    }
                    if (connectionStatus == 3)
                    {
                        JOptionPane.showMessageDialog(null, "Username already taken.", "", JOptionPane.INFORMATION_MESSAGE);
                        client.stop();
                        btnConnect.setEnabled(true);
                        btnConnect.setText("Connect");
                        return;
                    }

                    // If client connected successfully
                    ClientMain.setClient(client);
                    ClientMain.setClientUsername(txtUsername.getText());
                    ClientMain.getMainWindow().setTitle(txtUsername.getText() + "@" + txtServerAddress.getText() + " - Chat");
                    ClientMain.getMainWindow().switchContentPane(1);
                    ClientMain.getMainWindow().revalidate();
                    ClientMain.getMainWindow().getMainPanel().loadData();
                }
                catch (IOException e1)
                {
                    btnConnect.setEnabled(true);
                    btnConnect.setText("Connect");
                    JOptionPane.showMessageDialog(null, "Unable to connect to server.\nDetails: " + e1, "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * An action listener for the saved login table. This will autofill the TextFields with the selected list item
     * data.
     */
    private class tblSavedLoginListSelectionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            String host = tblSavedLogin.getValueAt(tblSavedLogin.getSelectedRow(), 0).toString();
            String username = tblSavedLogin.getValueAt(tblSavedLogin.getSelectedRow(), 1).toString();
            String password = savedLoginData[tblSavedLogin.getSelectedRow()][1];
            txtServerAddress.setText(host);
            txtUsername.setText(username);
            passServerPassword.setText(password);
            btnRemoveFromList.setEnabled(true);
        }
    }

    /**
     * Sets the connection status
     * @param connectionStatus connection status
     */
    public void setConnectionStatus(int connectionStatus)
    {
        this.connectionStatus = connectionStatus;
    }
}
