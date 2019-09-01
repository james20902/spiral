package frc.lib.control;

public abstract class Task implements Runnable {

    //todo, make this class hold more data about the task (maybe watchdog). maybe have an abstract logslowdown function here so child has to do it

    public Task(){
        init();
    }

    public abstract void init();

    public abstract void run();
}
