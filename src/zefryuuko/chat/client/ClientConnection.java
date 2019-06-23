package zefryuuko.chat.client;

import zefryuuko.chat.commdata.CommData;
import zefryuuko.chat.lib.Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread
{
    private Socket socket;
    private Client client;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private boolean isRunning = true;

    public ClientConnection(Socket socket, Client client)
    {
        this.socket = socket;
        this.client = client;
    }

    public void sendString(String data)
    {
        try
        {
            dataOutputStream.writeUTF(data);
            dataOutputStream.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (isRunning)
            {
                // Sleep for 1ms when there is no data to ease up CPU usage
                while (dataInputStream.available() == 0) Thread.sleep(1);

                // TODO: add message processing methods
                String receivedData = dataInputStream.readUTF();

                CommData response = RequestRouter.getResponse((CommData) Utilities.objDeserialize(receivedData));
                if (!response.getDataType().equals("NullResponse"))
                {
                    sendString(Utilities.objSerialize(response));
                }

            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            close();
        }
    }

    public void close()
    {
        try
        {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setRunning(boolean running)
    {
        isRunning = running;
    }

    public boolean isRunning()
    {
        return isRunning;
    }
}
