package zefryuuko.chat.client.gui;

import zefryuuko.chat.client.ClientMain;
import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessageTextField extends JPanel
{
    private GridBagConstraints c = new GridBagConstraints();
    private JTextField txtMessage = new JTextField();

    public MessageTextField()
    {
        // Panel properties
        this.setLayout(new GridBagLayout());
        this.c.gridx = this.c.gridy = 0;
        this.setMinimumSize(new Dimension(45, 40));
        this.setPreferredSize(new Dimension(45, 40));
        this.c.insets = new Insets(5, 10, 5, 10);
        this.setBackground(new Color(66, 69, 73));

        // Object properties
        txtMessage.setPreferredSize(new Dimension(150, 20));
        txtMessage.setBackground(new Color(66, 69, 73));
        txtMessage.setForeground(new Color(112, 117, 124));
        txtMessage.setBorder(null);
        txtMessage.setText("Enter a message  ");
        txtMessage.addActionListener(new txtMessageActionListener());
        txtMessage.addFocusListener(new txtMessageFocusListener());
        txtMessage.addKeyListener(new txtMessageKeyListener());

        // Panel objects
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(txtMessage, c);
    }

    public String getText()
    {
        return txtMessage.getText();
    }

    private class txtMessageKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER && !txtMessage.getText().isEmpty())
            {
                txtMessage.setText(txtMessage.getText() + "[br/] ");
                txtMessage.revalidate();
                return;
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }

    private class txtMessageActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!txtMessage.getText().equals(""))
            {
                ChatData chatData = new ChatData(ClientMain.getClientUsername(), txtMessage.getText());
                txtMessage.setText("");
                txtMessage.setForeground(Color.WHITE);
                ClientMain.getClient().sendString(Utilities.objSerialize(chatData));
            }
        }
    }

    private class txtMessageFocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if (txtMessage.getText().equals("Enter a message  "))
            {
                txtMessage.setText("");
                txtMessage.setForeground(Color.WHITE);
            }
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if (txtMessage.getText().isEmpty())
            {
                txtMessage.setText("Enter a message  ");
                txtMessage.setForeground(new Color(112, 117, 124));
            }
        }
    }
}
