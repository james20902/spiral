package frc.lib.output.drivebase;

import edu.wpi.first.wpilibj.PIDController;

/*
* this is based off of something i first saw from 4456
* it sets speed as a percentage of the maximum wheel velocity, simply a more stable arcade
* https://github.com/TEAM4456/MechLaunch2019/blob/master/src/main/java/frc/team4456/robot/subsystems/Drive.java
 */
public class VelocityDrive extends DriveSystem {//todo implement
    PIDController left, right;
    public DriveSignal math(float x, float y) {
        return new DriveSignal(y+x, y-x);
    }

    public DriveSignal math(){return null;};

    public void init() {
        left = new PIDController(1, 1, 1, 0, null, null, 4);
        right = new PIDController(1, 1, 1, 0, null, null, 4);
    }

    public void logSlowdown() {

    }
}