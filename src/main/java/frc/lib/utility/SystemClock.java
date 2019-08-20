package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;

public class SystemClock {

    public enum Units {
        NANOSECONDS(-9),
        MICROSECONDS(-6),
        MILLISECONDS(-3),
        SECONDS(1);

        private final int pow;
        private Units(int pow){
            this.pow = pow;
        }

    }

    //micro converted to milli
    public static double getSystemTime(){
        return HALUtil.getFPGATime() / 1000 ;
    }

    //milli
    public static double getMatchTime(){
        return HAL.getMatchTime();
    }

}
