package frc.lib.input;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickWrapper {

    private Joystick joy;

    public enum POV { NEUTRAL, UP, UPPERRIGHT, RIGHT, LOWERRIGHT, DOWN, LOWERLEFT, LEFT, UPPERLEFT }

    public enum buttonState{ NEUTRAL, PRESSED, HELD, RELEASED }

    public void findJoystick(){
        for(int i = 0; i < 5; i++){
            if(new Joystick(i).getButtonCount() > 0){
                registerStick(i);
                break;
            }
        }
    }

    public void registerStick(int port){
        joy = new Joystick(port);
        if(!joystickExists()){ throw new ControllerNotFoundException(port); }
    }

    public boolean joystickExists(){
        return joy.getButtonCount() != 0;
    }

    public boolean getPOV(POV pov){
        int compare = -1;
        switch(pov){
            case NEUTRAL:
                compare = -1;
                break;
            case UP:
                compare = 0;
                break;
            case UPPERRIGHT:
                compare = 45;
                break;
            case RIGHT:
                compare = 90;
                break;
            case LOWERRIGHT:
                compare = 135;
                break;
            case DOWN:
                compare = 180;
                break;
            case LOWERLEFT:
                compare = 225;
                break;
            case LEFT:
                compare = 270;
                break;
            case UPPERLEFT:
                compare = 315;
                break;
            default:
                break;
        }
        return joy.getPOV() == compare;
    }

    public buttonState getButtonState(int id){
        if(joy.getRawButton(id)){
            if(joy.getRawButtonPressed(id)){
                return buttonState.PRESSED;
            }
            return buttonState.HELD;
        } else {
            if(joy.getRawButtonReleased(id)){
                return buttonState.RELEASED;
            }
            return buttonState.NEUTRAL;
        }
    }

    public boolean observeButton(int id, buttonState condition){
        return getButtonState(id) == condition;
    }
}