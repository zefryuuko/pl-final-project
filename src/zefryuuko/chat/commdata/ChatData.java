package zefryuuko.chat.commdata;

public class ChatData extends CommData
{
    private final String username;
    private final String data;

    public ChatData(String username, String data)
    {
        super("ChatData");
        this.username = username;
        this.data = data;
    }

    public String getUsername()
    {
        return username;
    }

    public String getData()
    {
        return data;
    }
}
