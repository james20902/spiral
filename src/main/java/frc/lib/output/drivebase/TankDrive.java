package frc.lib.output.drivebase;

import frc.lib.input.Controller;

public class TankDrive extends DriveBase {

    public TankDrive(Controller instance) {
        super(instance);
    }

    private DriveSignal tank(float left, float right){
        return new DriveSignal(left, right);
    }


    public DriveSignal math() {
        return null;
    }

    public void init() {

    }

    public void logSlowdown() {

    }
}
