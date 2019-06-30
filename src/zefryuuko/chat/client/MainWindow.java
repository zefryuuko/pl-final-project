package zefryuuko.chat.client;

import zefryuuko.chat.client.gui.*;

import javax.swing.*;
import java.awt.*;

/**
 * The main window for the application
 */
public class MainWindow extends JFrame
{
    ConnectPanel connectPanel = new ConnectPanel();
    MainPanel mainPanel = new MainPanel();

    public MainWindow()
    {
        // MainWindow properties
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1280, 720));
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);

        // MainWindow default ContentPane
        this.setContentPane(connectPanel);
    }

    /**
     * Switches the window's content pane
     * @param paneID The ID of the contentPane
     */
    public void switchContentPane(int paneID)
    {
        switch(paneID)
        {
            case 0:
                this.setContentPane(connectPanel);
                break;
            case 1:
                this.setContentPane(mainPanel);
        }
    }

    public MainPanel getMainPanel()
    {
        return mainPanel;
    }

    public ConnectPanel getConnectPanel()
    {
        return connectPanel;
    }
}
