package zefryuuko.chat.client;

import zefryuuko.chat.client.gui.LineNumberTextArea;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TextViewerWindow extends JFrame
{
    private JPanel pnlMain = new JPanel();
    private JPanel pnlSouthContainer = new JPanel();
    private JLabel lblFileName = new JLabel();
    private LineNumberTextArea txtTextView = new LineNumberTextArea();
    private JButton btnDiscussSelected = new JButton();

    public TextViewerWindow()
    {
        // Window properties
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(600, 500));
        this.setMinimumSize(new Dimension(600, 500));
        this.setLocationRelativeTo(null);
        this.setTitle("Text Viewer");
        this.setVisible(true);
        this.setContentPane(pnlMain);

        // Content pane properties
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBorder(new EmptyBorder(10 ,10, 10, 10));
        pnlMain.setBackground(new Color(51, 54, 58));

        // Content pane object properties
        lblFileName.setText("");
        lblFileName.setForeground(Color.WHITE);
        lblFileName.setBorder(new EmptyBorder(0, 0, 5, 0));
        lblFileName.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        txtTextView.setTextAreaBackground(new Color(44, 47, 51));
        txtTextView.setTextAreaForeground(Color.WHITE);
        txtTextView.setLineNumberingBackground(new Color(59, 63, 68));
        txtTextView.setEditable(false);
        pnlSouthContainer.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 7));
        pnlSouthContainer.setBackground(new Color(51, 54, 58));
        btnDiscussSelected.setText("Discuss selected");


        // Content pane objects
        pnlSouthContainer.add(btnDiscussSelected);
        pnlMain.add(txtTextView, BorderLayout.CENTER);
        pnlMain.add(lblFileName, BorderLayout.NORTH);
        pnlMain.add(pnlSouthContainer, BorderLayout.SOUTH);
    }

    public TextViewerWindow(String filePath)
    {
        this();
        try
        {
            lblFileName.setText(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String data = "";
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) data += currentLine + "\n";
            data = data.substring(0, data.length() - 1);
            txtTextView.setText(data);
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "File " + filePath + "not found.", "", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setTextViewContent(String text)
    {
        txtTextView.setText(text);
    }
}
