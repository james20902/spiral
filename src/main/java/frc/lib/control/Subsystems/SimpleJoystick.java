package frc.lib.control.Subsystems;

import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;

public class SimpleJoystick extends Subsystem {
    protected int joystick, motorID, controller;
    boolean reversed;
    public SimpleJoystick(int timing, boolean reversed, int joystick, int motorID){
        super(timing);
        this.joystick = joystick;
        this.controller = 0;
    }
    public SimpleJoystick(int timing, boolean reversed, int joystick, int controller, int motorID){
        super(timing);
        this.joystick = joystick;
        this.controller = controller;
    }
    public void teleopPeriodic(){
        Motors.motors.get(motorID).setPercent(ControllerManager.controllers[controller].getAxis(joystick));
    }

    public void autonomousPeriodic(){
        Motors.motors.get(motorID).setPercent(ControllerManager.controllers[controller].getAxis(joystick));
    }

    public void kill(){ }
}