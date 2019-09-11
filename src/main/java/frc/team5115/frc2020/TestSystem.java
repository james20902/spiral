package frc.team5115.frc2020;

import frc.lib.control.Subsystems.Subsystem;

public class TestSystem extends Subsystem {


    public TestSystem(){

    }

    public void init() { }

    public void kill(){ }

    public void teleopPeriodic(){
        System.out.println("running");
//        testloop.execute();
    }

    public void autonomousPeriodic(){
        System.out.println("running blindfolded");
    }

    public void disabledPeriodic(){
        System.out.println("running quietly");
    }

    public void testPeriodic(){
        System.out.println("running with bugs");
    }

    public void logSlowdown(){
        System.out.println("test");
    }
}
