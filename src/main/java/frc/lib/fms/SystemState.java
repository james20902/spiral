package frc.lib.fms;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;

public class SystemState implements FMSUpdateable {

    public enum State {
        DISABLED, OPERATOR, AUTONOMOUS, TEST
    }

    private static SystemState instance;

    public static SystemState getInstance(){
        if(instance == null) {
            instance = new SystemState();
        }
        return instance;
    }

    private ControlWord globalState;
    private State currentState;

    public void init(){
        globalState = new ControlWord();
        currentState = State.DISABLED;
        FMSTask.registerUpdater(instance);
    }

    public void update(){
        HAL.getControlWord(globalState);
        if(isEnabled()){
            if(isAutonomous()){
                currentState = State.AUTONOMOUS;
            } else if(isTest()){
                currentState = State.TEST;
            } else {
                currentState = State.OPERATOR;
            }
        } else {
            currentState = State.DISABLED;
        }
    }

    public State getState(){ return currentState; }

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

    public boolean FMSPresent(){
        return globalState.getFMSAttached();
    }

    public boolean DSPresent(){
        return globalState.getDSAttached();
    }

}

