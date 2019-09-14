package frc.team5115.frc2020;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.lib.control.RobotBase;
import frc.lib.input.Controller;
import frc.lib.input.ControllerManager;

import frc.lib.utility.Console;

public class Robot extends RobotBase {

  Controller stick;

  public void start() {
    stick = ControllerManager.getJoystick(0);
    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    limelight.getEntry("ledMode").setNumber(1);
    limelight.getEntry("camMode").setNumber(1);
  }

  public void loop() {
    Console.print("axis " + stick.getAxis(1));
    Console.print("pov" + stick.getPOV());
    Console.print("button " + stick.getButtonState(1));
  }
}
