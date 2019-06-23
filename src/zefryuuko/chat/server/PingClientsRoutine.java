package zefryuuko.chat.server;

import zefryuuko.chat.commdata.RequestData;
import zefryuuko.chat.lib.RoutineInterface;
import zefryuuko.chat.lib.Utilities;

/**
 * A routine that pings all clients to check it's connection status.
 * A connection thread will remove itself when connection is lost.
 */
public class PingClientsRoutine implements RoutineInterface
{
    private String pingData = Utilities.objSerialize(new RequestData("ping"));
    @Override
    public void action()
    {
        ServerMain.getServer().broadcast(pingData);
    }
}
