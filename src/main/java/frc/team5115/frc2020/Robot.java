package frc.team5115.frc2020;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib.control.SubsystemManager;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    SubsystemManager.getInstance().addSubsystem(new TestSystem());
    SubsystemManager.getInstance().startSystems();
  }

  @Override
  public void robotPeriodic() {
    SubsystemManager.getInstance().masterState.set(getRobotStateID());
  }

  private int getRobotStateID(){
    if(RobotState.isDisabled()){
      return 0;
    } else if(RobotState.isAutonomous()){
      return 1;
    } else if(RobotState.isOperatorControl()){
      return 2;
    } else if(RobotState.isTest()){
      return 3;
    }
    return -1;
  }


}
