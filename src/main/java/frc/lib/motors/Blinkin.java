package frc.lib.motors;

import edu.wpi.first.wpilibj.Spark;

public class Blinkin {
    //todo make a huge enum of all possible colors from datasheet
    enum Colors{}
    private static Spark spark;
    private static boolean individual;
    public static void set(float color) {
        spark.set(color);
    }
    public static void initSpark(int pwm, boolean ind) {
        spark = new Spark(pwm);
        individual = ind;
    }
}
