package frc.lib.output.motors;

import frc.lib.control.Task;

import java.util.ArrayList;

public class MotorManager extends Task {
    ArrayList<MotorControllerBase> motors;

    public MotorManager(int delay) {
        super(delay);
    }

    @Override
    public void init() {
        motors = new ArrayList<MotorControllerBase>();
    }

    @Override
    public void update() {
        for(MotorControllerBase m : motors) {
            m.sendPower();
        }
    }

    public void addMotor(MotorControllerBase m){
        motors.add(m);
    }
}
