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
                ClientMain.getMainWindow().getMainPanel().populateOnlineUsers(connectedUserData.getConnectedUsers());
                break;
            case "ServerPropertiesData":
                ClientMain.getMainWindow().getMainPanel().populateServerProperties((ServerPropertiesData) commData);
                break;
            case "ChatData":
                ClientMain.getMainWindow().getMainPanel().addMessage((ChatData) commData);
                break;
            case "InitHandshakeResponseData":
                InitHandshakeResponseData initHandshakeResponseData = (InitHandshakeResponseData) commData;
                int finalConnectionStatus = 1;
                if (!initHandshakeResponseData.isUsernameValid())
                    finalConnectionStatus = 3;
                if (!initHandshakeResponseData.isAuthenticated())
                    finalConnectionStatus = 2;
                ClientMain.getMainWindow().getConnectPanel().setConnectionStatus(finalConnectionStatus);
                break;
            case "MessagesData":
                ClientMain.getMainWindow().getMainPanel().populateMessages(((MessagesData) commData).getMessages());
                break;
            case "RequestData":
                response = getRequestDataResponse((RequestData) commData);
                break;
        }

        return response;
    }

    private static CommData getRequestDataResponse(RequestData requestData)
    {
        CommData response = new CommData("NullResponse");

        switch(requestData.getRequest())
        {
            case "refreshGitClient":
                ClientMain.getMainWindow().getMainPanel().refreshGit();
                break;
        }

        return response;
    }
}
