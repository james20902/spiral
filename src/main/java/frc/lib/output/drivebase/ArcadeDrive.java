package frc.lib.output.drivebase;

import frc.lib.input.Controller;

public class ArcadeDrive extends DriveBase {

    public ArcadeDrive(Controller instance) {
        super(instance);
    }

    private DriveSignal arcade(float left, float right){
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
