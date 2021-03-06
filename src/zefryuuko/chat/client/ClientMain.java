package zefryuuko.chat.client;

import zefryuuko.chat.commdata.RequestData;
import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Utilities;

/**
 * The main class that is responsible for the client
 */
public class ClientMain
{
    private static Client client;
    private static MainWindow mainWindow;
    private static String clientUsername;
    private static boolean serverHasGit;
    private static String serverGitAddress;
    private static Git git;

    public static void main(String[] args)
    {
        mainWindow = new MainWindow();
        mainWindow.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            if (client != null && client.isRunning())
            {
                RequestData disconnectRequest = new RequestData("clientDisconnect");
                client.sendString(Utilities.objSerialize(disconnectRequest));
            }
            }
        });
    }

    public static Client getClient()
    {
        return client;
    }

    public static void setClient(Client client)
    {
        ClientMain.client = client;
    }

    public static MainWindow getMainWindow()
    {
        return mainWindow;
    }

    public static String getClientUsername()
    {
        return clientUsername;
    }

    public static void setClientUsername(String clientUsername)
    {
        ClientMain.clientUsername = clientUsername;
    }

    public static String getServerGitAddress()
    {
        return serverGitAddress;
    }

    public static void setServerGitAddress(String serverGitAddress)
    {
        ClientMain.serverGitAddress = serverGitAddress;
    }

    public static boolean isServerHasGit()
    {
        return serverHasGit;
    }

    public static void setServerHasGit(boolean serverHasGit)
    {
        ClientMain.serverHasGit = serverHasGit;
    }

    public static Git getGit()
    {
        return git;
    }

    public static void setGit(Git git)
    {
        ClientMain.git = git;
    }
}
