package zefryuuko.chat.commdata;

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
