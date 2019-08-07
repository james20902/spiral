package frc.team5115.frc2020;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.control.SubsystemManager;

public class Robot extends RobotBase {

  public void start() {
//    SubsystemManager.getInstance().addSubsystem(new TestSystem());
//    SubsystemManager.getInstance().startSystems();
  }

  public void loop() {
    //todo translate raw HAL byte to something readable
    SubsystemManager.getInstance().pollMasterState();
  }

  @Override
  public void startCompetition() {
    start();
    HAL.observeUserProgramStarting();

    //noinspection InfiniteLoopStatement
    while(true){
      loop();
    }
  }
}
