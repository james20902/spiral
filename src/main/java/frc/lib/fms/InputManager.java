package frc.lib.fms;

import edu.wpi.first.hal.HAL;
import frc.lib.input.Controller;

import java.nio.ByteBuffer;

public class InputManager implements FMSUpdateable {

    private static final byte MAX_JOYSTICKS = 6;

    private static InputManager instance;

    public static InputManager getInstance(){
        if(instance == null){
            instance = new InputManager();
            FMSTask.registerUpdater(instance);
        }
        return instance;
    }

    private boolean[] controllerPresent;
    private ByteBuffer buttonCountStorage;

    @Override
    public void init() {
        buttonCountStorage = ByteBuffer.allocateDirect(0);
    }

    @Override
    public void update() {

    }


    private boolean[] pollButtons(byte port){
        int buttonBuffer = HAL.getJoystickButtons(port, ByteBuffer.allocateDirect(1));
        boolean[] buttonOutput = new boolean[buttonCount(port)];
        for(int i = 0; i < buttonOutput.length - 1; i++){
            buttonOutput[i] = (buttonBuffer & 1 << i) != 0;
        }
        return buttonOutput;
    }

    private float[] pollAxes(byte port){
        float[] axesOutput = new float[axesCount(port)];
        HAL.getJoystickAxes(port, axesOutput);
        return axesOutput;
    }

    private short[] pollPOV(byte port){
        short[] POVOutput = new short[POVCount(port)];
        HAL.getJoystickPOVs(port, POVOutput);
        return POVOutput;
    }

    private byte buttonCount(byte port){
        HAL.getJoystickButtons(port, buttonCountStorage);
        return buttonCountStorage.get(0);
    }

    private byte axesCount(byte port){
        return (byte)HAL.getJoystickAxes(port, new float[12]);
    }

    private byte POVCount(byte port){
        return (byte)HAL.getJoystickPOVs(port, new short[12]);
    }

}
