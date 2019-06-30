package zefryuuko.chat.server;

public class Editor extends Server
{
    private String buffer;
    private String fileName;

    /**
     * Editor container
     * Hosts a realtime text editor and stores the buffer in it
     * @param port The port it will run on
     */
    public Editor(int port)
    {
        super(port);
        buffer = "";
    }

    /**
     * Editor container
     * Hosts a realtime text editor and stores the buffer in it
     * @param port The port it will run on
     * @param startingData The data to start out with
     */
    public Editor(int port, String fileName, String startingData)
    {
        this(port);
        this.fileName = fileName;
        this.buffer = startingData;
    }

    public String getBuffer()
    {
        return buffer;
    }

    public String getFileName()
    {
        return fileName;
    }
}
