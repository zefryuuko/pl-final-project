package zefryuuko.chat.commdata;

public class TextEditorInitData extends CommData
{
    private final String fileName;
    private final String startingData;

    public TextEditorInitData(String fileName, String startingData)
    {
        super("TextEditorInitData");
        this.fileName = fileName;
        this.startingData = startingData;
    }

    public String getFileName()
    {
        return fileName;
    }

    public String getStartingData()
    {
        return startingData;
    }
}
