package frc.lib.output.drivebase;

import frc.lib.control.Subsystem;

public class TankDrive extends Subsystem {
    private DriveSignal tank(float left, float right){
        return new DriveSignal(left, right);
    }
}
