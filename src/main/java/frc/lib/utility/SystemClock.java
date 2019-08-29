package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;

public class SystemClock {

    //todo, self implementation of TimeUnit using a LUT or something
    //this class is very very broken right now, the conversions are wrong

    //micro
    public static double getSystemTime(Units unit){
        return Units.MICRO.convert(HALUtil.getFPGATime(), unit);
    }
    public static double getSystemTime(){
        return getSystemTime(Units.MILLI);
    }

    public static double getMatchTime(Units unit){
        return Units.MILLI.convert((long)HAL.getMatchTime(), unit);
    }
    public static double getMatchTime(){
        return getMatchTime(Units.BASE);
    }

}
