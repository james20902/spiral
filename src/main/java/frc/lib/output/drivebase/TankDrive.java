package frc.lib.output.drivebase;

public class TankDrive extends DriveBase {

    private DriveSignal tank(float left, float right){
        return new DriveSignal(left, right);
    }
}
