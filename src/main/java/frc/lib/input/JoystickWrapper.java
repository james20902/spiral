package frc.lib.input;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickWrapper {

    private Joystick joy;
    private static int port;
    private static JoystickWrapper instance;

    public enum POV { NEUTRAL, UP, UPPERRIGHT, RIGHT, LOWERRIGHT, DOWN, LOWERLEFT, LEFT, UPPERLEFT }

    public enum buttonState{ NEUTRAL, PRESSED, HELD, RELEASED }

    public static JoystickWrapper getInstance() {
        if(instance == null){
            instance = new JoystickWrapper();
        }
        return instance;
    }

    public JoystickWrapper(){}

    public JoystickWrapper(int port){
        this.port = port;
        joy = new Joystick(port);
    }

    public void findJoystick(){
        for(int i = 0; i < 5; i++){
            if((!new Joystick(i).getName().equals("")) && (new Joystick(i).getButtonCount() > 0)){
                registerStick(i);
                break;
            }
        }
        if(port == -1){
            throw new ControllerNotFoundException(port);
        }
    }

    public static void registerStick(int port){
        instance = new JoystickWrapper(port);
    }

    public static int getPort(){
        return port;
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