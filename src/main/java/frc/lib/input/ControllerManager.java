package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.control.Task;
import frc.lib.utility.Console;
import frc.lib.utility.Settings;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerManager extends Task {

    private static ControllerManager instance;
    AtomicBoolean lock;

    public static ControllerManager getInstance(){
        if(instance == null){
            instance = new ControllerManager();
        }
        return instance;
    }

    public static final byte MAX_JOYSTICKS = 6;
    public static Controller[] controllers;
    public static float[][] deadzones = new float[1][1];

    public void init(){
        deadzones = Settings.getInstance().deadzones;
        findJoysticks();
        lock = new AtomicBoolean(false);
    }

    public void run() {
        if(lock.get()) return;
        lock.set(true);
        pollAllJoysticks();
        lock.set(false);
    }

    @Override
    public void logSlowdown() {
        Console.reportError("Slowdown on ControllerManager! Realistically this shouldn't happen so make an issue on the git repo.", -1);//todo what tf are the error codes
    }

    public static void pollAllJoysticks(){
        HAL.waitForDSDataTimeout(25);
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
            throw new NullPointerException("no joysticks present!"); //todo we shouldn't do these because they will crash the whole robot program
        }
        for(Controller stick : controllers){
            byte port = stick.getPort();
            stick.initialize(buttonCount(port), axesCount(port), POVCount(port));
        }
    }
}
