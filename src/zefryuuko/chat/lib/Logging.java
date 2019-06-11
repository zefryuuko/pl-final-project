package zefryuuko.chat.lib;

public class Logging
{
    private final String moduleName;
    private boolean writeToFile = false;

    public Logging(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public void log(String text)
    {
        String log = String.format("[%s] %s\n", this.moduleName, text);
        System.out.print(log);
        if (writeToFile) appendToLog(log);
    }

    public void appendToLog(String text)
    {

    }

    public boolean isWriteToFile()
    {
        return writeToFile;
    }

    public void setWriteToFile(boolean writeToFile)
    {
        this.writeToFile = writeToFile;
    }
}
