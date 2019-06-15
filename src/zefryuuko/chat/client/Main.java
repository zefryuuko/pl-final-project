package zefryuuko.chat.client;

import zefryuuko.chat.commdata.RequestData;
import zefryuuko.chat.lib.Utilities;

public class Main
{
    private static Client client;
    private static MainWindow mainWindow;
    private static String clientUsername;

    public static void main(String[] args)
    {
        mainWindow = new MainWindow();
        mainWindow.setVisible(true);
//        client = new Client("localhost", 5550);
//        client.run();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            if (client != null && client.isRunning())
            {
                RequestData disconnectRequest = new RequestData("clientDisconnect");
                client.sendString(Utilities.objSerialize(disconnectRequest));
            }
            }
        });
    }

    public static Client getClient()
    {
        return client;
    }

    public static void setClient(Client client)
    {
        Main.client = client;
    }

    public static MainWindow getMainWindow()
    {
        return mainWindow;
    }

    public static String getClientUsername()
    {
        return clientUsername;
    }

    public static void setClientUsername(String clientUsername)
    {
        Main.clientUsername = clientUsername;
    }
}
