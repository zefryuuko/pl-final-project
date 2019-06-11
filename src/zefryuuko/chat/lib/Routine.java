package zefryuuko.chat.lib;

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

    public void stopRunning()
    {
        this.isRunning = false;
    }
}
