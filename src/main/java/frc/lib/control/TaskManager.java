package frc.lib.control;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {

    private static TaskManager instance;

    public static TaskManager getInstance(){
        if(instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    private final ScheduledThreadPoolExecutor executor;

    private TaskManager(){
        executor = new ScheduledThreadPoolExecutor(8);
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
    }

    public void executeTask(Task task){
        scheduleTask(task, 1);
    }

    public void scheduleTask(Task task, int delay){
        scheduleTask(task, delay, TimeUnit.MILLISECONDS);
    }

    public void scheduleTask(Task task, int delay, TimeUnit unit){
        executor.schedule(task, delay, unit);
    }

    public void schedulePeriodicTask(Task task){
        schedulePeriodicTask(task, 20);
    }

    public void schedulePeriodicTask(Task task, int executionTime){
        schedulePeriodicTask(task, executionTime, TimeUnit.MILLISECONDS);
    }

    public void schedulePeriodicTask(Task task, int executionTime, TimeUnit unit){
        executor.scheduleWithFixedDelay(task, 0, executionTime, unit);
    }

    public void removeTask(Runnable r){
        executor.remove(r);
    }

    public void shutdown(){
        executor.shutdownNow();
    }

    @Override
    public String toString(){
        return executor.getQueue().toString();
    }

}
