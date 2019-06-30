package zefryuuko.chat.client;

import zefryuuko.chat.commdata.TextEditorInitData;
import zefryuuko.chat.lib.Utilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class EditorSessionsWindow extends JFrame
{
    private HashMap<String, String> sessions = new HashMap<>();
    private GridBagConstraints c = new GridBagConstraints();
    private JLabel lblSelect = new JLabel();
    private JList<String> lstSessions = new JList<>();
    private JScrollPane spaneSessionsList = new JScrollPane(lstSessions);
    private JButton btnJoin = new JButton();
    private JButton btnCreate = new JButton();
    private JFileChooser fileChooser = new JFileChooser();

    public EditorSessionsWindow()
    {
        // Window properties
        this.setPreferredSize(new Dimension(300, 500));
        this.setLocationRelativeTo(ClientMain.getMainWindow());
        this.setLayout(new GridBagLayout());
        this.setVisible(true);

        // Object properties
        lblSelect.setText("Select session:");
        btnCreate.setText("Create session");
        btnCreate.addActionListener(e -> createSession());
        btnJoin.setText("Join session");
        btnJoin.addActionListener(e -> joinSession());
        fileChooser.setMultiSelectionEnabled(false);

        // Window objects
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        this.add(lblSelect, c); c.gridy++;
        c.weighty = c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(spaneSessionsList, c); c.gridy++;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = c.weighty = 0;
        this.add(btnJoin, c); c.gridx++;
        this.add(btnCreate, c);
    }

    public void populateList(HashMap<String, String> sessions)
    {
        this.sessions = sessions;
        DefaultListModel defaultListModel = new DefaultListModel()
        for (String key : sessions.keySet())
        {
            defaultListModel.addElement(sessions.get(key));
        }
        lstSessions.setModel(defaultListModel);
    }

    private void createSession()
    {
        try
        {
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File chosenFile = fileChooser.getSelectedFile();
                String fileName = chosenFile.getName();
                String currentLine;
                String content = "";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(chosenFile));
                while ((currentLine = bufferedReader.readLine()) != null)
                {
                    content += currentLine + "\n";
                }
                content = content.substring(0, content.length() - 1);   // Remove trailing newline
                TextEditorInitData textEditorInitData = new TextEditorInitData(fileName, content);
                ClientMain.getClient().sendString(Utilities.objSerialize(textEditorInitData));
                // TODO: add text editor window popup
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, String.format("Error reading file.\nDetails: %s", e));
        }
    }

    private void joinSession()
    {

    }

}
