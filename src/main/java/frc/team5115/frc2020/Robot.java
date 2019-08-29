package frc.team5115.frc2020;
//todo do a thing about power, supposedly robotController.getbatteryvoltage or pdp stuff
import edu.wpi.first.wpilibj.Timer;
import frc.lib.control.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.input.Input;
import frc.lib.input.Joystick;
import frc.lib.utility.Console;
import frc.lib.utility.SystemClock;

public class Robot extends RobotBase {

  Input manager;
  Joystick stick;
  double checkpoint = 0;
  boolean pressed = false;

  public void start() {
    SubsystemManager.getInstance().addSubsystem(new TestSystem());
    Console.reportWarning("good morning gamers");
    manager = new Input();
    try {
      manager.findJoysticks();
    } catch (NullPointerException ex){
      Console.reportWarning("nice", ex.getStackTrace());
    }
    stick = manager.getJoystick(0);
    //todo, ScheduledThreadPoolExecutor implementation
  }

  public void loop() {
    if(Timer.getFPGATimestamp() > checkpoint + 1){
      //joystick values only update as fast as the poll all joysticks method is called
      //task framework is ESSENTIAL now, as the input task needs to run on its own loop at priority 10
      manager.pollAllJoysticks();
      if(stick.buttonState(1) == Joystick.ButtonState.PRESSED){
        pressed = true;
      }
      System.out.println(pressed);
      checkpoint = Timer.getFPGATimestamp();
    }
  }
}
