package zefryuuko.chat.lib;

import java.io.*;
import java.nio.file.Paths;
import java.util.Base64;

public class Utilities
{
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

    public static boolean fileExists(String path)
    {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    public static boolean deleteFile(String path)
    {
        File file = new File(path);
        return file.delete();
    }

    public static boolean dirExists(String path)
    {
        if (!Paths.get(path).toFile().isDirectory()) return false;
        return true;
    }

    public static boolean makeDir(String path)
    {
        File dir = new File(path);
        return dir.mkdirs();
    }

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

    public static File[] listFiles(String directory, String extension)
    {
        File folder = new File(directory);
        FilenameFilter fileNameFilter = (File dir, String name) ->
        {
                return name.endsWith(extension);
        };
        return folder.listFiles(fileNameFilter);
    }

    public static boolean isGitInstalled()
    {
        if (!isWindows())
            return runSystemCommand("which git").equals("") ? false : true;
        else
            return runSystemCommand("where git").equals("INFO Could not find files for the given pattern(s).\n") ? false : true;
    }

    public static boolean isWindows()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

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
