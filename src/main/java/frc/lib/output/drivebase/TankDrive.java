package frc.lib.output.drivebase;

import frc.lib.input.Controller;
import frc.lib.utility.Input;

public class TankDrive extends DriveSystem {

    public TankDrive(Controller instance) {
        super(instance);
    }

    public DriveSignal math(){
        return new DriveSignal(this.instance.getAxis(Input.getInstance().lAxis), this.instance.getAxis(Input.getInstance().rAxis));
    }

    public void logSlowdown() {

    }
}
