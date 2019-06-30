package zefryuuko.chat.lib;

import java.io.*;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * A class that is used for commonly used methods
 */
public class Utilities
{
    /**
     * Deserializes object from an encoded bytes in base64
     * @param s Base64 encoded serialized object
     * @return Deserialized object
     */
    public static Object objDeserialize(String s)
    {
        try
        {
            byte[] data = Base64.getDecoder().decode(s);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object obj = ois.readObject();
            ois.close();
            return obj;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /**
     * Serializes object to bytes encoded in base64
     * @param obj Object to be serialized
     * @return Bytes encoded in base64
     */
    public static String objSerialize(Serializable obj)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /**
     * Runs a command in the terminal
     * @param command Command to run
     * @return The output of the command
     */
    public static String runSystemCommand(String command)
    {
        try
        {
            Process proc = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String output = "";
            String currentLine = "";
            while ((currentLine = reader.readLine()) != null)
            {
                output += currentLine + "\n";
            }
            return output;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Runs a command in the terminal with the specified working directory
     * @param command Command to run
     * @param workingDir Directory the command will run on
     * @return The output of the command
     */
    public static String runSystemCommand(String command, String workingDir)
    {
        try
        {
            Process proc = Runtime.getRuntime().exec(command, null, new File(workingDir));
//            ProcessBuilder procBuilder = new ProcessBuilder(new String[] {"/bin/bash", "-c" , "'" + command + "'"});
//            procBuilder.directory(new File(workingDir));
//            Process proc = procBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String output = "";
            String currentLine = "";
            while ((currentLine = reader.readLine()) != null)
            {
                output += currentLine + "\n";
            }
            return output;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if a file exists
     * @param path File path
     * @return True if file exists
     */
    public static boolean fileExists(String path)
    {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * Deletes a file
     * @param path File path
     * @return True if deleted successfully
     */
    public static boolean deleteFile(String path)
    {
        File file = new File(path);
        return file.delete();
    }

    /**
     * Checks if a directory exists
     * @param path Directory path
     * @return True if directory exists
     */
    public static boolean dirExists(String path)
    {
        if (!Paths.get(path).toFile().isDirectory()) return false;
        return true;
    }

    /**
     * Creates a directory at the specified path
     * @param path Directory path
     * @return True if directory is created successfully
     */
    public static boolean makeDir(String path)
    {
        File dir = new File(path);
        return dir.mkdirs();
    }

    /**
     * Deletes a directory with all of it's content
     * @param path Directory path
     * @return True if directory is deleted
     */
    public static boolean deleteDir(String path)
    {
        if (!dirExists(path)) return false;
        File dir = new File(path);
        String[]entries = dir.list();
        for(String s : entries)
        {
            File currentFile = new File(dir.getPath(),s);
            currentFile.delete();
        }
        return true;
    }

    /**
     * Gets a list of files and directories in a directory.
     * @param directory Directory path
     * @param extension File extension filter
     * @return Array of files
     */
    public static File[] listFiles(String directory, String extension)
    {
        File folder = new File(directory);
        FilenameFilter fileNameFilter = (File dir, String name) ->
        {
                return name.endsWith(extension);
        };
        return folder.listFiles(fileNameFilter);
    }

    /**
     * Checks if git is on system PATH
     * @return
     */
    public static boolean isGitInstalled()
    {
        if (!isWindows())
            return runSystemCommand("which git").equals("") ? false : true;
        else
            return runSystemCommand("where git").equals("INFO Could not find files for the given pattern(s).\n") ? false : true;
    }

    /**
     * Checks if operating system is Windows
     * @return True if operating system is Windows
     */
    public static boolean isWindows()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("win") >= 0);
    }

    /**
     * Checks if operating system is macOS
     * @return True if operating system is macOS
     */
    public static boolean isMac()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("mac") >= 0);

    }

    /**
     * Checks if operating system is based on UNIX
     * @return True if operating system is based on UNIX
     */
    public static boolean isUnix()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    /**
     * Check if operating system is Solaris
     * @return True if operating system is Solaris
     */
    public static boolean isSolaris()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("sunos") >= 0);
    }
}

// References
// - Object-String serialization: https://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
// - Running system commands    : https://stackoverflow.com/questions/15356405/how-to-run-a-command-at-terminal-from-java-program
// - Delete Directory           : https://stackoverflow.com/questions/20281835/how-to-delete-a-folder-with-files-using-java
// - Operating System Detection : https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
