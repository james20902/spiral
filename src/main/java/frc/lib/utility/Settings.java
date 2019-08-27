package frc.lib.utility;

public class Settings {

    public static final float maxVelocity;
    public static final float ticksPerRevolution;
    public static final float wheelDiameter;
    static {
        //todo, load file and assign constants with static initializer???
        load();
        maxVelocity = 1;
        ticksPerRevolution = -1;
        wheelDiameter = -1;
    }
    public static void load(){//todo YAML loading, make sure to verify proper loading
    }
}
