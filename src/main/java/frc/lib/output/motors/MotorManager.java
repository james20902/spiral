package frc.lib.output.motors;

import frc.lib.control.Task;

import java.util.ArrayList;

public class MotorManager {
    ArrayList<MotorControllerBase> motors;
    static MotorManager instance;

    public static MotorManager getInstance(){
        if(instance == null) instance = new MotorManager();
        return instance;
    }

    public MotorManager() {
        motors = new ArrayList<MotorControllerBase>();
    }

    public void update() {
        for(MotorControllerBase m : motors) {
            m.sendPower();
        }
    }

    public void addMotor(MotorControllerBase m){
        motors.add(m);
    }
}
