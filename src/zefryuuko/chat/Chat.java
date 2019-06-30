package zefryuuko.chat;

import zefryuuko.chat.client.ClientMain;
import zefryuuko.chat.server.ServerMain;

/**
 * A class that determines which mode will the program run on.
 */
public class Chat
{
    public static void main(String[] args)
    {
        if (args.length == 0)                                       // Run client mode if no args are passed
        {
            System.out.println("Starting in client mode...");
            ClientMain.main(args);
        }
        else
        {
            if (args[0].equals("servermode"))                       // Run server mode if servermode is passed as an arg
            {
                System.out.println("Starting in server mode...");
                ServerMain.main(args);
            }
        }
    }
}
