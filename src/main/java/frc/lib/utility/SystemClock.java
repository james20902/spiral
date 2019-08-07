package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;

public class SystemClock {

    private static SystemClock instance;

    public static SystemClock getInstance(){
        if(instance == null){
            instance = new SystemClock();
        }
        return instance;
    }

    public long getSystemTime(){
        return HALUtil.getFPGATime();
    }

    public double getMatchTime(){
        return HAL.getMatchTime();
    }

}
