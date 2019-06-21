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
                ServerMain.getMessagesContainer().addMessage((ChatData) commData);
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
                response = new ConnectedUserData(new ArrayList<String>(ServerMain.getConnectedUsers().values()));
                break;
            case "clientDisconnect":
                ServerMain.getConnectedUsers().remove(sessionID);
                broadcastConnectedUsers();
                break;
            case "getServerPropertiesData":
                boolean hasGit = ServerMain.getRepoAddress() == null ? false : true;
                response = new ServerPropertiesData(ServerMain.getServerName(), ServerMain.getServerDescription(),
                                                    hasGit, ServerMain.getRepoAddress());
                break;
            case "getAllMessages":
                response = new MessagesData(ServerMain.getMessagesContainer().getMessages());
                break;
        }

        return response;
    }

    private static void broadcastChat(ChatData chatData)
    {
        String data = Utilities.objSerialize(chatData);
        ServerMain.getServer().broadcast(data);
    }

    private static CommData getInitHandshakeDataResponse(InitHandshakeData initHandshakeData, String sessionID)
    {
        CommData response = null;
        boolean isUsernameAvailable = false;
        boolean isAuthenticated = false;
        if (!ServerMain.getConnectedUsers().values().contains(initHandshakeData.getUsername())) isUsernameAvailable = true;
        if (ServerMain.getServerPassword().equals(initHandshakeData.getPassword())) isAuthenticated = true;
        response = new InitHandshakeResponseData(isAuthenticated, isUsernameAvailable);
        if (isAuthenticated && isUsernameAvailable)
        {
            ServerMain.getConnectedUsers().put(sessionID, initHandshakeData.getUsername());
            broadcastConnectedUsers();
        }
        return response;
    }

    public static void broadcastConnectedUsers()
    {
        ArrayList<String> connectedUsers = new ArrayList();

        for (Iterator<String> itr = ServerMain.getConnectedUsers().values().iterator(); itr.hasNext(); )
        {
            connectedUsers.add(itr.next());
        }

        String data = Utilities.objSerialize(new ConnectedUserData(connectedUsers));
        ServerMain.getServer().broadcast(data);
    }
}
