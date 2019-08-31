package frc.team5115.frc2020;
//todo do a thing about power, supposedly robotController.getbatteryvoltage or pdp stuff
import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.input.Controller;
import frc.lib.output.Pathfollowing;
import frc.lib.output.error.ErrorHandler;
import frc.lib.utility.Console;
import frc.lib.utility.SystemClock;

public class Robot extends RobotBase {
  Controller stick;
  double checkpoint = 0;
  boolean pressed = false;
  public static Robot robotInstance = null;
  Pathfollowing pathfollower;

  public void start() {
    try {
      pathfollower = new Pathfollowing("Insert Path Name");
    } catch(Exception e){
      ErrorHandler.report(e, "Error initializing pathfinder. Ignore if not using.", "Pathfinding");
    }
    robotInstance = this;
    SubsystemManager.getInstance().addSubsystem(new TestSystem());
    Console.reportWarning("good morning gamers");
  }

  public void loop() {
    if(SystemClock.getSystemTime() > checkpoint + 5){
      //joystick values only update as fast as the poll all joysticks method is called
      //task framework is ESSENTIAL now, as the input task needs to run on its own loop at priority 10
//      System.out.println("hello world");
      checkpoint = SystemClock.getSystemTime();
    }
  }
}
