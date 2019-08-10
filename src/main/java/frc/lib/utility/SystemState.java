package frc.lib.utility;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;

public class SystemState {

    private static final int DISABLED = 0;
    private static final int AUTONOMOUS = 1;
    private static final int OPERATOR = 2;
    private static final int TEST = 3;

    private ControlWord globalState;

    private static SystemState instance;

    public static SystemState getInstance(){
        if(instance == null) {
            instance = new SystemState();
        }
        return instance;
    }

    public void updateSystemState(){
        if(!HAL.isNewControlData()){
            System.out.println("stale");
            return;
        }

        HAL.getControlWord(globalState);

        if(globalState.getEnabled()){

        }
    }


}
