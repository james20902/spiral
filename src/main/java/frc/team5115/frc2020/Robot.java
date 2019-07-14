package frc.team5115.frc2020;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib.control.StateWrapper;
import frc.lib.input.JoystickWrapper;

public class Robot extends TimedRobot {

  private StateWrapper masterState;

  @Override
  public void robotInit() {
    masterState = new StateWrapper();
  }

  @Override
  public void robotPeriodic() {
    masterState.updateMasterState();
    if(JoystickWrapper.getInstance().observeButton(1, JoystickWrapper.buttonState.PRESSED)){
      masterState.toggleStateLocked();
    }
  }



}
