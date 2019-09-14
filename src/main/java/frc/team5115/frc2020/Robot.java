package frc.team5115.frc2020;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.input.Controller;
import frc.lib.input.ControllerManager;

import frc.lib.utility.Console;

public class Robot extends RobotBase {

  Controller stick;

  public void start() {
    stick = ControllerManager.getJoystick();
    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    limelight.getEntry("ledMode").setNumber(1);
    limelight.getEntry("camMode").setNumber(1);
    SubsystemManager.getInstance().startSubsystem(new TestSystem());
  }

  public void loop() {
//    Console.print("axis " + stick.getAxis(1));
//    Console.print("pov" + stick.getPOV());
//    Console.print("button " + stick.getButtonState(1));
  }
}
