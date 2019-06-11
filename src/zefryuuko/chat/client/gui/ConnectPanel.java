package zefryuuko.chat.client.gui;

import zefryuuko.chat.client.Main;
import zefryuuko.chat.client.Client;
import zefryuuko.chat.commdata.InitHandshakeData;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
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
    private int connectionStatus = 0;

    public ConnectPanel()
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;

        // Object properties
        pnlConnectionInfo.setSize(new Dimension(500, 300));
        pnlConnectionInfo.setLayout(new GridBagLayout());
        lblServerAddress.setText("Host:");
        lblServerPassword.setText("Password:");
        lblUsername.setText("Username: ");
        txtServerAddress.setPreferredSize(new Dimension(150, 20));
        passServerPassword.setPreferredSize(new Dimension(150, 20));
        txtUsername.setPreferredSize(new Dimension(150, 20));
        btnConnect.setText("Connect");
        btnConnect.addActionListener(new btnConnectActionlistener());
        btnAddToList.setText("Save to list");

        // Panel objects
        pnlConnectionInfo.add(lblServerAddress, c); c.gridy++;
        pnlConnectionInfo.add(txtServerAddress, c); c.gridy++;
        pnlConnectionInfo.add(lblServerPassword, c); c.gridy++;
        pnlConnectionInfo.add(passServerPassword, c); c.gridy++;
        pnlConnectionInfo.add(lblUsername, c); c.gridy++;
        pnlConnectionInfo.add(txtUsername, c); c.gridy++;
        pnlConnectionInfo.add(btnConnect, c); c.gridx++;
        pnlConnectionInfo.add(btnAddToList, c);

        c.gridx = c.gridy = 0;
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

    public void setConnectionStatus(int connectionStatus)
    {
        this.connectionStatus = connectionStatus;
    }
}
