package frc.lib.utility;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;

import java.util.concurrent.atomic.AtomicInteger;

public class SystemState {

    public static final int DISABLED = 0;
    public static final int AUTONOMOUS = 1;
    public static final int OPERATOR = 2;
    public static final int TEST = 3;

    private ControlWord globalState = new ControlWord();

    private static SystemState instance;

    private AtomicInteger translatedState = new AtomicInteger();

    public static SystemState getInstance(){
        if(instance == null) {
            instance = new SystemState();
        }
        return instance;
    }

    public void updateSystemState(){
        if(!HAL.isNewControlData()){
            return;
        }
        HAL.getControlWord(globalState);
        translateState();
    }

    public int getState(){ return translatedState.get(); }

    public void translateState(){
        if(!isEnabled()){
            translatedState.set(DISABLED);
            HAL.observeUserProgramDisabled();
        } else if(isAutonomous()){
            translatedState.set(AUTONOMOUS);
            HAL.observeUserProgramAutonomous();
        } else if(isTest()){
            translatedState.set(TEST);
            HAL.observeUserProgramTest();
        } else {
            translatedState.set(OPERATOR);
            HAL.observeUserProgramTeleop();
        }
    }

    public boolean isEnabled(){
        return globalState.getEnabled();
    }

    public boolean isAutonomous(){
        return globalState.getAutonomous();
    }

    public boolean isTest(){
        return globalState.getTest();
    }

    public boolean emergencyStopped(){
        return globalState.getEStop();
    }

    //todo, if fms present assume we are in competiton
    //and rather than stopping the whole robot just stop the subsystem affected by a theoretical problem
    public boolean FMSPresent(){
        return globalState.getFMSAttached();
    }

    public boolean DSPresent(){
        return globalState.getDSAttached();
    }

}
