package frc.lib.control;

public class Subsystem implements Runnable {

    private long timing;

    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(getTiming());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNewTiming(long timing){
        this.timing = timing;
    }


    public long getTiming(){ return timing; }



}
