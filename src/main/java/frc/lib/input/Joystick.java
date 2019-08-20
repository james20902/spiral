package frc.lib.input;

public class Joystick {

    private static class HALJoystickButtons {
        byte state;
        byte ID;
    }

    private static class HALJoystickAxes {
        float[] output = new float[12];
        byte ID;

    }

    private static class HALJoystickPOVs {
        short[] state = new short[12];
        byte ID;
    }



}
