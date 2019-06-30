package zefryuuko.chat.commdata;

/**
 * A container that stores a request from client/server. Used for requests that does not need any parameters
 */
public class RequestData extends CommData
{
    private final String request;

    public RequestData(String request)
    {
        super("RequestData");
        this.request = request;
    }

    public String getRequest()
    {
        return request;
    }
}
