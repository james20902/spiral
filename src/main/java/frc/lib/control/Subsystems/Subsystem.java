package frc.lib.control.Subsystems;

import edu.wpi.first.wpilibj.Watchdog;
import frc.lib.utility.SystemState;
import frc.team5115.frc2020.Robot;
import java.util.concurrent.TimeUnit;

public abstract class Subsystem implements Runnable {
    private String systemName = this.getClass().getSimpleName();

    private int timing = 0;
    private Watchdog watchdog;

    public Subsystem(){
        this(0);
    }

    public Subsystem(int timing){
        this.timing = timing;
//        Robot.robotInstance.addTask(this, this.timing, TimeUnit.MILLISECONDS);
        //todo redo this to run on its own scheduler
        watchdog = new Watchdog(timing * Math.pow(10, -3), this::logSlowdown);
    }

    @Override
    public void run() {
        watchdog.reset();
        switch (SystemState.getInstance().getState()) {
            case SystemState.DISABLED:
                watchdog.addEpoch("Disabled Loop");
                disabledPeriodic();
                break;
            case SystemState.AUTONOMOUS:
                watchdog.addEpoch("Autonomous Loop");
                autonomousPeriodic();
                break;
            case SystemState.OPERATOR:
                watchdog.addEpoch("Teleop Loop");
                teleopPeriodic();
                break;
            case SystemState.TEST:
                watchdog.addEpoch("Test Loop");
                testPeriodic();
                break;
            default:
                throw new IllegalStateException("If you got here somehow you've really messed up");
        }
    }

    public void kill(){
        System.out.println(getSystemName() + " system shutting down");
        kill();
        watchdog.disable();
    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public void testPeriodic(){}

    public String getSystemName(){
        return systemName;
    }

    public void logSlowdown(){
        watchdog.printEpochs();
    }

}
