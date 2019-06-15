package zefryuuko.chat.client;

import zefryuuko.chat.commdata.*;

public class RequestRouter
{
    public static CommData getResponse(CommData commData)
    {
        CommData response = new CommData("NullResponse");
        String dataType = commData.getDataType();

        switch (dataType)
        {
            case "ConnectedUserData":
                ConnectedUserData connectedUserData = (ConnectedUserData) commData;
                Main.getMainWindow().getMainPanel().populateOnlineUsers(connectedUserData.getConnectedUsers());
                break;
            case "ServerPropertiesData":
                Main.getMainWindow().getMainPanel().populateServerProperties((ServerPropertiesData) commData);
                break;
            case "ChatData":
                Main.getMainWindow().getMainPanel().addMessage((ChatData) commData);
                break;
            case "InitHandshakeResponseData":
                InitHandshakeResponseData initHandshakeResponseData = (InitHandshakeResponseData) commData;
                int finalConnectionStatus = 1;
                if (!initHandshakeResponseData.isUsernameValid())
                    finalConnectionStatus = 3;
                if (!initHandshakeResponseData.isAuthenticated())
                    finalConnectionStatus = 2;
                Main.getMainWindow().getConnectPanel().setConnectionStatus(finalConnectionStatus);
                break;
            case "MessagesData":
                Main.getMainWindow().getMainPanel().populateMessages(((MessagesData) commData).getMessages());
                break;
        }

        return response;
    }
}
