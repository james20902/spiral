package frc.lib.output.drivebase;

import frc.lib.input.ControllerManager;
import frc.lib.utility.Settings;

public class ArcadeDrive extends DriveSystem {
    public DriveSignal math(){
        float throttle = ControllerManager.getInstance().getJoystickAxis(Settings.getInstance().drive_sticks[1][0], Settings.getInstance().drive_sticks[1][1]) - ControllerManager.getInstance().getJoystickAxis(Settings.getInstance().drive_sticks[0][0], Settings.getInstance().drive_sticks[0][1]);
        float turn = ControllerManager.getInstance().getJoystickAxis(Settings.getInstance().drive_sticks[2][0], Settings.getInstance().drive_sticks[2][1]);

        return new DriveSignal(throttle - turn,throttle + turn);
    }

    public void init() {

    }

    public void logSlowdown() {

    }
}