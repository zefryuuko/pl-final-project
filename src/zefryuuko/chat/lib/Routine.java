package zefryuuko.chat.lib;

/**
 * A class that handles actions that has to be done in n time.
 */
public class Routine extends Thread
{
    private int interval;
    private RoutineInterface routine;
    private boolean isRunning;

    public Routine(RoutineInterface routine, int interval)
    {
        this.interval = interval;
        this.routine = routine;
        this.isRunning = false;
    }

    public void run()
    {
        this.isRunning = true;
        while (isRunning)
        {
            try
            {
                Thread.sleep(interval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            if (isRunning)
            {
                routine.action();
            }
        }
    }

    /**
     * Stops the routine.
     */
    public void stopRunning()
    {
        this.isRunning = false;
    }
}
