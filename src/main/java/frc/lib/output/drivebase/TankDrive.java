package frc.lib.output.drivebase;

import frc.lib.control.Subsystems.Subsystem;

public class TankDrive extends Drivebase {

    private DriveSignal tank(float left, float right){
        return new DriveSignal(left, right);
    }
}
