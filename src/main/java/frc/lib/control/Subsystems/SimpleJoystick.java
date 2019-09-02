package frc.lib.control.Subsystems;

import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;
import frc.lib.utility.Console;

public class SimpleJoystick extends Subsystem {
    protected int joystick, controller;
    protected int[] motorID;
    boolean reversed;
    public SimpleJoystick(int timing, boolean reversed, int joystick, int motorID){
        super(timing);
        this.joystick = joystick;
        this.controller = 0;
    }
    public SimpleJoystick(int timing, boolean reversed, int joystick[], int controller, int motorID[]){
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

    public void init() {}

    public void logSlowdown(){
        Console.reportWarning("Slowdown in a SimpleJoystick subsystem. Please slow it down a little.");
    }

    public void kill(){}
}