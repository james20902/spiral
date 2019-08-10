package frc.team5115.frc2020;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.control.SubsystemManager;
import frc.lib.utility.SystemState;

public class Robot extends RobotBase {

  public void start() {
    SubsystemManager.getInstance().addSubsystem(new TestSystem());
    SubsystemManager.getInstance().startSystems();
  }

  public void loop() {
//    System.out.println("REEEEEE");
  }

  @Override
  public void startCompetition() {
    while(!SystemState.getInstance().DSPresent()){
      SystemState.getInstance().updateSystemState();
    }
    System.out.println("DriverStation connected, initializing");

    start();
    HAL.observeUserProgramStarting();

    //noinspection InfiniteLoopStatement
    while(true){
      SystemState.getInstance().updateSystemState();
      loop();
    }
  }
}
