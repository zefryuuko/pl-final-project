package zefryuuko.chat.client.gui;

import zefryuuko.chat.client.ClientMain;
import zefryuuko.chat.client.EditorSessionsWindow;
import zefryuuko.chat.client.FileBrowserWindow;
import zefryuuko.chat.commdata.*;
import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainPanel extends JPanel
{
    GridBagConstraints c = new GridBagConstraints();
    private JTextPane paneOnlineUsers = new JTextPane();
    private String paneOnlineUsersCSS = "body{background:#2c2f33;color:#ffffff;margin:0;padding:0 7px 0px 7px;font-family:'Helvetica Neue', Arial, serif;}h3.header{color:#666a71;}ul{list-style-type:none;padding:0;margin:0;}.user{width:100%;height:30px;background:#36393e;padding:5px;margin-bottom:5px;font-family:'Helvetica Neue', Arial, serif;text-align:left;}";
    private JPanel pnlServerInfo = new JPanel();
    private JLabel lblServerName = new JLabel();
    private JLabel lblServerDescription = new JLabel();
    private JButton btnShowFiles = new JButton();
    private MessagesContainer spaneMessagesContainer = new MessagesContainer();
    private MessageTextField txtMessage = new MessageTextField();
    private EditorSessionsWindow editorSessionsWindow;

    public MainPanel()
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;
        this.c.insets = new Insets(0, 0, 0, 0);

        // Object properties
        paneOnlineUsers.setMinimumSize(new Dimension(200, 600));
        paneOnlineUsers.setPreferredSize(new Dimension(200, 600));
        paneOnlineUsers.setSize(new Dimension(200, 600));
        paneOnlineUsers.setEditable(false);
        paneOnlineUsers.setCaret(new NoTextSelectionCaret(paneOnlineUsers));
        paneOnlineUsers.setContentType("text/html");
        paneOnlineUsers.setText("<html><head><style>" + paneOnlineUsersCSS + "</style></head><body><h3 class='header'>ONLINE</h3><div class='container'></div></body></html>");
        pnlServerInfo.setPreferredSize(new Dimension(10, 45));
        pnlServerInfo.setMaximumSize(new Dimension(10, 45));
        pnlServerInfo.setMinimumSize(new Dimension(10, 45));
        pnlServerInfo.setBackground(new Color(48, 51, 56));
        pnlServerInfo.setLayout(new BorderLayout());
        pnlServerInfo.setBorder(new EmptyBorder(5, 15, 5, 15));
        lblServerName.setText("Loading server name...");
        lblServerName.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        lblServerName.setForeground(new Color(189, 195, 204));
        lblServerDescription.setText("Loading server description...");
        lblServerDescription.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        btnShowFiles.setText("Show files");
        btnShowFiles.addActionListener(e -> showFiles());
        lblServerDescription.setForeground(Color.LIGHT_GRAY);
        txtMessage.setEnabled(false);
        txtMessage.setText("Loading messages...");

        // Panel objects
        pnlServerInfo.add(lblServerName, BorderLayout.WEST);
        pnlServerInfo.add(lblServerDescription, BorderLayout.SOUTH);
        c.gridheight = 3;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(paneOnlineUsers, c);
        c.gridx++;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        this.add(pnlServerInfo, c);
        c.gridy++;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(spaneMessagesContainer, c);
        c.gridy++;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        this.add(txtMessage, c);
    }

    public void loadData()
    {
        CommData getServerProperties = new RequestData("getServerPropertiesData");
        ClientMain.getClient().sendString(Utilities.objSerialize(getServerProperties));
        CommData getConnectedUserData = new RequestData("getConnectedUserData");
        ClientMain.getClient().sendString(Utilities.objSerialize(getConnectedUserData));
        CommData getAllMessages = new RequestData("getAllMessages");
        ClientMain.getClient().sendString(Utilities.objSerialize(getAllMessages));
    }

    public void populateServerProperties(ServerPropertiesData serverPropertiesData)
    {
        lblServerName.setText(serverPropertiesData.getServerName());
        lblServerDescription.setText(serverPropertiesData.getServerDescription());
        ClientMain.setServerHasGit(serverPropertiesData.hasGit());
        ClientMain.setServerGitAddress(serverPropertiesData.getGitAddress());
        loadGit();
        pnlServerInfo.revalidate();
    }

    public void populateOnlineUsers(ArrayList<String> users)
    {
        String html = "<html><head><style>" + paneOnlineUsersCSS + "</style></head><body><h3 class='header'>ONLINE</h3><div class='container'>";
        for (String user : users)
        {
            html = html + "<div class='user'>" + user + "</div>";
        }
        html = html + "</div></body></html>";
        paneOnlineUsers.setText(html);
    }

    public void populateMessages(LinkedList<ChatData> messages)
    {
        for (ChatData message : messages)
        {
            MessageContainer messageContainer = new MessageContainer(message.getUsername(), message.getData());
            spaneMessagesContainer.addMessage(messageContainer);
        }
        txtMessage.resetText();
        txtMessage.setEnabled(true);
        spaneMessagesContainer.revalidate();
        spaneMessagesContainer.repaint();
    }

    private void showFiles()
    {
        String directory = ClientMain.getGit().getFullDir();
        new FileBrowserWindow(directory);
    }

    private void loadGit()
    {
        if (!ClientMain.isServerHasGit()) return;
        pnlServerInfo.add(btnShowFiles, BorderLayout.EAST);
        if (!Utilities.isGitInstalled())
        {
            btnShowFiles.setEnabled(false);
            btnShowFiles.setText("Git disabled");
            btnShowFiles.setToolTipText("Git is disabled because 'git' command is not found.");
            JOptionPane.showMessageDialog(this, "Git features is disabled because 'git' command is not found. Please check your PATH and restart the program.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            Git git = new Git(ClientMain.getServerGitAddress());
            ClientMain.setGit(git);
        }
    }

    public void refreshGit()
    {
        btnShowFiles.setText("Pulling...");
        btnShowFiles.setEnabled(false);
        ClientMain.getGit().pull();
        btnShowFiles.setText("Show files");
        btnShowFiles.setEnabled(true);
    }

    public void addMessage(ChatData chatData)
    {
        MessageContainer messageContainer = new MessageContainer(chatData.getUsername(), chatData.getData());
        spaneMessagesContainer.addMessage(messageContainer);
        spaneMessagesContainer.revalidate();
        spaneMessagesContainer.repaint();
    }
}
