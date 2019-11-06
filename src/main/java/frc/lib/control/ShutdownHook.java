package frc.lib.control;

import frc.lib.utility.Console;
import frc.lib.utility.SuperMonkeyBall;

public class ShutdownHook extends Thread{

    public void run(){
        Console.reportWarning("Spiral shutting down");
        TaskManager.getInstance().shutdown();
        SuperMonkeyBall.getInstance().shutdown();
    }

}
