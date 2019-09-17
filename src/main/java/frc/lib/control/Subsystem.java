package frc.lib.control;

import frc.lib.utility.SystemState;

public abstract class Subsystem extends Task {

    private boolean overridden = false;
    private int internalState;

    public void standardExecution() {
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

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public void testPeriodic(){}

    public void overrideState(int state){
        internalState = state;
    }

}
