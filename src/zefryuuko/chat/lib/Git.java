package zefryuuko.chat.lib;

import java.util.HashMap;

/**
 * An interface that connects git cli with Java program
 */
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

    /**
     * Runs 'git pull' command
     */
    public void pull()
    {
        logging.log("Pulling from remote");
        String command = "git pull";
        String output = Utilities.runSystemCommand(command, this.fullDir);
        output = output.substring(0, (output.length() >= 2 ? output.length() - 2 : 0));
        for (String o : output.split("\n"))
        {
            logging.log(o);
        }
    }

    /**
     * Compares the last checked commit with the current commit.
     * @return True if changes has been made
     */
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

    /**
     * Gets the latest commit data by running 'git show -p origin/master' command
     * @return
     */
    public HashMap<String, String> getLatestCommitData()
    {
        logging.log("Getting latest commit data");
        HashMap<String, String> output = new HashMap();

        String command = "git show -p origin/master";
        System.out.println(Utilities.runSystemCommand(command, fullDir));
        String[] commandOutput = Utilities.runSystemCommand(command, fullDir).split("\n");
        String summary = "";
        String description = "";

        // Get summary
        int currentLine = 4;
        while (true)
        {
            summary += commandOutput[currentLine].replaceAll("    ", "") + "\n";
            currentLine++;
            if (commandOutput[currentLine].equals("    ") || commandOutput[currentLine].equals("")) break;
        }
        summary = summary.substring(0, summary.length() - 1);

        // Get description
        currentLine++;
        if (commandOutput[currentLine].startsWith("    "))
        {
            while (true)
            {
                description += commandOutput[currentLine].replaceAll("    ", "") + "\n";
                currentLine++;
                if (commandOutput[currentLine].equals("")) break;
            }
        }
        description = description.substring(0, description.length() > 0 ? description.length() - 1 : 0);

        output.put("author", commandOutput[1].substring(8, commandOutput[1].indexOf("<") - 1));
        output.put("summary", summary);
        output.put("description", description);

        return output;
    }

    /**
     * Clones a repository.
     */
    private void cloneRemote()
    {
        logging.log("Cloning " + repoAddress + " to " + fullDir);
        String command = String.format("git clone %s %s", repoAddress, currentRepoDir);
        String output = Utilities.runSystemCommand(command, reposDir);
        logging.log("Clone command ran successfully.");
    }

    /**
     * Gets full directory of the repository's working directory.
     * @return
     */
    public String getFullDir()
    {
        return fullDir;
    }
}
