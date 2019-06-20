package zefryuuko.chat.client.gui;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MessageContainer extends JPanel
{
    private GridBagConstraints c = new GridBagConstraints();
    private JLabel lblUsername = new JLabel();
    private JPanel pnlSpacer = new JPanel();
    private JTextPane txtMessage = new JTextPane();
    private String txtMessageCSS = "body{color:#ffffff;font-family:'Helvetica Neue',sans-serif;font-size:10px;}a{color:#ffffff;}.imgview{height:200px;width:300px;}.container{background:#3d4147;padding:5px 10px 7px 10px;margin-top:5px;width:100%;color:#e5e5e5;font-family:'Menlo',monospace;}.container-alt{background:#3d4147;padding:5px 6px 7px 6px;margin-top:5px;color:#e5e5e5;font-family:'Menlo',monospace;}.subheading{font-size:11px;}";

    public MessageContainer(String username, String message)
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;
        this.setBackground(new Color(54, 57, 62));

        // Object properties
        lblUsername.setText(parseUsername(username));
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
        pnlSpacer.setOpaque(false);
        txtMessage.setEditable(false);
        txtMessage.setContentType("text/html");
        txtMessage.setText("<html><style>" + txtMessageCSS + "</style><body>" + processRichText(message) + "</body></html>");
        txtMessage.addHyperlinkListener(new txtMessageHyperlinkListener());
        txtMessage.setForeground(new Color(215, 216, 217));
        txtMessage.setBackground(new Color(54, 57, 62));
        txtMessage.setBorder(null);

        // Panel objects
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        this.add(lblUsername, c); c.gridx++;
        this.add(generateFlair(username), c); c.gridx++;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        this.add(pnlSpacer, c); c.gridx = 0; c.gridy++;
        this.c.insets = new Insets(2, 0, 0, 0);
        this.c.gridwidth = 3;
        this.add(txtMessage, c);
    }

    private String parseUsername(String username)
    {
        String output = username;
        String[] filter = {"[Bot]", "[CodeDiscuss]"};
        for (String keyword : filter) output = output.replace(keyword, "");
        return output;
    }

    private JPanel generateFlair(String username)
    {
        JPanel pnlFlair = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        JLabel lblFlair = new JLabel();

        pnlFlair.setLayout(new GridBagLayout());
        lblFlair.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        if (username.contains("[Bot]"))
        {
            lblFlair.setText("Bot");
            lblFlair.setForeground(Color.WHITE);
            pnlFlair.setBackground(new Color(202, 105, 219));
        }
        else if (username.contains("[CodeDiscuss]"))
        {
            lblFlair.setText("Discussion");
            lblFlair.setForeground(Color.WHITE);
            pnlFlair.setBackground(new Color(105, 168, 219));
        }
        else
        {
            pnlFlair.setOpaque(false);
            pnlFlair.setPreferredSize(new Dimension(0, lblFlair.getPreferredSize().height));
        }
        c.gridx = c.gridy = 0;
        c.insets = new Insets(0, 5, 0, 5);
        pnlFlair.add(lblFlair, c);

        return pnlFlair;
    }

    private String processRichText(String message)
    {
        // Escape symbols used by HTML
        if (!message.startsWith("[noescape]") && !message.endsWith("[/noescape]"))
        {
            message = message.replaceAll("<", "&lt;");
            message = message.replaceAll(">", "&gt;");
        }
        else
        {
            message = message.replace("[noescape]", "");
            message = message.replace("[/noescape]", "");
        }

        String output = "";
        String[] tokenizedMessage = message.split(" ");



        for (int i = 0; i < tokenizedMessage.length; i++)
        {
            String token = tokenizedMessage[i];
            if (token.contains("[br/]"))
            {
                tokenizedMessage[i] = tokenizedMessage[i].replace("[br/]", "<br/>");
            }
            else if (isURL(token) && isImageURL(token))
                tokenizedMessage[i] = String.format("<a href='%s'>%s</a><br><div class='container-alt' width=312px><div class='imgview' style='background-image:url(\"%s\");'></div></div>", token, token, token);
            else if (isURL(token))
                tokenizedMessage[i] = String.format("<a href='%s'>%s</a>", token, token);
        }

        for (String token : tokenizedMessage)
        {
            output = output + " " + token;
        }

        return output.substring(1);
    }

    private boolean isURL(String text)
    {
        text = text.toLowerCase();
        if (text.startsWith("http://") || text.startsWith("https://")) return true;
        return false;
    }

    private boolean isImageURL(String text)
    {
        text = text.toLowerCase();
        String[] acceptedImageFormat = {".png", ".jpg", ".jpeg", ".gif"};
        for (String imageFormat : acceptedImageFormat)
        {
            if (text.endsWith(imageFormat)) return true;
        }
        return false;
    }

    private class txtMessageHyperlinkListener implements HyperlinkListener
    {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e)
        {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType()))
            {
                try
                {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(e.getURL().toURI());
                }
                catch (IOException | URISyntaxException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
