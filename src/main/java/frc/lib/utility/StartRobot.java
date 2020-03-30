package frc.lib.utility;

import edu.wpi.first.hal.HAL;

public class StartRobot {

  public static void start(){
    if (!HAL.initialize(500, 0)) {
      throw new IllegalStateException("Failed to initialize HAL. Terminating");
    }

    HAL.sendConsoleLine("********** Spiral starting **********");

    HAL.waitForDSData();
    HAL.observeUserProgramStarting();
  }
}

