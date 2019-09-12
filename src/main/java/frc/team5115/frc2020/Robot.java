package frc.team5115.frc2020;

import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.output.Pathfollowing;
import frc.lib.output.error.ErrorHandler;
import frc.lib.utility.Console;
import frc.lib.utility.SystemClock;

public class Robot extends RobotBase {

  Pathfollowing pathfollower;

  public void start() {
    try {
      pathfollower = new Pathfollowing("Insert Path Name");
    } catch(Exception e){
      ErrorHandler.report(e, "Error initializing pathfinder. Ignore if not using.", "Pathfinding");
    }
  }

  public void loop() {
    Console.print(SystemClock.getSystemTime());
  }
}
