package zefryuuko.chat.server;

import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Routine;
import zefryuuko.chat.lib.Utilities;

import java.util.HashMap;

public class ServerMain
{
    private static HashMap<String, String> connectedUsers;
    private static Server server;
    private static String serverPassword = "test";
    private static String repoAddress = "https://github.com/zefryuuko/useless-repository.git";
    private static String serverName = "Zef's Server";
    private static String serverDescription = "A server for testing purposes.";
    private static int savedMessagesLimit = 20;
    private static MessagesContainer messagesContainer;
    private static Git git;
    private static Routine gitNotificationRoutine;

    public static void main(String[] args)
    {
        Utilities.makeDir("appdata/repofiles");

        connectedUsers = new HashMap();
        messagesContainer = new MessagesContainer(savedMessagesLimit);
        server = new Server(5550);
        server.start();
        git = new Git(repoAddress);
        gitNotificationRoutine = new Routine(new GitNotificationRoutine(), 10000);
        gitNotificationRoutine.start();
    }

    public static Server getServer()
    {
        return server;
    }

    public static String getServerPassword()
    {
        return serverPassword;
    }

    public static HashMap<String, String> getConnectedUsers()
    {
        return connectedUsers;
    }

    public static Git getGit()
    {
        return git;
    }

    public static String getRepoAddress()
    {
        return repoAddress;
    }

    public static void setRepoAddress(String repoAddress)
    {
        ServerMain.repoAddress = repoAddress;
    }

    public static String getServerName()
    {
        return serverName;
    }

    public static void setServerName(String serverName)
    {
        ServerMain.serverName = serverName;
    }

    public static String getServerDescription()
    {
        return serverDescription;
    }

    public static void setServerDescription(String serverDescription)
    {
        ServerMain.serverDescription = serverDescription;
    }

    public static MessagesContainer getMessagesContainer()
    {
        return messagesContainer;
    }
}
