package zefryuuko.chat.server;

import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Routine;
import zefryuuko.chat.lib.Utilities;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class ServerMain
{
    private static HashMap<String, String> connectedUsers;
    private static Server server;
    private static String serverPassword;
    private static String repoAddress;
    private static String serverName;
    private static String serverDescription;
    private static int savedMessagesLimit = 20;
    private static MessagesContainer messagesContainer;
    private static Git git;
    private static Routine gitNotificationRoutine;

    public static void main(String[] args)
    {
        Utilities.makeDir("appdata/repofiles");
        loadConfig();

        connectedUsers = new HashMap();
        messagesContainer = new MessagesContainer(savedMessagesLimit);
        server = new Server(5550);
        server.start();
        if (!repoAddress.equals(""))
        {
            git = new Git(repoAddress);
            gitNotificationRoutine = new Routine(new GitNotificationRoutine(), 10000);
            gitNotificationRoutine.start();
        }
    }

    private static void loadConfig()
    {
        // Check server-config.properties
        if (!Utilities.fileExists("appdata/server-config.properties"))
        {
            System.out.println("server-config.properties not found. Generating file...");
            try
            {
                Writer inputStream = new FileWriter("appdata/server-config.properties");
                Properties serverConfig = new Properties();
                serverConfig.setProperty("server_name", "A Chat Server");
                serverConfig.setProperty("server_description", "A regular chat server");
                serverConfig.setProperty("server_password", "");
                serverConfig.setProperty("server_git_address", "");
                serverConfig.store(inputStream, "Server properties");
            }
            catch (IOException e)
            {
                System.out.println("Failed to generate server-config.properties\nDetails: " + e);
            }
            System.exit(0);
        }
        else
        {
            try
            {
                InputStream inputStream = new FileInputStream(new File("appdata/server-config.properties"));
                Properties properties = new Properties();
                properties.load(inputStream);
                serverName = properties.getProperty("server_name");
                serverDescription = properties.getProperty("server_description");
                serverPassword = properties.getProperty("server_password");
                repoAddress = properties.getProperty("server_git_address");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
