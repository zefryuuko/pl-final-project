package zefryuuko.chat.client;

import zefryuuko.chat.commdata.*;

/**
 * A class that routes requests from client and processes the request accordingly.
 */
public class RequestRouter
{
    /**
     * Gets the response of the request
     * @param commData CommData received from client
     * @return CommData instance containing the response for the request.
     */
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

    /**
     * Gets the appropriate response for a RequestData.
     * @param requestData RequestData instance
     * @return CommData containing the response that will be sent to the server.
     */
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
