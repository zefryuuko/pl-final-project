package zefryuuko.chat.commdata;

/**
 * A container that stores information for login
 */
public class InitHandshakeData extends CommData
{
    private final String username;
    private final String password;

    public InitHandshakeData(String username, String password)
    {
        super("InitHandshakeData");
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
