package frc.team5115.frc2020;
//todo do a thing about power, supposedly robotController.getbatteryvoltage or pdp stuff
import edu.wpi.first.hal.HAL;
import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.utility.SystemState;

public class Robot extends RobotBase {

  public void start() {
    SubsystemManager.getInstance().addSubsystem(new TestSystem());
    //todo, ScheduledThreadPoolExecutor implementation
  }

  public void loop() {
  }
}
