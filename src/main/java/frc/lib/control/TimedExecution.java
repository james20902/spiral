package frc.lib.control;

import frc.lib.utility.SystemClock;

public class TimedExecution {

    public enum timeMode{
        RELATIVE, ABSOLUTE
    }

    private double time;
    private timeMode mode;
    private double checkpoint;
    private boolean reset = true;
    private Runnable callback;

    public TimedExecution(timeMode mode, double time, Runnable callback){
        this.mode = mode;
        this.time = time;
        this.callback = callback;
    }

    public boolean relativeCheck(){
        return SystemClock.getSystemTime() > checkpoint + time;
    }

    public boolean absoluteCheck(){
        return SystemClock.getSystemTime() == time;
    }

    public void execute(){
        if(reset){
            setCheckpoint();
            reset = false;
        }
        boolean check;
        if(mode == timeMode.RELATIVE){
            check = relativeCheck();
        } else {
            check = absoluteCheck();
        }
        if(check){
            callback.run();
        }
    }

    public void setCheckpoint(){
        checkpoint = SystemClock.getSystemTime();
    }



}
