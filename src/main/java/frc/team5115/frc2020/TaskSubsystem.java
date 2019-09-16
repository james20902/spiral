package frc.team5115.frc2020;

import frc.lib.control.Task;
import frc.lib.utility.SystemState;

public class TaskSubsystem extends Task {

    public void standardExecution(){
        switch (SystemState.getInstance().getState()){
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

}
