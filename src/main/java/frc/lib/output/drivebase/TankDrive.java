package frc.lib.output.drivebase;

import frc.lib.control.Subsystems.Subsystem;

public class TankDrive extends Subsystem {

    public void kill(){}

    private DriveSignal tank(float left, float right){
        return new DriveSignal(left, right);
    }
}
