package zefryuuko.chat.client.gui;

import zefryuuko.chat.client.Main;
import zefryuuko.chat.client.Client;
import zefryuuko.chat.commdata.InitHandshakeData;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
    private JButton btnConnect = new JButton();
    private JPanel pnlSavedLogin = new JPanel();
    private JLabel lblSavedLogin = new JLabel();
    private JScrollPane spaneSavedLogin = new JScrollPane();
    private JTable tblSavedLogin = new JTable();
    private String[] tblSavedLoginColumns = new String[] {"Host", "Username"};
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
        pnlSavedLogin.setPreferredSize(new Dimension(400, 100));
        pnlSavedLogin.setLayout(new GridBagLayout());
        pnlSavedLogin.setBackground(new Color(44, 47, 51));
        lblSavedLogin.setText("SAVED LOGINS                                                                                                      ");
        lblSavedLogin.setForeground(Color.WHITE);
        lblSavedLogin.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
//        spaneSavedLogin.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spaneSavedLogin.getViewport().setBackground(new Color(44, 47, 51));
        spaneSavedLogin.setBorder(null);
        tblSavedLogin = new JTable(new String[][] {{"localhost", "zefryuuko"}, {"zefryuuko.ga", "zefryuuko_"}}, tblSavedLoginColumns);
        tblSavedLogin.setBackground(new Color(44, 47, 51));
        tblSavedLogin.setForeground(Color.WHITE);
        tblSavedLogin.getTableHeader().setBackground(new Color(44, 47, 51));
        tblSavedLogin.getTableHeader().setForeground(Color.WHITE);
        tblSavedLogin.setShowGrid(false);
        tblSavedLogin.getSelectionModel().addListSelectionListener(new tblSavedLoginListSelectionListener());


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
        pnlConnectionInfo.add(btnAddToList, c);
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
                    Main.setClient(client);
                    Main.setClientUsername(txtUsername.getText());
                    Main.getWindow().setTitle(txtUsername.getText() + "@" + txtServerAddress.getText() + " - Chat");
                    Main.getWindow().switchContentPane(1);
                    Main.getWindow().revalidate();
                    Main.getWindow().getMainPanel().loadData();
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

    private class tblSavedLoginListSelectionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            String host = tblSavedLogin.getValueAt(tblSavedLogin.getSelectedRow(), 0).toString();
            String username = tblSavedLogin.getValueAt(tblSavedLogin.getSelectedRow(), 1).toString();
            txtServerAddress.setText(host);
            txtUsername.setText(username);
        }
    }

    public void setConnectionStatus(int connectionStatus)
    {
        this.connectionStatus = connectionStatus;
    }
}
