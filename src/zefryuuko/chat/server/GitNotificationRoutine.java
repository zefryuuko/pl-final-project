package zefryuuko.chat.server;

import sun.rmi.runtime.Log;
import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.lib.Logging;
import zefryuuko.chat.lib.RoutineInterface;
import zefryuuko.chat.lib.Utilities;

import java.util.HashMap;

public class GitNotificationRoutine implements RoutineInterface
{
    @Override
    public void action()
    {
        Logging logging = new Logging("GitNotificationRoutine");
        logging.log("Getting repo changes...");
        Main.getGit().pull();
        if (Main.getGit().checkLatestCommit())
        {
            HashMap changesData = Main.getGit().getLatestCommitData();
            String message = String.format("%s made changes to the repository.\nLatest commit summary: %s",
                                            changesData.get("author"), changesData.get("summary"));
            ChatData chatData = new ChatData("Git", message);
            Main.getServer().broadcast(Utilities.objSerialize(chatData));
            logging.log("Broadcasted repo changes.");
            return;
        }
        logging.log("No changes has been done.");
    }
}
