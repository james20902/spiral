package frc.lib.control;

import frc.lib.utility.SystemState;

public abstract class Subsystem implements Runnable {

    private String systemName;
    private int timing;
    private boolean overridden = false;
    private int internalState;

    public Subsystem(){
        this(5);
    }

    public Subsystem(int timing){
        this.timing = timing;
        this.systemName = this.getClass().getSimpleName();
    }

    public Subsystem(int timing, String name){
        this.systemName = name;
        this.timing = timing;
    }

    @Override
    public void run() {
        if(!overridden){
            internalState = SystemState.getInstance().getState();
        }
        switch (internalState){
            case SystemState.DISABLED:
                disabledPeriodic();
                break;
            case SystemState.AUTONOMOUS:
                autonomousPeriodic();
                break;
            case SystemState.OPERATOR:
                teleopPeriodic();
                break;
            case SystemState.TEST:
                testPeriodic();
                break;
            default:
                throw new IllegalStateException("The requested override state doesn't exist!");
        }
    }

    public String getSystemName(){
        return systemName;
    }

    public int getTiming() {
        return timing;
    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public void testPeriodic(){}

    public void overrideState(int state){
        internalState = state;
    }

}
