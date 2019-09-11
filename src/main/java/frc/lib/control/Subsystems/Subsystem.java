package frc.lib.control.Subsystems;

import frc.lib.utility.SystemState;

public abstract class Subsystem implements Runnable {
    private String systemName = this.getClass().getSimpleName();


    @Override
    public void run() {
        switch (SystemState.getInstance().getState()) {
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
                throw new IllegalStateException("If you got here somehow you've really messed up");
        }
    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public void testPeriodic(){}

    public String getSystemName(){
        return systemName;
    }

    public abstract void logSlowdown();
}
