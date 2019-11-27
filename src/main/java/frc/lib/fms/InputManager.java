package frc.lib.fms;

import edu.wpi.first.hal.HAL;
import frc.lib.input.Controller;

import java.nio.ByteBuffer;

public class InputManager implements FMSUpdateable {

    private static final byte MAX_JOYSTICKS = 6;
    private static final int POV_COUNT = 9;

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
        int buttonBuffer = HAL.getJoystickButtons(port, buttonCountStorage);
        int buttonCount = buttonCount(port);
        short[] POVBuffer = new short[1];
        HAL.getJoystickPOVs(port, POVBuffer);
        boolean[] buttonOutput = new boolean[buttonCount + POV_COUNT];

        for(int i = 0; i < buttonCount - 1; i++){
            buttonOutput[i] = (buttonBuffer & 1 << i) != 0;
        }

        int compare = -1;
        for(int i = 0; i < POV_COUNT; i++){
            switch(i){
                case 0:
                    compare = -1;
                    break;
                case 1:
                    compare = 0;
                    break;
                case 2:
                    compare = 45;
                    break;
                case 3:
                    compare = 90;
                    break;
                case 4:
                    compare = 135;
                    break;
                case 5:
                    compare = 180;
                    break;
                case 6:
                    compare = 225;
                    break;
                case 7:
                    compare = 270;
                    break;
                case 8:
                    compare = 315;
                    break;
            }
            buttonOutput[i + buttonCount] = (POVBuffer[0] == compare);
        }
        return buttonOutput;
    }

    private float[] pollAxes(byte port){
        float[] axesOutput = new float[axesCount(port)];
        HAL.getJoystickAxes(port, axesOutput);
        return axesOutput;
    }

    private byte axesCount(byte port){
        return (byte)HAL.getJoystickAxes(port, new float[HAL.kMaxJoystickAxes]);
    }

    private byte buttonCount(byte port){
        HAL.getJoystickButtons(port, buttonCountStorage);
        return buttonCountStorage.get(0);
    }

}
