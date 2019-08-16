package frc.team5115.frc2020;

import frc.lib.control.Subsystem;
import frc.lib.control.TimedExecution;

public class TestSystem extends Subsystem {

    TimedExecution testloop;

    public TestSystem(){
        testloop = new TimedExecution(TimedExecution.timeMode.RELATIVE, 1, this::oneMsLoop);
    }

    @Override
    public void teleopPeriodic(){
        System.out.println("running");
//        testloop.execute();
    }

    @Override
    public void autonomousPeriodic(){
        System.out.println("running blindfolded");
    }

    @Override
    public void disabledPeriodic(){
        System.out.println("running quietly");
    }

    public void oneMsLoop(){
        System.out.println("test");
    }
}
