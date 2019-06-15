package zefryuuko.chat.client.gui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LineNumberTextArea extends JPanel
{
    private JTextArea textArea = new JTextArea();
    private JTextArea lineNumbers = new JTextArea();
    private JScrollPane textAreaScrollPane = new JScrollPane(textArea);
    private JScrollPane lineNumbersScrollPane = new JScrollPane(lineNumbers);

    public LineNumberTextArea()
    {
        // Panel properties
        this.setLayout(new BorderLayout());

        // Object properties
        lineNumbersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        lineNumbersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lineNumbersScrollPane.setMinimumSize(new Dimension(40, 100));
        lineNumbersScrollPane.setPreferredSize(new Dimension(40, 100));
        lineNumbersScrollPane.setMaximumSize(new Dimension(40, 100));
        lineNumbersScrollPane.setBorder(null);
        textAreaScrollPane.setBorder(null);
        lineNumbers.setFont(new Font("Menlo", Font.PLAIN, textArea.getFont().getSize()));
        lineNumbers.setBorder(null);
        lineNumbers.setEditable(false);
        lineNumbers.setCaret(new NoTextSelectionCaret(lineNumbers));
        lineNumbers.setBackground(new Color(234, 234, 234));
        lineNumbers.setForeground(Color.GRAY);
        lineNumbers.setBorder(new MatteBorder(3, 0, 3, 0, lineNumbers.getBackground()));
        textArea.setFont(new Font("Menlo", Font.PLAIN, textArea.getFont().getSize()));
        textArea.setBorder(new MatteBorder(3, 7, 3, 3, textArea.getBackground()));
        textArea.addKeyListener(new textAreaKeyListener());
        textAreaScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> updateLineCountScroll());

        // Panel objects
        this.add(lineNumbersScrollPane, BorderLayout.WEST);
        this.add(textAreaScrollPane, BorderLayout.CENTER);
    }

    private int getLineCount(String text)
    {
        return (int) text.chars().filter(x -> x == '\n').count() + 1;
    }

    private void updateLineCount()
    {
        int lineCount = getLineCount(textArea.getText());
        if (lineCount == 0) lineNumbers.setText("   1");
        String lineNumbersContent = "";
        for (int i = 1; i <= lineCount; i++) lineNumbersContent += String.format("%4d\n", i);
        lineNumbersContent = lineNumbersContent.substring(0, lineNumbersContent.length() - 1);
        lineNumbers.setText(lineNumbersContent);
        lineNumbers.revalidate();
    }

    private void updateLineCountScroll()
    {
        int scrollPosition = textAreaScrollPane.getVerticalScrollBar().getValue();
        SwingUtilities.invokeLater(() -> lineNumbersScrollPane.getVerticalScrollBar().setValue(scrollPosition));
        lineNumbersScrollPane.revalidate();
    }

    private class textAreaKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {

        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER ||
                (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) ||
                (e.getKeyCode() == KeyEvent.VK_V && e.isMetaDown()))
            {
                updateLineCount();
            }
        }
    }

    public void setText(String text)
    {
        textArea.setText(text);
        updateLineCount();
    }

    public String getText()
    {
        return textArea.getText();
    }

    public String getSelectedText()
    {
        return textArea.getSelectedText();
    }

    public int getSelectionStart()
    {
        return textArea.getSelectionStart();
    }

    public int getSelectionEnd()
    {
        return textArea.getSelectionEnd();
    }

    public int getLineOfOfset(int offset) throws BadLocationException
    {
        return textArea.getLineOfOffset(offset);
    }

    public void setTextAreaBackground(Color c)
    {
        textArea.setBackground(c);
        textArea.setBorder(new MatteBorder(3, 7, 3, 3, textArea.getBackground()));
    }

    public void setTextAreaForeground(Color c)
    {
        textArea.setForeground(c);
    }

    public void setLineNumberingBackground(Color c)
    {
        lineNumbers.setBackground(c);
        lineNumbers.setBorder(new MatteBorder(3, 0, 3, 0, lineNumbers.getBackground()));
    }

    public void setLineNumberingForeground(Color c)
    {
        lineNumbers.setForeground(c);
    }

    public void setEditable(boolean b)
    {
        textArea.setEditable(b);
    }
}
