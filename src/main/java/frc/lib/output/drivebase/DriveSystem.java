package frc.lib.output.drivebase;

import frc.lib.control.Task;

public abstract class DriveSystem extends Task {

    DriveSignal signal;

    public DriveSystem(/*TODO should have joystick input methods in here*/){
        super(8);
    }

    public void setSignal(DriveSignal signal){
        this.signal = signal;
    }

    public DriveSignal getSignal(){return signal;}

    public abstract DriveSignal math();

    public void update(){
        //todo set motors
    }

    public void disabledPeriodic(){
        setSignal(new DriveSignal(0, 0, true));
    }
    //TODO might want to do something during auto????
    public void teleopPeriodic(){
        setSignal(math());
    }
    public static double handleDeadzone(double val, double deadzone) {//scaled radial dead zone(the correct way to do it), but 1d
        if(val < deadzone)
            return 0;
        else
            return ((val - deadzone) / (1 - deadzone));
    }

    public static void handleDeadzone2d(double x, double y, double deadzone){//scaled radial deadzone(correct way to do it)
        double mag = Math.sqrt(x*x + y*y);
        if(mag < deadzone) {
            x = 0;
            y = 0;
        } else {
            x = x*((mag - deadzone) / (1 - deadzone));
            y = y*((mag - deadzone) / (1 - deadzone));
        }
    }
}
