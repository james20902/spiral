package frc.lib.output;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import frc.lib.utility.Console;

public class Gyro {
    private SerialPort.Port port;
    private AHRS navXInstance;

    public Gyro(SerialPort.Port port){
        this.port = port;
        navXInstance = new AHRS(this.port);
        if(!navXInstance.isConnected()){
            Console.reportError("no navX detected on this interface!");
        }
    }
}
