package zefryuuko.chat.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * A pane that stores all MessageContainers
 */
public class MessagesContainer extends JScrollPane
{
    private GridBagConstraints c = new GridBagConstraints();
    private ScrollablePanel pnlContainer = new ScrollablePanel();

    public MessagesContainer()
    {
        // Panel properties
        this.setViewportView(pnlContainer);
        this.setBorder(null);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getViewport().setOpaque(false);
        this.setBackground(new Color(54, 57, 62));

        // Object properties
        c.gridx = c.gridy = 0;
        c.insets = new Insets(5, 20, 5, 20);
        pnlContainer.setBackground(new Color(54, 57, 62));
        pnlContainer.setLayout(new GridBagLayout());
        pnlContainer.addComponentListener(new pnlContainerComponentListener());

        // Panel objects
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
    }

    /**
     * Adds a message into the container
     * @param messageContainer MessageContainer to be added
     */
    public void addMessage(MessageContainer messageContainer)
    {
        pnlContainer.add(messageContainer, c); c.gridy++;
        pnlContainer.revalidate();
    }

    /**
     * A component listener used to auto scroll to the bottom when a message arrives
     */
    private class pnlContainerComponentListener implements ComponentListener
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
        }

        @Override
        public void componentMoved(ComponentEvent e)
        {

        }

        @Override
        public void componentShown(ComponentEvent e)
        {

        }

        @Override
        public void componentHidden(ComponentEvent e)
        {

        }
    }
}
