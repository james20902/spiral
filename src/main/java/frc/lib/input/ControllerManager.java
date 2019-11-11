package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.control.Task;

import java.nio.ByteBuffer;

public class ControllerManager extends Task {

    private static ControllerManager instance;

    public static ControllerManager getInstance(){
        if(instance == null){
            instance = new ControllerManager();
        }
        return instance;
    }

    private static final byte MAX_JOYSTICKS = (byte)HAL.kMaxJoystickAxes;
    private static final byte MAX_BUTTONS = 64;
    private static final byte MAX_POVS = (byte)HAL.kMaxJoystickPOVs;
    private static final byte MAX_CONTROLLERS = 16;

    private float[][] joysticks;
    private float[][] buttons;
    private short[][] povs;

    private final ByteBuffer countStorage = ByteBuffer.allocateDirect(1);

    private boolean controllerLock = false;

    @Override
    public void init(){
        joysticks = new float[MAX_CONTROLLERS][MAX_JOYSTICKS];
        buttons = new float[MAX_CONTROLLERS][MAX_BUTTONS];
        povs = new short[MAX_CONTROLLERS][MAX_POVS];
    }

    public void standardExecution(){
        pollAllJoysticks();
    }

    public void competitionExecution(){

    }

    public float getJoystickAxis(byte port, byte axis) {
        return joysticks[port][axis];
    }

    public float getPOV(byte port, byte id) {//todo error reporting so i can report id < 1 or being out of range
        return povs[port][id];
    }

    public float getButton(byte port, byte id) {
        return buttons[port][id];
    }

    private void pollAllJoysticks(){ // ds4 controllers have 4ms polling rate(250hz)
        for(byte i = 0; i < MAX_CONTROLLERS; i++) {
            joysticks[i] = pollAxes(i, axesCount(i));
            //buttons[i] = pollButtons(i); todo how buttons?
            povs[i] = pollPOV(i, POVCount(i));
        }
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
