package frc.lib.control.Subsystems;

import edu.wpi.first.wpilibj.Watchdog;
import frc.lib.control.SubsystemManager;
import frc.lib.control.Task;
import frc.lib.utility.SystemState;

public abstract class Subsystem extends Task {
    private String systemName = this.getClass().getSimpleName();

    private int timing = 0;
    private Watchdog watchdog;

    public Subsystem(){
        this(5);
    }

    public Subsystem(int timing){
        this.timing = timing;
        SubsystemManager.getInstance().addSubsystem(this);
        watchdog = new Watchdog(timing * Math.pow(10, -3), this::logSlowdown);
    }

    public Subsystem(int timing, String name){
        this(timing);
        this.systemName = name;
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

    public abstract void logSlowdown();
}
