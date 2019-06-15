package zefryuuko.chat.server;

import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.lib.Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class MessagesContainer
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
        saveToFile();
    }

    public LinkedList<ChatData> getMessages()
    {
        return messages;
    }

    private void saveToFile()
    {
        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("appdata/saved-messages.msgcontainer"));
            bufferedWriter.write(Utilities.objSerialize(this.messages));
            bufferedWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
