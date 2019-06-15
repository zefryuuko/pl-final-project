package zefryuuko.chat.commdata;

import java.util.LinkedList;

public class MessagesData extends CommData
{
    private final LinkedList<ChatData> messages;

    public MessagesData(LinkedList<ChatData> messages)
    {
        super("MessagesData");
        this.messages = messages;
    }

    public LinkedList<ChatData> getMessages()
    {
        return messages;
    }
}
