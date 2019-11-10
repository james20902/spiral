package frc.lib.output.drivebase;

/*
* this is based off of something i saw from 4456
* it sets speed as a percentage of the maximum wheel velocity, simply a more stable arcade
* https://github.com/TEAM4456/MechLaunch2019/blob/master/src/main/java/frc/team4456/robot/subsystems/Drive.java
 */
public class VelocityDrive extends DriveSystem {//todo implement

    public DriveSignal math(float x, float y) {
        return new DriveSignal(y+x, y-x);
    }

    public DriveSignal math(){return null;};

    public void init() {

    }

    public void logSlowdown() {

    }
}
