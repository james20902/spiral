package frc.lib.control;

import edu.wpi.first.wpilibj.Watchdog;

public class Subsystem extends Thread {

    private long timing;
    private StateController systemState;
    private Watchdog watchdog;

    public Subsystem(){
        this(0);
    }

    public Subsystem(long timing){
        this.timing = timing;
        systemState = new StateController();
        watchdog = new Watchdog(timing * Math.pow(10, -3), this::logSlowdown);
    }

    @Override
    public void run(){
        watchdog.reset();
        while(true){
            systemState.updateSystemState();
            try {
                switch(systemState.getSystemState()){
                    case DISABLED:
                        disabledPeriodic();
                        break;
                    case AUTONOMOUS:
                        autonomousPeriodic();
                        break;
                    case OPERATOR:
                        teleopPeriodic();
                        break;
                }
                Thread.sleep(getTiming());
            } catch (InterruptedException e) {
                System.out.println(getSystemName() + " system stopped");
                disabledPeriodic();
                watchdog.disable();
            }
        }
    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public String getSystemName(){
        return this.getClass().getSimpleName();
    }

    public void setNewTiming(long timing){
        this.timing = timing;
    }


    public long getTiming(){ return timing; }

    public void logSlowdown(){
        watchdog.printEpochs();
    }

}
