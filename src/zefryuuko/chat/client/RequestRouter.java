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
                Main.getWindow().getMainPanel().populateOnlineUsers(connectedUserData.getConnectedUsers());
                break;
            case "ChatData":
                Main.getWindow().getMainPanel().addMessage((ChatData) commData);
                break;
            case "InitHandshakeResponseData":
                InitHandshakeResponseData initHandshakeResponseData = (InitHandshakeResponseData) commData;
                int finalConnectionStatus = 1;
                if (!initHandshakeResponseData.isUsernameValid())
                    finalConnectionStatus = 3;
                if (!initHandshakeResponseData.isAuthenticated())
                    finalConnectionStatus = 2;
                Main.getWindow().getConnectPanel().setConnectionStatus(finalConnectionStatus);
                break;
        }

        return response;
    }
}
