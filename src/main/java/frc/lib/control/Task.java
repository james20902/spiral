package frc.lib.control;

public abstract class Task implements Runnable {

    //to do, make this class hold more data about the task (maybe watchdog)

    public Task(){
        init();
    }

    public abstract void init();

    public abstract void run();
}
