package frc.lib.input;

import edu.wpi.first.hal.HAL;
import frc.lib.output.Logger;

import java.nio.ByteBuffer;

public class Input extends Thread{

    public static final byte MAX_JOYSTICKS = 6;

    private byte[] availableJoysticks;

    @Override
    public void run(){

    }

    public boolean joystickExists(byte port){
        ByteBuffer count = ByteBuffer.allocateDirect(1);
        HAL.getJoystickButtons(port, count);
        return count.get(0) > 0;
    }

    public void pollForJoysticks(){
        byte validStickCount = 0;
        for(byte i = 0; i < MAX_JOYSTICKS; i++){
            if(joystickExists(i)){
                availableJoysticks[validStickCount] = i;
                validStickCount++;
            }
        }
        if(validStickCount == 0){
            Logger.reportError("No joysticks detected!");
        }
    }


}