package frc.lib.control;

import edu.wpi.first.wpilibj.Watchdog;
import frc.lib.utility.SystemState;

public class Subsystem extends Thread {

    private String systemName = this.getClass().getSimpleName();

    private long timing;
    private Watchdog watchdog;

    public Subsystem(){
        this(2);
    }

    public Subsystem(long timing){
        this.timing = timing;
        watchdog = new Watchdog(timing * Math.pow(10, -3), this::logSlowdown);
    }

    @Override
    public void run(){
        watchdog.reset();
        while(true){
            try {
                //todo implement SystemState
                switch(SystemState.getInstance().getState()){
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
                Thread.sleep(getTiming());
            } catch (InterruptedException e) {
                System.out.println(getSystemName() + " system shutting down");
                disabledPeriodic();
                watchdog.disable();
                break;
            }
        }
    }

    public void disabledPeriodic(){}

    public void autonomousPeriodic(){}

    public void teleopPeriodic(){}

    public void testPeriodic(){}

    String getSystemName(){
        return systemName;
    }

    public void setNewTiming(long timing){
        this.timing = timing;
    }


    public long getTiming(){ return timing; }

    public void logSlowdown(){
        watchdog.printEpochs();
    }

}
