package zefryuuko.chat.server;

import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.lib.Utilities;

import java.io.*;
import java.util.LinkedList;

public class MessagesContainer
{
    private final int limit;
    private LinkedList<ChatData> messages;

    public MessagesContainer(int limit)
    {
        this.limit = limit;
        if (Utilities.fileExists("appdata/saved-messages.msgcontainer"))
            loadMessagesFile();
        else
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

    private void loadMessagesFile())
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("appdata/saved-messages.msgcontainer"));
            String data = "";
            while ((data += bufferedReader.readLine()) != null);
            this.messages = (LinkedList<ChatData>) Utilities.objDeserialize(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
