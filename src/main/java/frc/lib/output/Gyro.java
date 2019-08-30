package frc.lib.output;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import frc.lib.output.error.ErrorHandler;
import frc.lib.utility.Console;

public class Gyro {
    private SerialPort.Port port;
    private AHRS navXInstance;

    public Gyro(SerialPort.Port port){
        this.port = port;
        navXInstance = new AHRS(this.port);
        if(!navXInstance.isConnected()){
            ErrorHandler.report("NavX not detected. Please check that it is plugged in and lights are blinking, and that you chose the correct port", "NavX");
        }
    }
}
