package frc.lib.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class Gyro {
    private SerialPort.Port port;
    private AHRS navXInstance;
//use AHRS.getFusedHeading instead of getYaw
    public Gyro(SerialPort.Port port){
        this.port = port;
        navXInstance = new AHRS(this.port);
    }
}
