package frc.lib.control;

import edu.wpi.first.wpilibj.Watchdog;

public class Subsystem implements Runnable {

    private long timing;
    private StateController systemState;
    private Watchdog watchdog;

    public Subsystem(long timing){
        this.timing = timing;
        systemState = new StateController();
        watchdog = new Watchdog(timing * Math.pow(10, -3), this::logSlowdown);
    }

    @Override
    public void run(){
        while(true){
            try {
                systemState.updateSystemState();
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
                e.printStackTrace();
            }
        }
    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public String getName(){
        return this.getClass().getName();
    }

    public void setNewTiming(long timing){
        this.timing = timing;
    }


    public long getTiming(){ return timing; }

    public void logSlowdown(){

    }

    public void masterStateOverride(StateController.RobotState state){
        systemState.changeSystemState(state);
    }


}
