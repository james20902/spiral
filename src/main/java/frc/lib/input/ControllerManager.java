package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.team5115.frc2020.Robot;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ControllerManager implements Runnable{
    public static final byte MAX_JOYSTICKS = 6;
    public static Controller[] controllers;
    public static final float[] deadzones = {0.15f, 0.2f};//todo deadzone settings loading

    public static void init(){
        controllers = new Controller[MAX_JOYSTICKS];
        for(int i = 0; i < MAX_JOYSTICKS; i++){
            controllers[i] = new Controller((byte)i);
        }
        findJoysticks();//todo actually initialize the controller
    }

    public void run(){
        pollAllJoysticks();
    }

    public static void pollAllJoysticks(){
        for(Controller instance : controllers){
            byte port = instance.getPort();
            instance.updateData(
                    pollButtons(port),
                    pollAxes(port, instance.getAxesCount()),
                    pollPOV(port, instance.getPOVcount())
            );
        }
    }

    public static Controller getJoystick(int port){
        return controllers[port];
    }

    private static int pollButtons(byte port){
        return HAL.getJoystickButtons(port, ByteBuffer.allocateDirect(1));
    }

    private static float[] pollAxes(byte port, byte count){
        float[] axesOutput = new float[count];
        HAL.getJoystickAxes(port, axesOutput);
        return axesOutput;
    }

    private static short[] pollPOV(byte port, byte count){
        short[] POVOutput = new short[count];
        HAL.getJoystickPOVs(port, POVOutput);
        return POVOutput;
    }

    public static byte buttonCount(byte port){
        ByteBuffer count = ByteBuffer.allocateDirect(1);
        HAL.getJoystickButtons(port, count);
        return count.get(0);
    }

    public static byte axesCount(byte port){
        return (byte)HAL.getJoystickAxes(port, new float[12]);
    }

    public static byte POVCount(byte port){
        return (byte)HAL.getJoystickPOVs(port, new short[12]);
    }

    public static void findJoysticks(){
        List<Controller> storage = new ArrayList<>();
        for(byte i = 0; i < MAX_JOYSTICKS; i++){
            if(buttonCount(i) > 0){
                storage.add(new Controller(i));
            }
        }
        controllers = storage.toArray(new Controller[storage.size()]);
        if (controllers.length == 0){
            throw new NullPointerException("no joysticks present!");
        }
        for(Controller stick : controllers){
            byte port = stick.getPort();
            stick.initialize(buttonCount(port), axesCount(port), POVCount(port));
        }
    }
}
