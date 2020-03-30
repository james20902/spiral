package frc.lib.control;

import edu.wpi.first.wpilibj.DriverStation;

public class SystemWatchdog {

  private long currentTime;
  private long targetTime;
  private int msExecutionTime;

  public SystemWatchdog(int msExecutionTime) {
    this.msExecutionTime = msExecutionTime;
  }

  public void feed(){
    currentTime = System.currentTimeMillis();
    if(currentTime > targetTime){
      DriverStation.reportError("This is so sad", false);
    }
    targetTime = currentTime + msExecutionTime;
  }
}
