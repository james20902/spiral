package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.control.Task;
import frc.lib.utility.Console;
import frc.lib.utility.Settings;
import frc.lib.utility.SystemState;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ControllerManager extends Task {

    private static ControllerManager instance;

    public static ControllerManager getInstance(){
        if(instance == null){
            instance = new ControllerManager();
        }
        return instance;
    }

    private static final byte MAX_JOYSTICKS = 6;

    private static Controller[] controllers;

    private float[][] deadzones = new float[1][1];

    private final ByteBuffer countStorage = ByteBuffer.allocateDirect(1);

    private boolean controllerLock = false;

    @Override
    public void init(){
        deadzones = Settings.getInstance().deadzones;
//        findJoysticks();
    }

    public void standardExecution(){
        //fixed array, lock, poll all 6 objects (like old driver station did)
        if(!controllerLock){
            hotplugControllers();
            controllerLock = true;
        }
        pollAllJoysticks();
    }

    public void competitionExecution(){
        //this assumes a controller is plugged in
        //need a way to keep checking for controllers if its unplugged
        if(SystemState.getInstance().isEnabled() && !controllerLock){
            finalizeControllers();
            controllerLock = true;
        }
        pollAllJoysticks();
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

    public static Controller getJoystick(){
        return getJoystick(0);
    }

    public static Controller getJoystick(int port){
        return controllers[port];
    }

    private int pollButtons(byte port){
        return HAL.getJoystickButtons(port, countStorage);
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
        ByteBuffer temp = ByteBuffer.allocateDirect(1);
        HAL.getJoystickButtons(port, temp);
        return temp.get(0);
    }

    private byte axesCount(byte port){
        return (byte)HAL.getJoystickAxes(port, new float[12]);
    }

    private byte POVCount(byte port){
        return (byte)HAL.getJoystickPOVs(port, new short[12]);
    }

    public void finalizeControllers(){
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

    private void hotplugControllers(){
        for(byte i = 0; i < MAX_JOYSTICKS; i++){
            controllers[i] = new Controller(i);
        }
        for(Controller stick : controllers){
            stick.initialize((byte)12, (byte)12, (byte)12);
        }
        Console.print("done");
    }
}
