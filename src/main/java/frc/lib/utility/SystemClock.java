package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import java.util.concurrent.TimeUnit;

public class SystemClock {

    //todo, self implementation of TimeUnit using a LUT or something

    //micro
    public static double getSystemTime(TimeUnit unit){
        return TimeUnit.MICROSECONDS.convert(HALUtil.getFPGATime(), unit);
    }
    public static double getSystemTime(){
        return getSystemTime(TimeUnit.MILLISECONDS);
    }

    public static double getMatchTime(TimeUnit unit){
        return TimeUnit.MILLISECONDS.convert((long)HAL.getMatchTime(), unit);
    }
    public static double getMatchTime(){
        return getMatchTime(TimeUnit.SECONDS);
    }

}
