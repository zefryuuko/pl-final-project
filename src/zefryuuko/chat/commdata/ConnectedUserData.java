package zefryuuko.chat.commdata;

import java.util.ArrayList;

/**
 * A message container that stores currently active users
 */
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
