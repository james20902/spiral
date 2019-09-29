package frc.lib.output.drivebase;

import frc.lib.input.Controller;
import frc.lib.utility.Input;

public class ArcadeDrive extends DriveSystem {

    public ArcadeDrive(Controller instance) {
        super(instance);
    }

    public DriveSignal math(){
        double x = this.instance.getAxis(Input.getInstance().lAxis);
        double y = this.instance.getAxis(Input.getInstance().rAxis);
        return new DriveSignal(Math.min(x+y, 1), Math.min(y-x, 1));//todo should scale both down if it is greater than one
    }

    public void init() {

    }

    public void logSlowdown() {

    }
}
