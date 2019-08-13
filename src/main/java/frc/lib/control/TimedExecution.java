package frc.lib.control;

public class TimedExecution {

    public enum timeMode{RELATIVE, ABSOLUTE}

    private double time;
    private double checkpoint;
    private boolean reset = true;
    private Runnable callback;

    public TimedExecution(double time, Runnable callback){
        this.time = time;
        this.callback = callback;
    }

    public void execute(){
        if(reset){
            setCheckpoint();
        }
    }

    public void setCheckpoint(){

    }

}
