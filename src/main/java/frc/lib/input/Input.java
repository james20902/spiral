package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.utility.Console;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Input{

    public static final byte MAX_JOYSTICKS = 6;

    private Joystick[] availableJoysticks;

    public Input(){
    }

    public void pollAllJoysticks(){
        for(Joystick instance : availableJoysticks){
            byte port = instance.getPort();
            instance.updateData(
                    pollButtons(port),
                    pollAxes(port, instance.getAxesCount()),
                    pollPOV(port, instance.getPOVcount())
            );
        }
    }

    public Joystick getJoystick(int port){
        return availableJoysticks[port];
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

    public byte buttonCount(byte port){
        ByteBuffer count = ByteBuffer.allocateDirect(1);
        HAL.getJoystickButtons(port, count);
        return count.get(0);
    }

    public byte axesCount(byte port){
        return (byte)HAL.getJoystickAxes(port, new float[12]);
    }

    public byte POVCount(byte port){
        return (byte)HAL.getJoystickPOVs(port, new short[12]);
    }

    public void findJoysticks(){
        List<Joystick> storage = new ArrayList<>();
        for(byte i = 0; i < MAX_JOYSTICKS; i++){
            if(buttonCount(i) > 0){
                storage.add(new Joystick(i));
            }
        }
        availableJoysticks = storage.toArray(new Joystick[storage.size()]);
        if (availableJoysticks.length == 0){
            throw new NullPointerException("no joysticks present!");
        }
        for(Joystick stick : availableJoysticks){
            byte port = stick.getPort();
            stick.initialize(buttonCount(port), axesCount(port), POVCount(port));
        }
    }


}