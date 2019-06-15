package zefryuuko.chat.server;

import zefryuuko.chat.commdata.ChatData;

import java.io.Serializable;
import java.util.LinkedList;

public class MessagesContainer implements Serializable
{
    private final int limit;
    private LinkedList<ChatData> messages;

    public MessagesContainer(int limit)
    {
        this.limit = limit;
        this.messages = new LinkedList();
    }

    public void addMessage(ChatData chatData)
    {
        messages.addLast(chatData);
        if (messages.size() > limit) messages.removeFirst();
    }

    public LinkedList<ChatData> getMessages()
    {
        return messages;
    }
}
