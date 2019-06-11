package zefryuuko.chat.client;

import zefryuuko.chat.lib.Logging;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client
{
    private final String host;
    private final int port;
    private ClientConnection clientConnection;
    private Logging logging;

    public Client(String host, int port)
    {
        this.logging = new Logging("Client-" + port);
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException
    {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 10000);
            clientConnection = new ClientConnection(socket, this);
            clientConnection.start();
            logging.log("Client connected to server");
    }

    public void sendString(String data)
    {
        clientConnection.sendString(data);
    }

    public void stop()
    {
        clientConnection.setRunning(false);
        logging.log("Client thread stopped");
    }
}
