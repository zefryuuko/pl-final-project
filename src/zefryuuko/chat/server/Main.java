package zefryuuko.chat.server;

import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Utilities;

import java.util.HashMap;

public class Main
{
    private static HashMap<String, String> connectedUsers;
    private static Server server;
    private static String serverPassword = "test";
    private static String repoAddress = "https://github.com/zefryuuko/useless-repository.git";
    private static Git git;

    public static void main(String[] args)
    {
        Utilities.makeDir("appdata/repofiles");
        git = new Git(repoAddress);
        connectedUsers = new HashMap();
        server = new Server(5550);
        server.start();
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
}
