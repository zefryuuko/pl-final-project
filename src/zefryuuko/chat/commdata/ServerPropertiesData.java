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
}
