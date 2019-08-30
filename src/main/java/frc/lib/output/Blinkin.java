package frc.lib.output;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.output.error.ErrorHandler;

public class Blinkin {
    public enum Colors{
        RAINBOW_RAINBOW(-0.99), RAINBOW_PARTY(-0.97), RAINBOW_OCEAN(-0.95), RAINBOW_LAVE(-0.93), RAINBOW_FOREST(-0.91), RAINBOW_GLITTER(-0.89), CONFETTI(-0.87), SHOT_RED(-0.85), SHOT_BLUE(-0.83), SHOT_WHITE(-0.81), SINELON_RAINBOW(-0.79), SINELON_PARTY(-0.77), SINELON_OCEAN(-0.75), SINELON_LAVA(-0.73), SINELON_FOREST(-0.71), BPM_RAINBOW(-0.69), BPM_PARTY(-0.67), BPM_OCEAN(-0.65), BPM_LAVA(-0.63), BPM_FOREST(-0.61), FIRE_MED(-0.59), FIRE_LARGE(-0.57), TWINK_RAINBOW(-0.55), TWINK_PARTY(-0.53), TWINK_OCEAN(-0.51), TWINK_LAVA(-0.49), TWINK_FOREST(-0.47), WAV_RAINBOW(-0.45), WAV_PARTY(-0.43), WAV_OCEAN(-0.41), WAV_LAVA(-0.39), WAV_FOREST(-0.37), LARSON_RED(-0.35), LARSON_GREY(-0.33), CHASE_RED(-.31), CHASE_BLUE(-.29), CHASE_GRAY(-.27), HEARTBEAT_RED(-.25), HEARTBEAT_BLUE(-.23), HEARTBEAT_WHITE(-.21), HEARTBEAT_GRAY(-.19), BREATH_RED(-.17), BREATH_BLUE(-.15), BREATH_GRAY(-.13), STROBE_RED(-.11), STROBE_BLUE(-.09), STROBE_GOLD(-.07), STROBE_WHITE(-.05), BLEND2BLACK(-.03), LARSON(-.01), LIGHT_CHASE(.01), HEART_SLOW(.03), HEART_MED(.05), HEART_FAST(.07), BREATH_SLOW(.09), BREATH_FAST(.11), SHOT(.13), STROBE(.15), BLEND2BLACK2(.17), LARSON2(.19), CHASE(.21), HEART_SLOW2(.23), HEART_MED2(.25), HEART_FAST2(.27), BREATH_SLOW2(.29), BREATH_FAST2(.31), SHOT2(.33), STROBE2(.35), SPARKLE1_2(.37), SPARKLE2_1(.39), GRADIENT1_2(.41), BPM1_2(.43), END2END1_2(.45), END2END(.47), NOBLEND1_2(.49), TWINK1_2(.51), WAVES1_2(.53), SINELON1_2(.55), HOT_PINK(.57), DARK_RED(.59), RED(.61), RED_ORANGE(.63), ORANGE(.65), GOLD(.67), YELLOW(.69), LAWN_GREEN(.71), LIME(.73), DARK_GREEN(.75), GREEN(.77), BLUE_GREEN(.79), AQUA(.81), SKY_BLUE(.83), DARK_BLUE(.85), BLUE(.87), BLUE_VIOLET(.89), VIOLET(.91), WHITE(.93), GRAY(.95), DARK_GRAY(.97), BLACK(.99);

        double c;
        Colors(double c){
            this.c = c;
        }
        double getPWM(){
            return c;
        }
    }
    private static Spark spark;
    private static boolean individual;
    public static void set(float color) {
        if(spark != null){
            spark.set(color);
        } else {
            ErrorHandler.report("Please check that you initialized the Blinkin in robot.motors correctly", "Blinkin");
        }
    }
    public static void initSpark(int pwm, boolean ind) {
        spark = new Spark(pwm);
        individual = ind;
    }
}