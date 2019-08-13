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

    //todo, redo convert method
    public static double convert(long measure, Units unit){
        return measure * Math.pow(10, unit.pow);
    }

    public static double getSystemTime(){
        return convert(HALUtil.getFPGATime(), Units.MILLISECONDS);
    }

    public static double getMatchTime(){
        return HAL.getMatchTime();
    }

}
