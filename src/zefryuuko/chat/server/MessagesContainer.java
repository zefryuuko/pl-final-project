package zefryuuko.chat.server;

import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.lib.Utilities;

import java.io.*;
import java.util.LinkedList;

/**
 * A class that handles messages history managing and saving.
 */
public class MessagesContainer
{
    private int limit = 0;
    private LinkedList<ChatData> messages;

    /**
     * MessagesContainer constructor
     * @param limit The maximum number of messages that is going to be saved.
     */
    public MessagesContainer(int limit)
    {
        this.limit = limit;
        if (Utilities.fileExists("appdata/saved-messages.msgcontainer"))
            loadMessagesFile();
        else
            this.messages = new LinkedList();
    }

    /**
     * Adds a message to the messages history.
     * @param chatData ChatData instance to be stored.
     */
    public void addMessage(ChatData chatData)
    {
        messages.addLast(chatData);
        if (messages.size() > limit) messages.removeFirst();
        saveToFile();
    }

    /**
     * Gets a list of messages.
     * @return LinkedList with ChatData that contains all messages.
     */
    public LinkedList<ChatData> getMessages()
    {
        return messages;
    }

    /**
     * Loads messages history from a file
     */
    private void loadMessagesFile()
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("appdata/saved-messages.msgcontainer"));
            String data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) data += line;
            this.messages = (LinkedList<ChatData>) Utilities.objDeserialize(data);
            while (messages.size() > limit)
            {
                messages.removeFirst();
            }
            saveToFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Saves all messages history stored in memory.
     */
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
