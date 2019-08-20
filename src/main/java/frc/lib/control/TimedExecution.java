package frc.lib.control;

import frc.lib.utility.SystemClock;

public class TimedExecution {

    private double delay;
    private double target;
    private Runnable callback;

    public TimedExecution(double time, Runnable callback){
        delay = time;
        this.callback = callback;
    }

    public void execute(){
        if(SystemClock.getSystemTime() > target){
            callback.run();
            target = SystemClock.getSystemTime() + delay;
        }
    }



}
