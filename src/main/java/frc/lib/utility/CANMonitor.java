package frc.lib.utility;

import edu.wpi.first.hal.can.CANJNI;
import edu.wpi.first.hal.can.CANStatus;

public class CANMonitor {

    private static CANStatus data = new CANStatus();

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

    public int transmitErrors(){ return data.transmitErrorCount; }

    public int recieveErrors(){ return data.receiveErrorCount; }

    public int busOffErrors(){ return data.busOffCount; }

    public int TXOverloads(){ return data.txFullCount; }






}
