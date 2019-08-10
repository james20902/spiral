package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.wpilibj.Timer;

public class SystemClock {

    //todo method level annotation containing power for different second conversions

    private static SystemClock instance;

    public static SystemClock getInstance(){
        if(instance == null){
            instance = new SystemClock();
        }
        return instance;
    }

    //micro seconds
    public long getSystemTime(){
        return HALUtil.getFPGATime();
    }



    public double getMatchTime(){
        return HAL.getMatchTime();
    }

}
