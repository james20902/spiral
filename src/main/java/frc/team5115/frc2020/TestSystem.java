package frc.team5115.frc2020;

import frc.lib.control.Subsystems.Subsystem;

public class TestSystem extends Subsystem {


    public TestSystem(){

    }

    public void kill(){ }

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
