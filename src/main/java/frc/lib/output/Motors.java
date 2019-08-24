package frc.lib.output;

import frc.lib.output.drivebase.DriveSignal;

import java.util.ArrayList;
import java.util.List;

public class Motors {
    public static List<Motor> left = new ArrayList<Motor>();
    public static List<Motor> right = new ArrayList<Motor>();
    public static List<Motor> motors = new ArrayList<Motor>();
    public static void setDrive(DriveSignal drive){
        for(Motor m : left){
            m.setPercent(drive.getLeftSpeed());
        }
        for(Motor m : right){
            m.setPercent(drive.getRightSpeed());
        }
    }
    //todo maybe borrow the pose estimation from Vae Victis and add collision detection because i'm too lazy to write pose stuff
}
