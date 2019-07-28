package frc.lib.control;

public class StateController {

    public enum RobotState{DISABLED, AUTONOMOUS, OPERATOR}
    private RobotState systemState;
    private boolean stateLocked = false;

    private RobotState translateSystemState(){
        if(edu.wpi.first.wpilibj.RobotState.isDisabled()){
            return RobotState.DISABLED;
        } else if(edu.wpi.first.wpilibj.RobotState.isAutonomous()){
            return RobotState.AUTONOMOUS;
        } else {
            return RobotState.OPERATOR;
        }
    }

    public void updateSystemState(){
        if(systemState == translateSystemState()){
            return;
        }
        if(!stateLocked){
            systemState = translateSystemState();
        }
    }

    public RobotState getSystemState(){
        return systemState;
    }

    public void lockState(){
        stateLocked = true;
    }

}
