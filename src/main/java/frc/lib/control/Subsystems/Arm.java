package frc.lib.control.Subsystems;

import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;

public class Arm extends Subsystem {
    protected int joystick, motorID, controller;
    boolean reversed;
    public Arm(long timing,  boolean reversed, int motorID){
        super(timing);
        this.joystick = 0;
        this.controller = 0;
    }
    public void teleopPeriodic(){
        Motors.motors.get(motorID).setPercent(ControllerManager.controllers[controller].getAxis(joystick));
    }

    public void kill(){ }
}