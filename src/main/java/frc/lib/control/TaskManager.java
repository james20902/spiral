package frc.lib.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class TaskManager implements Runnable{
    TaskManager instance;
    ScheduledThreadPoolExecutor executor;
    ConcurrentHashMap<ScheduledFuture, Task> tasks;

    public TaskManager(){
        executor = new ScheduledThreadPoolExecutor(8);
        tasks = new ConcurrentHashMap<ScheduledFuture, Task>();
    }

    public TaskManager getInstance(){
        if(instance == null) instance = new TaskManager();
        return instance;
    }

    public void addTask(Task task) {
        ScheduledFuture s = null;
        switch(task.getType()){
            case REPEATED:
                s = executor.scheduleAtFixedRate(task, task.delay, task.period, TimeUnit.MILLISECONDS);
                break;
            case ONCE_DELAYED:
                s = executor.schedule(task, task.delay, TimeUnit.MILLISECONDS);
                break;
        }
        if(s != null) {
            tasks.put(s, task);
        }
    }

    public void run() {//TODO make sure queue matches up with the hashmap
        getInstance();
        while(!Thread.interrupted()) {
            for(ConcurrentHashMap.Entry<ScheduledFuture, Task> entry : tasks.entrySet()) {
                Task t = entry.getValue();
                if(t.running && System.currentTimeMillis() - t.startTime > t.period/2) {
                    entry.getKey().cancel(true);
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}