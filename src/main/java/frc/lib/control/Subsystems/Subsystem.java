package frc.lib.control.Subsystems;

import edu.wpi.first.wpilibj.Watchdog;
import frc.lib.control.SubsystemManager;
import frc.lib.control.Task;
import frc.lib.utility.SystemState;

public abstract class Subsystem extends Task {
    private String systemName = this.getClass().getSimpleName();

    private int timing = 0;

    public Subsystem(){
        this(5);
    }

    public Subsystem(int timing){
        this.timing = timing;
        SubsystemManager.getInstance().addSubsystem(this);
    }

    public Subsystem(int timing, String name){
        this(timing);
        this.systemName = name;
    }

    @Override
    public void run() {
        thog.reset();
        switch (SystemState.getInstance().getState()) {
            case SystemState.DISABLED:
                thog.addEpoch("Disabled Loop");
                disabledPeriodic();
                break;
            case SystemState.AUTONOMOUS:
                thog.addEpoch("Autonomous Loop");
                autonomousPeriodic();
                break;
            case SystemState.OPERATOR:
                thog.addEpoch("Teleop Loop");
                teleopPeriodic();
                break;
            case SystemState.TEST:
                thog.addEpoch("Test Loop");
                testPeriodic();
                break;
            default:
                throw new IllegalStateException("If you got here somehow you've really messed up");
        }
    }

    public void kill(){
        System.out.println(getSystemName() + " system shutting down");
        this.kill();
        thog.disable();
    }

    public abstract void disabledPeriodic();

    public abstract void autonomousPeriodic();

    public abstract void teleopPeriodic();

    public abstract void testPeriodic();

    public String getSystemName(){
        return systemName;
    }

    public abstract void logSlowdown();
}
