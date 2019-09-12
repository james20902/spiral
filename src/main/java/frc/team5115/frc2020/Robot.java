package frc.team5115.frc2020;

import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.input.Controller;
import frc.lib.input.ControllerManager;
import frc.lib.output.Pathfollowing;
import frc.lib.output.error.ErrorHandler;
import frc.lib.utility.Console;
import frc.lib.utility.SystemClock;

public class Robot extends RobotBase {

  Pathfollowing pathfollower;
  Controller stick;

  public void start() {
    try {
      pathfollower = new Pathfollowing("Insert Path Name");
    } catch(Exception e){ //SHALOM!
      ErrorHandler.report(e, "Error initializing pathfinder. Ignore if not using.", "Pathfinding");
    }
    Console.print(Runtime.getRuntime().availableProcessors());
    stick = ControllerManager.getJoystick(0);
  }

  public void loop() {
    Console.print(stick.getButtonState(1));
  }
}
