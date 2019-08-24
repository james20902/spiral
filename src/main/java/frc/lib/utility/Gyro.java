package frc.lib.utility;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class Gyro {
    private static AHRS navX;
    public static void init(){
        navX = new AHRS(SerialPort.Port.kMXP);
        if(navX == null)
            navX = new AHRS(SerialPort.Port.kUSB);
    }

    public static AHRS getNavx(){
        return navX;
    }
}