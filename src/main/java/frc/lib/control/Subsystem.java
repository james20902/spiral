package frc.lib.control;


public abstract class Subsystem extends Task {

    private boolean overridden = false;
    private int internalState;

    public void standardExecution() {

    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public void testPeriodic(){}

    public void overrideState(int state){
        internalState = state;
    }

}
