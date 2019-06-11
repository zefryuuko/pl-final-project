package zefryuuko.chat.client.gui;

import zefryuuko.chat.client.Main;
import zefryuuko.chat.commdata.*;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel
{
    GridBagConstraints c = new GridBagConstraints();
    private JTextPane paneOnlineUsers = new JTextPane();
    private String paneOnlineUsersCSS = "body{background:#2c2f33;color:#ffffff;margin:0;padding:0 7px 0px 7px;font-family:'Helvetica Neue', Arial, serif;}h3.header{color:#666a71;}ul{list-style-type:none;padding:0;margin:0;}.user{width:100%;height:30px;background:#36393e;padding:5px;margin-bottom:5px;font-family:'Helvetica Neue', Arial, serif;text-align:left;.hidden{background:#2c2f33;height:1px;padding:0px;margin-bottom:0px;font-size:1px;}";
    private MessagesContainer spaneMessagesContainer = new MessagesContainer();
    private MessageTextField txtMessage = new MessageTextField();

    public MainPanel()
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;
        this.c.insets = new Insets(0, 0, 0, 0);

        // Object properties
        paneOnlineUsers.setPreferredSize(new Dimension(200, 600));
        paneOnlineUsers.setSize(new Dimension(200, 600));
        paneOnlineUsers.setEditable(false);
        paneOnlineUsers.setCaret(new NoTextSelectionCaret(paneOnlineUsers));
        paneOnlineUsers.setContentType("text/html");
        paneOnlineUsers.setText("<html><head><style>" + paneOnlineUsersCSS + "</style></head><body><h3 class='header'>ONLINE</h3><div class='container'></div></body></html>");


        // Panel objects
        c.gridheight = 2;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(paneOnlineUsers, c);
        c.gridx++;
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
        CommData getConnectedUserData = new RequestData("getConnectedUserData");
        Main.getClient().sendString(Utilities.objSerialize(getConnectedUserData));
    }

    public void populateOnlineUsers(ArrayList<String> users)
    {
        String html = "<html><head><style>" + paneOnlineUsersCSS + "</style></head><body><h3 class='header'>ONLINE</h3><div class='container'>";
        for (String user : users)
        {
            html = html + "<div class='user'>" + user + "</div>";
        }
        html = html + "<div class='hidden'>                         </div>";
        html = html + "</div></body></html>";
        paneOnlineUsers.setText(html);
    }

    public void populateMessages(ArrayList<String> messages)
    {
        // TODO: Load messages
    }

    public void addMessage(ChatData chatData)
    {
        MessageContainer messageContainer = new MessageContainer(chatData.getUsername(), chatData.getData());
        spaneMessagesContainer.addMessage(messageContainer);
        spaneMessagesContainer.revalidate();
        spaneMessagesContainer.getVerticalScrollBar().setValue(spaneMessagesContainer.getVerticalScrollBar().getMinimum());
        spaneMessagesContainer.getVerticalScrollBar().setValue(spaneMessagesContainer.getVerticalScrollBar().getMaximum());
    }
}
