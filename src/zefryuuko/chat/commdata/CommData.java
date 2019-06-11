package zefryuuko.chat.commdata;

import java.io.Serializable;

public class CommData implements Serializable
{
    private final String dataType;

    /**
     * CommData <br/>
     * A class that stores objects that will be sent through the network
     * @param dataType Type of data that is sent. Used as an identifier.
     */
    public CommData(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }
}
