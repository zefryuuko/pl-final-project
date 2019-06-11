package zefryuuko.chat.commdata;

import java.util.ArrayList;

public class ConnectedUserData extends CommData
{
    private ArrayList<String> connectedUsers;

    public ConnectedUserData(ArrayList<String> connectedUsers)
    {
        super("ConnectedUserData");
        this.connectedUsers = connectedUsers;
    }

    public ArrayList<String> getConnectedUsers()
    {
        return connectedUsers;
    }
}
