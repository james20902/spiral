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

    public static double convert(long measure, Units unit){
        return Math.pow(measure, unit.pow);
    }

    public static long getSystemTime(){
        return HALUtil.getFPGATime();
    }

    public static double getMatchTime(){
        return HAL.getMatchTime();
    }

}
