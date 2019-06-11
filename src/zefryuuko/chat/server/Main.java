package zefryuuko.chat.server;

import zefryuuko.chat.lib.Git;
import zefryuuko.chat.lib.Routine;
import zefryuuko.chat.lib.Utilities;

import java.util.HashMap;

public class Main
{
    private static HashMap<String, String> connectedUsers;
    private static Server server;
    private static String serverPassword = "test";
    private static String repoAddress = "https://github.com/zefryuuko/useless-repository.git";
    private static Git git;
    private static Routine gitNotificationRoutine;

    public static void main(String[] args)
    {
        Utilities.makeDir("appdata/repofiles");

        connectedUsers = new HashMap();
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
}
