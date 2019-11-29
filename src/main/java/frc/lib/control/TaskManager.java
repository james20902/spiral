package frc.lib.control;

import java.util.Map;
import java.util.concurrent.*;


public class TaskManager implements Runnable{

    private static TaskManager instance;
    private ScheduledThreadPoolExecutor executor;
    private ConcurrentHashMap<ScheduledFuture, Task> tasks;

    public TaskManager(){
        executor = new ScheduledThreadPoolExecutor(8, new TaskFactory(), new TaskRejectionHandler());
        tasks = new ConcurrentHashMap<>();
    }

    public static TaskManager getInstance(){
        if(instance == null){
            instance = new TaskManager();
            new Thread(instance).start();
        }
        return instance;
    }

    public void addTask(Task task) {
        ScheduledFuture s;
        switch(task.getType()){
            case REPEATED:
                s = executor.scheduleAtFixedRate(task, task.delay, task.period, TimeUnit.MILLISECONDS);
                break;
            case ONCE_DELAYED:
                s = executor.schedule(task, task.delay, TimeUnit.MILLISECONDS);
                break;
            default:
                //todo something here
                throw new RuntimeException();
        }
        tasks.put(s, task);
    }

    public void run() {//TODO make sure queue matches up with the hashmap
        while(!Thread.interrupted()) {
            try {
                for(Map.Entry<ScheduledFuture, Task> entry : tasks.entrySet()) {
                    Task t = entry.getValue();
                    if(t.running && System.currentTimeMillis() - t.startTime > t.period/2) {
                        entry.getKey().cancel(true);
                    }
                }
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void disableAll(){
        executor.shutdown();
    }

    public void shutdown(){
        executor.shutdownNow();
    }
}

class TaskFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable);
    }
}

class TaskRejectionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        System.out.println("Execution failed!");
    }
}