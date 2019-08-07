package frc.lib.control;

import edu.wpi.first.wpilibj.Watchdog;

public class Subsystem extends Thread {

    private String systemName = this.getClass().getSimpleName();

    private static final int DISABLED = 0;
    private static final int AUTONOMOUS = 1;
    private static final int OPERATOR = 2;
    private static final int TEST = 3;

    private long timing;
    private Watchdog watchdog;

    public Subsystem(){
        this(0);
    }

    public Subsystem(long timing){
        this.timing = timing;
        watchdog = new Watchdog(5 * Math.pow(10, -3), this::logSlowdown);
    }

    @Override
    public void run(){
        watchdog.reset();
        while(true){
            try {
                switch(SubsystemManager.getInstance().masterState.get()){
                    case DISABLED:
                        watchdog.addEpoch("Disabled Loop");
                        disabledPeriodic();
                        break;
                    case AUTONOMOUS:
                        watchdog.addEpoch("Autonomous Loop");
                        autonomousPeriodic();
                        break;
                    case OPERATOR:
                        watchdog.addEpoch("Teleop Loop");
                        teleopPeriodic();
                        break;
                    case TEST:
                        watchdog.addEpoch("Test Loop");
                        testPeriodic();
                        break;
                    default:
                        throw new IllegalStateException("If you got here somehow you've really messed up");
                }
                watchdog.printEpochs();
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
