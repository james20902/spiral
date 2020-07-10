package frc.lib;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavXIMU {

    private final AHRS nativeInstance;

    public NavXIMU(){
        nativeInstance = new AHRS(SPI.Port.kMXP);
        if(!nativeInstance.isConnected()){
            throw new IllegalStateException();
        }

        nativeInstance.zeroYaw();
    }

    public AHRS getNativeInstance(){
        return nativeInstance;
    }

    public double getHeading(){
        return nativeInstance.getFusedHeading();
    }

    public double getHeading180(){
        return getHeading() - 180;
    }

}
