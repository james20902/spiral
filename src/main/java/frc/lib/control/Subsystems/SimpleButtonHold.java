package frc.lib.control.Subsystems;

import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;

public class SimpleButtonHold extends Subsystem {
    protected int button, motorID, controller;
    boolean reversed;
    public SimpleButtonHold(long timing, boolean reversed, int button, int motorID){
        super(timing);
        this.button = button;
        this.controller = 0;
    }
    public void teleopPeriodic(){
        Motors.motors.get(motorID).setPercent(ControllerManager.controllers[controller].getButtonState(button).getSpeed());
    }

    public void autonomousPeriodic(){
        Motors.motors.get(motorID).setPercent(ControllerManager.controllers[controller].getButtonState(button).getSpeed());
    }

    public void kill(){ }
}