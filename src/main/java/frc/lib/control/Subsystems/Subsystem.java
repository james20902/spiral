package frc.lib.control.Subsystems;

import edu.wpi.first.wpilibj.Watchdog;
import frc.lib.utility.SystemState;

import java.lang.reflect.Method;

public abstract class Subsystem extends Thread {

    private String systemName = this.getClass().getSimpleName();

    private long timing = 0;
    private Watchdog watchdog;

    public Subsystem(long timing){
        this.timing = timing;
        watchdog = new Watchdog(timing * Math.pow(10, -3), this::logSlowdown);//todo were these going to be redone? i forgot
    }

    @Override
    public void run() {
        watchdog.reset();
        while (!interrupted()) {
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
            System.out.println(getSystemName() + " system shutting down");
            kill();
            watchdog.disable();
        }
    }

    public abstract void kill();

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
