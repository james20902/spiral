package frc.lib.output.motors;

import frc.lib.control.Task;

public class MotorTask extends Task {
    public MotorTask(int delay) {
        super(delay);
    }

    @Override
    public void init() {
        MotorManager.getInstance();
    }

    @Override
    public void update() {
        MotorManager.getInstance().update();
    }
}
