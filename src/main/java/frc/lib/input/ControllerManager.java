package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.control.Task;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ControllerManager extends Task {

    private static ControllerManager instance;

    public static ControllerManager getInstance(){
        if(instance == null){
            instance = new ControllerManager();
        }
        return instance;
    }

    private static final byte MAX_JOYSTICKS = 6;


    private float[][] deadzones = new float[1][1];

    private final ByteBuffer countStorage = ByteBuffer.allocateDirect(1);

    private boolean controllerLock = false;

    @Override
    public void init(){
    }

    public void standardExecution(){

    }

    public void competitionExecution(){

    }

    private void pollAllJoysticks(){

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

}
