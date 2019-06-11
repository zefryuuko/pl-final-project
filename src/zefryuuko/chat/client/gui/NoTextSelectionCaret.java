package zefryuuko.chat.client.gui;

import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

public class NoTextSelectionCaret extends DefaultCaret
{
    public NoTextSelectionCaret(JTextComponent textComponent)
    {
        setBlinkRate( textComponent.getCaret().getBlinkRate() );
        textComponent.setHighlighter( null );
    }

    @Override
    public int getMark()
    {
        return getDot();
    }
}

// Source: https://stackoverflow.com/questions/32515391/how-to-disable-text-selection-on-jtextarea-swing
