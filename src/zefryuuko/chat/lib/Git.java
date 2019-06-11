package zefryuuko.chat.lib;

import java.util.HashMap;

public class Git
{
    private Logging logging;
    private String repoAddress;
    private String latestCommit;
    private static String reposDir = "appdata/repofiles/";
    private String currentRepoDir;
    private String fullDir;

    public Git(String repoAddress)
    {
        this.logging = new Logging("Git");
        this.repoAddress = repoAddress;
        this.latestCommit = "";
        this.currentRepoDir = repoAddress.replaceAll("[^A-Za-z0-9_-]", "");
        this.fullDir = reposDir + currentRepoDir;
        if (Utilities.dirExists(fullDir))
        {
            if (!Utilities.dirExists((fullDir + "/.git")))
            {
                logging.log("Directory " + currentRepoDir + " does not contain .git directory");
                logging.log("Deleting all contents on " + currentRepoDir);
                Utilities.deleteDir(fullDir);
                this.cloneRemote();
            }
            else
            {
                this.pull();
                this.checkLatestCommit();
            }
        }
        else
        {
            // If repository folder doesnt exist
            logging.log("Repo does not exists locally. Cloning from " + repoAddress);
            this.cloneRemote();
        }
    }

    public void pull()
    {
        logging.log("Pulling from remote");
        String command = "git pull";
        String output = Utilities.runSystemCommand(command, this.fullDir);
        output = output.substring(0, output.length() - 2);
        for (String o : output.split("\n"))
        {
            logging.log(o);
        }
    }

    public boolean checkLatestCommit()
    {
        String command = "git rev-parse origin/master";
        String commandOutput = Utilities.runSystemCommand(command, fullDir);
        if (this.latestCommit.equals(commandOutput))
        {
            return false;
        }
        else
        {
            this.latestCommit = commandOutput;
            return true;
        }
    }

    public HashMap<String, String> getLatestCommitData()
    {
        logging.log("Getting latest commit data");
        HashMap<String, String> output = new HashMap();

        String command = "git show -p origin/master";
        System.out.println(Utilities.runSystemCommand(command, fullDir));
        String[] commandOutput = Utilities.runSystemCommand(command, fullDir).split("\n");

        output.put("author", commandOutput[1].substring(8, commandOutput[1].indexOf("<") - 1));
        output.put("summary", commandOutput[4].replaceAll("    ", ""));

        return output;
    }

    private void cloneRemote()
    {
        logging.log("Cloning " + repoAddress + " to " + fullDir);
        String command = String.format("git clone %s %s", repoAddress, currentRepoDir);
        String output = Utilities.runSystemCommand(command, reposDir);
        logging.log("Clone command ran successfully.");
    }

}
