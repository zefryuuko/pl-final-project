package zefryuuko.chat.client;

import zefryuuko.chat.client.gui.LineNumberTextArea;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class FileBrowserWindow extends JFrame
{
    private final String baseDir;
    private String currentSelectedDir;
    private JPanel pnlMain = new JPanel();
    private JPanel pnlBrowse = new JPanel();
    private JPanel pnlNavigation = new JPanel();
    private JScrollPane spaneFiles = new JScrollPane();
    private JButton btnBack = new JButton();
    private JList lstFiles = new JList();
    private LineNumberTextArea txtPreview = new LineNumberTextArea();
    private GridBagConstraints c = new GridBagConstraints();

    public FileBrowserWindow(String baseDir)
    {
        this.baseDir = baseDir;
        populateFileList(baseDir);

        // Window properties
        this.setContentPane(pnlMain);
        this.setSize(new Dimension(1280, 720));
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Object properties
        pnlMain.setLayout(new GridBagLayout());
        pnlMain.setBackground(new Color(59, 63, 68));
        pnlBrowse.setLayout(new GridBagLayout());
        pnlBrowse.setBackground(new Color(59, 63, 68));
        pnlNavigation.setMaximumSize(new Dimension(20, 30));
        pnlNavigation.setPreferredSize(new Dimension(20, 30));
        pnlNavigation.setMinimumSize(new Dimension(20, 30));
        pnlNavigation.setBackground(new Color(44, 47, 51));
        lstFiles.setBackground(new Color(59, 63, 68));
        lstFiles.setForeground(Color.WHITE);
        spaneFiles.getViewport().setOpaque(false);
        txtPreview.setTextAreaBackground(new Color(44, 47, 51));
        txtPreview.setTextAreaForeground(Color.WHITE);
        txtPreview.setLineNumberingBackground(new Color(59, 63, 68));
        btnBack.setText("Back");
        btnBack.setEnabled(false);
        btnBack.addActionListener(e -> goBack());
        spaneFiles.setViewportView(lstFiles);
        lstFiles.addMouseListener(new lstFilesMouseListener());

        // Panel objects
        pnlNavigation.add(btnBack);

        c.gridx = c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        pnlBrowse.add(pnlNavigation, c); c.gridy++;
        c.weightx = c.weighty = 1;
        pnlBrowse.add(spaneFiles, c);

        c.insets = new Insets(10, 10, 10, 5);
        c.gridx = c.gridy = 0;
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        pnlMain.add(pnlBrowse, c); c.gridx++;
        c.insets = new Insets(10, 5, 10, 10);
        pnlMain.add(txtPreview, c);
    }

    private void populateFileList(String path)
    {
        currentSelectedDir = path;
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        for (File file : Utilities.listFiles(path, ""))
        {
            String[] filePath = file.getPath().split("/");
            defaultListModel.addElement(filePath[filePath.length - 1]);
        }
        this.lstFiles.setModel(defaultListModel);
    }

    private void goBack()
    {
        String[] currentDirSplit = currentSelectedDir.split("/");
        String nextDir = "";
        for (int i = 0; i < currentDirSplit.length - 1; i++)
            nextDir += currentDirSplit[i] + "/";
        currentSelectedDir = nextDir.substring(0, nextDir.length() - 1);
        populateFileList(currentSelectedDir);
        if (currentSelectedDir.equals(baseDir))
            btnBack.setEnabled(false);
    }

    private class lstFilesMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent evt)
        {
            if (evt.getClickCount() == 2)
            {
                if (Utilities.dirExists(currentSelectedDir + "/" + lstFiles.getSelectedValue()))
                {
                    currentSelectedDir = currentSelectedDir + "/" + lstFiles.getSelectedValue();
                    populateFileList(currentSelectedDir);
                    btnBack.setEnabled(true);
                }
                else
                {
                    try
                    {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(currentSelectedDir + "/" + lstFiles.getSelectedValue()));
                        String fileContent = "";
                        String currentLine;
                        while ((currentLine = bufferedReader.readLine()) != null) fileContent += currentLine + "\n";
                        txtPreview.setText(fileContent.substring(0, fileContent.length() - 1));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
// Resources:
// - JTree file explorer: https://stackoverflow.com/questions/16112119/creating-dynamic-jtree-from-absolute-file-path
