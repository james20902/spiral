package frc.lib.output.drivebase;

import frc.lib.control.Subsystem;
import frc.lib.input.Controller;

public abstract class DriveBase extends Subsystem {

    DriveSignal signal;
    Controller instance;

    public DriveBase(Controller instance){
        this.instance = instance;
    }

    public void setSignal(DriveSignal signal){
        this.signal = signal;
    }

    public abstract DriveSignal math();

    public void disabledPeriodic(){
        setSignal(new DriveSignal(0, 0));
    }
    public void teleopPeriodic(){
        setSignal(math());
    }
}
