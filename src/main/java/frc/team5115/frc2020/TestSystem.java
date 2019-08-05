package frc.team5115.frc2020;

import frc.lib.control.Subsystem;

public class TestSystem extends Subsystem {
    @Override
    public void teleopPeriodic(){
        System.out.println("running");
    }

    @Override
    public void autonomousPeriodic(){
        System.out.println("running blindfolded");
    }

    @Override
    public void disabledPeriodic(){
//        System.out.println("running quietly");
    }
}
