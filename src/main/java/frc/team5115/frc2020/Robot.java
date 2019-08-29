package frc.team5115.frc2020;
//todo do a thing about power, supposedly robotController.getbatteryvoltage or pdp stuff
import edu.wpi.first.wpilibj.DriverStation;
import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.input.Input;
import frc.lib.utility.Console;

public class Robot extends RobotBase {

  public void start() {
    SubsystemManager.getInstance().addSubsystem(new TestSystem());
    Input test = new Input();
    try {
      test.findJoysticks();
    } catch (NullPointerException ex){
      Console.reportWarning("nice", ex.getStackTrace());
    }
    //todo, ScheduledThreadPoolExecutor implementation
  }

  public void loop() {

  }
}
