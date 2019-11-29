package frc.lib.control;

public abstract class Task implements Runnable {
    protected TaskType type;
    long delay, period;
    long startTime;
    boolean running;

    public Task(long delay, long period, TaskType type){
        this.delay = delay;
        this.period = period;
        init();
    }

    public Task(long period, TaskType type){
        this.delay = 0;
        this.period = period;
        init();
    }

    public abstract void init();

    @Override
    public void run(){
        startTime = System.currentTimeMillis();
        running = true;
        update();
        running = false;
    }

    public abstract void update();

    public void interrupted(){
        //Some logger support logs task name interrupt
    }

    public TaskType getType(){
        return type;
    }
}