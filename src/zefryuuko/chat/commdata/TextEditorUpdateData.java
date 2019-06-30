package zefryuuko.chat.commdata;

public class TextEditorUpdateData extends CommData
{
    private final int offset;
    private final String data;

    /**
     * Stores the changes on the text editor
     * @param offset Cursor position on textbox
     * @param data Things added
     */
    public TextEditorUpdateData(int offset, String data)
    {
        super("TextEditorUpdateData");
        this.offset = offset;
        this.data = data;
    }

    public int getOffset()
    {
        return offset;
    }

    public String getData()
    {
        return data;
    }
}
