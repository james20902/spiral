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
        double l = x+y;
        double r = y-x;
        l /= Math.max(l,r);
        r /= Math.max(l,r);
        return new DriveSignal(l, r);
    }

    public void init() {

    }

    public void logSlowdown() {

    }
}
