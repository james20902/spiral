package frc.lib.control;

public class StateWrapper {

    public enum RobotState{DISABLED, AUTONOMOUS, OPERATOR}
    public static RobotState masterState;
    private boolean stateLocked;

    private boolean sameState(){
        boolean compare = true;
        switch(getMasterState()){
            case DISABLED:
                compare = edu.wpi.first.wpilibj.RobotState.isDisabled();
                break;
            case AUTONOMOUS:
                compare = edu.wpi.first.wpilibj.RobotState.isAutonomous();
                break;
            case OPERATOR:
                compare = edu.wpi.first.wpilibj.RobotState.isEnabled();
                break;
            default:
                break;
        }
        return compare;
    }

    public void updateMasterState(){
        if(sameState()){ return; }
        if(edu.wpi.first.wpilibj.RobotState.isDisabled()){
            changeMasterState(RobotState.DISABLED);
        } else if(edu.wpi.first.wpilibj.RobotState.isAutonomous()){
            changeMasterState(RobotState.AUTONOMOUS);
        } else {
            changeMasterState(RobotState.OPERATOR);
        }
    }

    public void changeMasterState(RobotState state){
        if(!stateLocked){
            masterState = state;
        }
    }

    public static RobotState getMasterState(){
        return masterState;
    }

    public void toggleStateLocked(){
        stateLocked = !stateLocked;
    }

}
