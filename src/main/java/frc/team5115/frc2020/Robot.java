package frc.team5115.frc2020;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib.input.ControllerNotFoundException;
import frc.lib.input.JoystickWrapper;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    try {
      JoystickWrapper.getInstance().findJoystick();
    } catch (ControllerNotFoundException e) {

    }
  }

  @Override
  public void robotPeriodic() {

  }

  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {

  }
}
