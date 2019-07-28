package frc.team5115.frc2020;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib.control.StateController;
import frc.lib.control.SubsystemManager;
import frc.lib.input.JoystickWrapper;

public class Robot extends TimedRobot {

  private StateController masterState;
  public static JoystickWrapper joystick;

  @Override
  public void robotInit() {
    joystick = new JoystickWrapper();
    joystick.findJoystick();
    masterState = new StateController();
    SubsystemManager.getInstance().addSubsystem(new TestSystem(5));
    SubsystemManager.getInstance().startSystems();
    SubsystemManager.getInstance().printSystems();
    SubsystemManager.getInstance().stopSystem("TestSystem");
  }

  @Override
  public void robotPeriodic() {
    masterState.updateSystemState();
  }








}
