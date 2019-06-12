package zefryuuko.chat.commdata;

public class ServerPropertiesData extends CommData
{
    private final String serverName;
    private final boolean hasGit;
    private final String gitAddress;

    public ServerPropertiesData(String serverName, boolean hasGit, String gitAddress)
    {
        super("ServerPropertiesData");
        this.serverName = serverName;
        this.hasGit = hasGit;
        this.gitAddress = gitAddress;
    }
}
