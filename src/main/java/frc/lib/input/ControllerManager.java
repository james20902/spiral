package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.control.Task;
import frc.lib.utility.Console;
import frc.lib.utility.Settings;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ControllerManager extends Task {

    private static ControllerManager instance;
    private ReentrantLock update = new ReentrantLock();

    public static ControllerManager getInstance(){
        if(instance == null){
            instance = new ControllerManager();
        }
        return instance;
    }

    private static final byte MAX_JOYSTICKS = 6;
    private static Controller[] controllers;
    private float[][] deadzones = new float[1][1];

    public void init(){
        deadzones = Settings.getInstance().deadzones;
        findJoysticks();
    }

    public void run() {
        update.lock();
        try {
            pollAllJoysticks();
        } finally {
            update.unlock();
        }
    }

    @Override
    public void logSlowdown() {
        Console.reportError("Slowdown on ControllerManager! Realistically this shouldn't happen so make an issue on the git repo.", -1);//todo what tf are the error codes
    }

    private void pollAllJoysticks(){
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

    private int pollButtons(byte port){
        return HAL.getJoystickButtons(port, ByteBuffer.allocateDirect(1));
    }

    private float[] pollAxes(byte port, byte count){
        float[] axesOutput = new float[count];
        HAL.getJoystickAxes(port, axesOutput);
        return axesOutput;
    }

    private short[] pollPOV(byte port, byte count){
        short[] POVOutput = new short[count];
        HAL.getJoystickPOVs(port, POVOutput);
        return POVOutput;
    }

    private byte buttonCount(byte port){
        ByteBuffer count = ByteBuffer.allocateDirect(1);
        HAL.getJoystickButtons(port, count);
        return count.get(0);
    }

    private byte axesCount(byte port){
        return (byte)HAL.getJoystickAxes(port, new float[12]);
    }

    private byte POVCount(byte port){
        return (byte)HAL.getJoystickPOVs(port, new short[12]);
    }

    private void findJoysticks(){
        List<Controller> storage = new ArrayList<>();
        for(byte i = 0; i < MAX_JOYSTICKS; i++){
            if(buttonCount(i) > 0){
                storage.add(new Controller(i));
            }
        }
        Console.reportWarning("Controller(s) detected!");
        controllers = storage.toArray(new Controller[storage.size()]);

        for(Controller stick : controllers){
            byte port = stick.getPort();
            stick.initialize(buttonCount(port), axesCount(port), POVCount(port));
        }
    }
}
