package zefryuuko.chat.server;

import zefryuuko.chat.commdata.CommData;
import zefryuuko.chat.lib.Logging;
import zefryuuko.chat.lib.Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * A class that manages connection with a single user.
 */
public class ServerConnection extends Thread
{
    private Logging logging;
    private Socket socket;
    private Server server;
    private final String sessionID;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private boolean isRunning = true;

    /**
     * ServerConnection <br/>
     * Handles communication with a single client.
     * @param socket Socket instance
     * @param server Server instance
     */
    public ServerConnection(Socket socket, Server server, String sessionID)
    {
        super("NServerConnection");
        this.socket = socket;
        this.server = server;
        this.sessionID = sessionID;
        this.logging = new Logging("ServerConnection");
    }

    /**
     * Sends data to the client
     * @param data The data to be sent to client in byte array
     */
    public void sendString(String data)
    {
        try
        {
            dataOutputStream.writeUTF(data);    // Send data to server
            dataOutputStream.flush();           // Flush the data output stream.
        }
        catch (SocketException e)
        {
            // Stop running if connection is lost
            logging.log("Session " + this.sessionID + " closed.");
            this.isRunning = false;
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

                // Read incoming data
                String receivedData = dataInputStream.readUTF();

                // Get response for client request
                CommData response = RequestRouter.getResponse((CommData) Utilities.objDeserialize(receivedData), sessionID);
                if (!response.getDataType().equals("NullResponse"))
                {
                    this.sendString(Utilities.objSerialize(response));
                }
            }

            // Close streams when connection is lost
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isRunning()
    {
        return isRunning;
    }
}
