package zefryuuko.chat.server;

import zefryuuko.chat.commdata.ChatData;
import zefryuuko.chat.commdata.RequestData;
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
            HashMap<String, String> changesData = Main.getGit().getLatestCommitData();
            String description = changesData.get("description").equals("") ? "" : "<br><br>" + changesData.get("description");
            String message = String.format("[noescape]%s made changes to the repository.<div class='container'>%s%s</div>[/noescape]",
                                            changesData.get("author"), changesData.get("summary"), description);
            ChatData chatData = new ChatData("Git [Bot]", message);
            Main.getServer().broadcast(Utilities.objSerialize(chatData));
            RequestData refreshGitClient = new RequestData("refreshGitClient");
            Main.getServer().broadcast(Utilities.objSerialize(refreshGitClient));
            Main.getMessagesContainer().addMessage(chatData);
            logging.log("Broadcasted latest repo change.");
            return;
        }
        logging.log("No changes has been done.");
    }
}
