package frc.team5115.frc2020;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib.control.StateController;
import frc.lib.input.JoystickWrapper;

public class Robot extends TimedRobot {

  private StateController masterState;

  @Override
  public void robotInit() {
    masterState = new StateController();
  }

  @Override
  public void robotPeriodic() {
    masterState.updateSystemState();
    if(JoystickWrapper.getInstance().observeButton(1, JoystickWrapper.buttonState.PRESSED)){
      masterState.toggleStateLocked();
    }
  }








}
