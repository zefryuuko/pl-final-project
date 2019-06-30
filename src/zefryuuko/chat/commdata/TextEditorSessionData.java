package zefryuuko.chat.commdata;

public class TextEditorSessionData extends CommData
{
    private final int port;
    private final String fileName;
    private final String currentContent;

    public TextEditorSessionData(int port, String fileName, String currentContent)
    {
        super("TextEditorSessionData");
        this.port = port;
        this.fileName = fileName;
        this.currentContent = currentContent;
    }

    public int getPort()
    {
        return port;
    }

    public String getFileName()
    {
        return fileName;
    }

    public String getCurrentContent()
    {
        return currentContent;
    }
}