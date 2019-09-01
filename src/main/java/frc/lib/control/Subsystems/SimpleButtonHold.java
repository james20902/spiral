package frc.lib.control.Subsystems;

import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;

public class SimpleButtonHold extends Subsystem {
    protected int button, controller;
    protected int[] motorID;
    boolean reversed;

    public SimpleButtonHold(int timing, boolean reversed, int button, int motorID){
        super(timing);
        this.button = button;
        this.controller = 0;
    }
    public SimpleButtonHold(int timing, boolean reversed, int[] button, int controller, int motorID[]){
        super(timing);
        this.button = button[0];
        this.controller = controller;
        this.motorID = motorID;
    }

    public void teleopPeriodic(){
        for(int i : motorID)
            Motors.motors.get(i).setPercent(ControllerManager.controllers[controller].getButtonState(button).getSpeed());
    }

    public void autonomousPeriodic(){
        for(int i : motorID)
            Motors.motors.get(i).setPercent(ControllerManager.controllers[controller].getButtonState(button).getSpeed());
    }

    public void kill(){ }
}