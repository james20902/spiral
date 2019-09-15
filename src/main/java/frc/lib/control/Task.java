package frc.lib.control;

import frc.lib.utility.Console;
import frc.lib.utility.Heartbeat;

public abstract class Task implements Runnable {

    private Heartbeat heartbeat;
    private int timing;
    private String name;

    public Task(){
        this(20);
    }

    public Task(int timing){
        this(timing, null);
    }

    public Task(String name){
        this(20, name);
    }

    public Task(int timing, String name){
        this.timing = timing;
        if(name == null){
            this.name = this.getClass().getSimpleName();
        } else {
            this.name = name;
        }
        heartbeat = new Heartbeat(this::logSlowdown, getTiming());
        init();
    }

    public int getTiming(){
        return timing;
    }

    public String getName(){
        return name;
    }

    public void init(){
        heartbeat.start();
    }

    public void run(){
        heartbeat.check();
    }

    public void logSlowdown(){
        Console.reportWarning("System " + getName() + " slowed down past expected " + getTiming() + "ms cycle!");
    }
}
