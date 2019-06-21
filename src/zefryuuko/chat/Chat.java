package zefryuuko.chat;

import zefryuuko.chat.client.ClientMain;
import zefryuuko.chat.server.ServerMain;

public class Chat
{
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            ClientMain.main(args);
        }
        else
        {
            if (args[0].equals("servermode"))
            {
                System.out.println("Starting in server mode...");
                ServerMain.main(args);
            }
        }
    }
}
