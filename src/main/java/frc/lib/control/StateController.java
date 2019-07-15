package frc.lib.control;

public class StateController {

    public enum RobotState{DISABLED, AUTONOMOUS, OPERATOR}
    private RobotState systemState;
    private boolean stateLocked;

    private boolean sameState(){
        boolean compare = true;
        switch(getSystemState()){
            case DISABLED:
                compare = edu.wpi.first.wpilibj.RobotState.isDisabled();
                break;
            case AUTONOMOUS:
                compare = edu.wpi.first.wpilibj.RobotState.isAutonomous();
                break;
            case OPERATOR:
                compare = edu.wpi.first.wpilibj.RobotState.isOperatorControl();
                break;
            default:
                break;
        }
        return compare;
    }

    public void updateSystemState(){
        if(sameState()){ return; }

        if(edu.wpi.first.wpilibj.RobotState.isDisabled()){
            changeSystemState(RobotState.DISABLED);
        } else if(edu.wpi.first.wpilibj.RobotState.isAutonomous()){
            changeSystemState(RobotState.AUTONOMOUS);
        } else {
            changeSystemState(RobotState.OPERATOR);
        }
    }

    public void changeSystemState(RobotState state){
        if(!stateLocked){
            systemState = state;
        }
    }

    public RobotState getSystemState(){
        return systemState;
    }

    public void toggleStateLocked(){
        stateLocked = !stateLocked;
    }

}
