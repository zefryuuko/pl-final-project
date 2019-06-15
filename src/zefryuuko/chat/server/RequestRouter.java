package zefryuuko.chat.server;

import zefryuuko.chat.commdata.*;
import zefryuuko.chat.lib.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

public class RequestRouter
{
    public static CommData getResponse(CommData commData, String sessionID)
        {
        CommData response = new CommData("NullResponse");
        String dataType = commData.getDataType();

        switch(dataType)
        {
            case "RequestData":
                response = getRequestDataResponse((RequestData) commData, sessionID);
                break;
            case "ChatData":
                Main.getMessagesContainer().addMessage((ChatData) commData);
                broadcastChat((ChatData) commData);
                break;
            case "InitHandshakeData":
                response = getInitHandshakeDataResponse((InitHandshakeData) commData, sessionID);
                break;
        }

        return response;
    }

    private static CommData getRequestDataResponse(RequestData requestData, String sessionID)
    {
        CommData response = new CommData("NullResponse");

        switch(requestData.getRequest())
        {
            case "getConnectedUserData":
                response = new ConnectedUserData(new ArrayList<String>(Main.getConnectedUsers().values()));
                break;
            case "clientDisconnect":
                Main.getConnectedUsers().remove(sessionID);
                broadcastConnectedUsers();
                break;
            case "getServerPropertiesData":
                boolean hasGit = Main.getRepoAddress() == null ? false : true;
                response = new ServerPropertiesData(Main.getServerName(), Main.getServerDescription(),
                                                    hasGit, Main.getRepoAddress());
        }

        return response;
    }

    private static void broadcastChat(ChatData chatData)
    {
        String data = Utilities.objSerialize(chatData);
        Main.getServer().broadcast(data);
    }

    private static CommData getInitHandshakeDataResponse(InitHandshakeData initHandshakeData, String sessionID)
    {
        CommData response = null;
        boolean isUsernameAvailable = false;
        boolean isAuthenticated = false;
        if (!Main.getConnectedUsers().values().contains(initHandshakeData.getUsername())) isUsernameAvailable = true;
        if (Main.getServerPassword().equals(initHandshakeData.getPassword())) isAuthenticated = true;
        response = new InitHandshakeResponseData(isAuthenticated, isUsernameAvailable);
        if (isAuthenticated && isUsernameAvailable)
        {
            Main.getConnectedUsers().put(sessionID, initHandshakeData.getUsername());
            broadcastConnectedUsers();
        }
        return response;
    }

    public static void broadcastConnectedUsers()
    {
        ArrayList<String> connectedUsers = new ArrayList();

        for (Iterator<String> itr = Main.getConnectedUsers().values().iterator(); itr.hasNext(); )
        {
            connectedUsers.add(itr.next());
        }

        String data = Utilities.objSerialize(new ConnectedUserData(connectedUsers));
        Main.getServer().broadcast(data);
    }
}
