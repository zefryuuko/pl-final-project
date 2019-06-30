package zefryuuko.chat.commdata;

/**
 * A container that is used to stored authentication status
 */
public class InitHandshakeResponseData extends CommData
{
    private final boolean isAuthenticated;
    private final boolean isUsernameAvailable;

    public InitHandshakeResponseData(boolean isAuthenticated, boolean isUsernameAvailable)
    {
        super("InitHandshakeResponseData");
        this.isAuthenticated = isAuthenticated;
        this.isUsernameAvailable = isUsernameAvailable;
    }

    public boolean isAuthenticated()
    {
        return isAuthenticated;
    }

    public boolean isUsernameValid()
    {
        return isUsernameAvailable;
    }
}
