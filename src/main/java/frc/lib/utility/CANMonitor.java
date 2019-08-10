package frc.lib.utility;

import edu.wpi.first.hal.can.CANJNI;
import edu.wpi.first.hal.can.CANStatus;

public class CANMonitor {

    private static CANStatus data;

    private static CANMonitor instance;

    public static CANMonitor getInstance(){
        if(instance == null){
            instance = new CANMonitor();
        }
        CANJNI.GetCANStatus(data);
        return instance;
    }

    public double getUtilization(){
        return data.percentBusUtilization;
    }

    //todo methods to measure transmit/recieve errors as well as busoff and tx errors




}
