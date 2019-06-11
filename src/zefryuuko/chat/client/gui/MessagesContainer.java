package zefryuuko.chat.client.gui;

import javax.swing.*;
import java.awt.*;

public class MessagesContainer extends JScrollPane
{
    private GridBagConstraints c = new GridBagConstraints();
    private JPanel pnlContainer = new JPanel();

    public MessagesContainer()
    {
        // Panel properties
        this.setViewportView(pnlContainer);
        this.setBorder(null);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        UIManager.put("ScrollBar.track", new Color(54, 57, 62));

        // Object properties
        c.gridx = c.gridy = 0;
        c.insets = new Insets(5, 20, 5, 20);
        pnlContainer.setBackground(new Color(54, 57, 62));
        pnlContainer.setLayout(new GridBagLayout());

        // Panel objects
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
//        pnlContainer.add(tempMsg, c); c.gridy++;
//        pnlContainer.add(tempMsg2, c);
    }

    public void addMessage(MessageContainer messageContainer)
    {
        pnlContainer.add(messageContainer, c); c.gridy++;
        pnlContainer.revalidate();
    }
}
