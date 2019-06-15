package zefryuuko.chat.commdata;

public class ServerPropertiesData extends CommData
{
    private final String serverName;
    private final String serverDescription;
    private final boolean hasGit;
    private final String gitAddress;

    public ServerPropertiesData(String serverName, String serverDescription, boolean hasGit, String gitAddress)
    {
        super("ServerPropertiesData");
        this.serverName = serverName;
        this.serverDescription = serverDescription;
        this.hasGit = hasGit;
        this.gitAddress = gitAddress;
    }

    public String getServerName()
    {
        return serverName;
    }

    public String getServerDescription()
    {
        return serverDescription;
    }

    public boolean hasGit()
    {
        return hasGit;
    }

    public String getGitAddress()
    {
        return gitAddress;
    }
}
