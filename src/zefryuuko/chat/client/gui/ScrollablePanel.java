package zefryuuko.chat.client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * A scrollable panel used as a JScrollPane viewport view allowing fixed width based on window size.
 */
public class ScrollablePanel extends JPanel implements Scrollable
{
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return ((orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width) - 10;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}

// Referenced from
// https://stackoverflow.com/questions/2716274/jscrollpane-needs-to-shrink-its-width