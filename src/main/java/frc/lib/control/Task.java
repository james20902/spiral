package frc.lib.control;


import edu.wpi.first.wpilibj.Watchdog;

public abstract class Task implements Runnable {

    //todo, make this class hold more data about the task.
    Watchdog thog;

    public Task(){
        init();
        thog = new Watchdog(25, this::logSlowdown);
        thog.enable();
    }

    public abstract void init();

    public abstract void run();

    public abstract void logSlowdown();
}
