package zefryuuko.chat.server;

import zefryuuko.chat.lib.Logging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Server extends Thread
{
    private Logging logging;
    private ServerSocket serverSocket;
    private HashMap<String, ServerConnection> connections;
    private int port;
    private boolean isRunning = true;

    /**
     * Server <br/>
     * Host for data transfer using TCP protocol.
     * @param port Port the server will bind to.
     */
    public Server(int port)
    {
        super("ServerThread" + port);
        this.connections = new HashMap<>();
        this.port = port;
        this.logging = new Logging("Server-" + port);
    }

    public void run()
    {
        logging.log("Server thread started.");
        try
        {
            serverSocket = new ServerSocket(port);
            while (isRunning)
            {
                // Wait for new connection from client
                Socket socket = serverSocket.accept();

                // Generate random UUID for the client
                String sessionID = UUID.randomUUID().toString();
                logging.log("Connection accepted with sessionID " + sessionID);

                // Instantiate new connection object, run the thread and store the reference on a map.
                ServerConnection serverConnection = new ServerConnection(socket, this, sessionID);
                serverConnection.start();
                connections.put(sessionID, serverConnection);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Gets a map of alive connections
     * @return HashMap with sessionID as key, ServerConnection as value.
     */
    public HashMap<String, ServerConnection> getConnections()
    {
        return connections;
    }

    public void broadcast(String message)
    {
        for (Iterator<String> itr = Main.getServer().getConnections().keySet().iterator(); itr.hasNext(); )
        {
            String sessionID = itr.next();

            Main.getServer().getConnections().get(sessionID).sendString(message);       // Send data to client
            if (!Main.getServer().getConnections().get(sessionID).isRunning())          // Delete connection if the connection is lost
            {
                itr.remove();
                Main.getConnectedUsers().remove(sessionID);
                RequestRouter.broadcastConnectedUsers();
            }
        }
    }
}
