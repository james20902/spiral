package frc.lib.control;

import frc.lib.utility.SuperMonkeyBall;

public abstract class Subsystem extends Task {

    private boolean overridden = false;
    private int internalState;

    public void standardExecution() {
        if(!overridden){
            internalState = SuperMonkeyBall.getInstance().getState();
        }
        switch (internalState){
            case SuperMonkeyBall.DISABLED:
                disabledPeriodic();
                break;
            case SuperMonkeyBall.AUTONOMOUS:
                autonomousPeriodic();
                break;
            case SuperMonkeyBall.OPERATOR:
                teleopPeriodic();
                break;
            case SuperMonkeyBall.TEST:
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
