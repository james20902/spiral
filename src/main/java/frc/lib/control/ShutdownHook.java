package frc.lib.control;

public class ShutdownHook extends Thread{

    public void run(){
        TaskManager.getInstance().shutdown();
    }

}
