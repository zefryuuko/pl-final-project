package zefryuuko.chat.server;

import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.commdata.RequestData;
import zefryuuko.chat.lib.Logging;
import zefryuuko.chat.lib.RoutineInterface;
import zefryuuko.chat.lib.Utilities;

import java.util.HashMap;

/**
 * A routine that checks git updates and broadcasts the notification
 * to all clients.
 */
public class GitNotificationRoutine implements RoutineInterface
{
    @Override
    public void action()
    {
        Logging logging = new Logging("GitNotificationRoutine");
        logging.log("Getting repo changes...");
        ServerMain.getGit().pull();                     // Pull repository data
        if (ServerMain.getGit().checkLatestCommit())    // Check if the latest commit is different from the commit before checking.
        {
            HashMap<String, String> changesData = ServerMain.getGit().getLatestCommitData();
            String description = changesData.get("description").equals("") ? "" : "<br><br>" + changesData.get("description");
            String message = String.format("[noescape]%s made changes to the repository.<div class='container'>%s%s</div>[/noescape]",
                                            changesData.get("author"), changesData.get("summary"), description);
            ChatData chatData = new ChatData("Git [Bot]", message);
            ServerMain.getServer().broadcast(Utilities.objSerialize(chatData));
            RequestData refreshGitClient = new RequestData("refreshGitClient");
            ServerMain.getServer().broadcast(Utilities.objSerialize(refreshGitClient));
            ServerMain.getMessagesContainer().addMessage(chatData);
            logging.log("Broadcasted latest repo change.");
            return;
        }
        logging.log("No changes has been done.");
    }
}
