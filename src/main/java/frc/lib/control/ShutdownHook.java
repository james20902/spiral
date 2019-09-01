package frc.lib.control;

import frc.lib.utility.Console;

public class ShutdownHook extends Thread{

    public void run(){
        Console.reportWarning("Spiral shutting down");
        TaskManager.getInstance().shutdown();
    }

}
