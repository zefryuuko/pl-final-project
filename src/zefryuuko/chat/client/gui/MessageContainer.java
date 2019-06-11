package zefryuuko.chat.client.gui;

import javax.swing.*;
import java.awt.*;

public class MessageContainer extends JPanel
{
    private GridBagConstraints c = new GridBagConstraints();
    private JLabel lblUsername = new JLabel();
    private JTextPane txtMessage = new JTextPane();

    public MessageContainer(String username, String message)
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;
        this.setBackground(new Color(54, 57, 62));

        // Object properties
        lblUsername.setText(username);
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
        txtMessage.setEditable(false);
        txtMessage.setText(message);
        txtMessage.setForeground(new Color(215, 216, 217));
        txtMessage.setBackground(new Color(54, 57, 62));
        txtMessage.setBorder(null);

        // Panel objects
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        this.add(lblUsername, c); c.gridy++;
        this.c.insets = new Insets(2, 0, 0, 0);
        this.add(txtMessage, c);

    }
}
