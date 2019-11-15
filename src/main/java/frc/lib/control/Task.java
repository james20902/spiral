package frc.lib.control;

public abstract class Task implements Runnable {

    int delay;

    public Task(int delay){
        this.delay = delay;
    }

    public abstract void init();

    @Override
    public void run(){
        init();
        while(!Thread.interrupted()){
            try{
                update();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                //do nothing, thread is supposed to sleep
            }
        }
        interrupted();
    }

    public abstract void update();

    public void interrupted(){
        //Some logger support logs task name interrupt
    }

}