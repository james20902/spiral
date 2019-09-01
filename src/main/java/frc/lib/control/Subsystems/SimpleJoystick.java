package frc.lib.control.Subsystems;

import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;

public class SimpleJoystick extends Subsystem {
    protected int joystick, controller;
    protected int[] motorID;
    boolean reversed;
    public SimpleJoystick(int timing, boolean reversed, int joystick, int motorID){
        super(timing);
        this.joystick = joystick;
        this.controller = 0;
    }
    public SimpleJoystick(int timing, boolean reversed, int joystick[], int controller, int motorID[]){//todo make Subsystem have identical constructor
        super(timing);
        this.joystick = joystick[0];
        this.controller = controller;
    }
    public void teleopPeriodic(){
        for(int i : motorID)
            Motors.motors.get(i).setPercent(ControllerManager.controllers[controller].getAxis(joystick));
    }

    public void autonomousPeriodic(){
        for(int i : motorID)
            Motors.motors.get(i).setPercent(ControllerManager.controllers[controller].getAxis(joystick));
    }

    public void kill(){}
}