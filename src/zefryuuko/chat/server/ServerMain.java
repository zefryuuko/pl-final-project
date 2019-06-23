package zefryuuko.chat.server;

import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Routine;
import zefryuuko.chat.lib.Utilities;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * ServerMain
 * A class that stores important references that are needed at
 * the runtime of the server.
 */
public class ServerMain
{
    private static HashMap<String, String> connectedUsers;
    private static Server server;
    private static String serverPassword;
    private static String repoAddress;
    private static String serverName;
    private static String serverDescription;
    private static int savedMessagesLimit;
    private static MessagesContainer messagesContainer;
    private static Git git;
    private static Routine gitNotificationRoutine;
    private static Routine pingClientsRoutine;
    private static boolean gitEnabled = true;

    public static void main(String[] args)
    {
        // Operating system check and git installation check
        if (Utilities.isWindows())
        {
            System.out.println("Detected Windows operating system. Git features will be disabled.");
            gitEnabled = false;
        }
        else if (!Utilities.isGitInstalled())
        {
            System.out.println("Git is not detected on PATH. Git features will be disabled");
            gitEnabled = false;
        }

        // Make important directories if it is not present and loads server config.
        Utilities.makeDir("appdata/repofiles");
        loadConfig();

        connectedUsers = new HashMap();
        messagesContainer = new MessagesContainer(savedMessagesLimit);
        server = new Server(5550);
        server.start();

        // Ping clients every 10 seconds to clear disconnected sessions.
        pingClientsRoutine = new Routine(new PingClientsRoutine(), 10000);
        pingClientsRoutine.start();

        // Enable git client if a repo address is specified and system requirement is met.
        if (!repoAddress.equals("") && gitEnabled)
        {
            git = new Git(repoAddress);
            gitNotificationRoutine = new Routine(new GitNotificationRoutine(), 10000);
            gitNotificationRoutine.start();
        }
    }

    private static void loadConfig()
    {
        // Check if server-config.properties exists
        if (!Utilities.fileExists("appdata/server-config.properties"))
        {
            // If it didn't, generate a new one.
            System.out.println("server-config.properties not found. Generating file...");
            try
            {
                Writer inputStream = new FileWriter("appdata/server-config.properties");
                Properties serverConfig = new Properties();
                serverConfig.setProperty("server_name", "A Chat Server");
                serverConfig.setProperty("server_description", "A regular chat server");
                serverConfig.setProperty("server_password", "");
                serverConfig.setProperty("server_git_address", "");
                serverConfig.setProperty("max_saved_messages", "50");
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
            // if it existed, read the configuration file.
            try
            {
                InputStream inputStream = new FileInputStream(new File("appdata/server-config.properties"));
                Properties properties = new Properties();
                properties.load(inputStream);
                serverName = properties.getProperty("server_name");
                serverDescription = properties.getProperty("server_description");
                serverPassword = properties.getProperty("server_password");
                repoAddress = properties.getProperty("server_git_address");
                savedMessagesLimit = Integer.parseInt(properties.getProperty("max_saved_messages"));
                if (serverName.equals(""))
                {
                    System.out.println("ERROR in server-config.properties: server_name cannot be empty.");
                    System.exit(1);
                }
                if (savedMessagesLimit == 0)
                {
                    System.out.println("ERROR in server-config.properties: max_saved_messages must be greater than 0.");
                    System.exit(1);
                }
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
