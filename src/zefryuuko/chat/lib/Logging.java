package zefryuuko.chat.lib;

/**
 * A class used for logging messages in the terminal.
 */
public class Logging
{
    private final String moduleName;

    public Logging(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public void log(String text)
    {
        String log = String.format("[%s] %s\n", this.moduleName, text);
        System.out.print(log);
    }
}
