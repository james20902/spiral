package frc.lib.input;

public class ControllerManager {
    public static Controller[] controllers;

    static void init(){
        controllers = new Controller[Input.MAX_JOYSTICKS];
        for(int i = 0; i < Input.MAX_JOYSTICKS; i++){
            controllers[i] = new Controller((byte)i);
        }
    }
}
