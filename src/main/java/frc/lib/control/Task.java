package frc.lib.control;

import edu.wpi.first.wpilibj.Watchdog;

public abstract class Task implements Runnable {

    //todo, make this class hold more data about the task. Init never gets run
    //todo, heartbeat

    public Task(){
        init();
    }

    public void init(){};

    public abstract void run();

    public void logSlowdown(){};
}
