package frc.lib.control;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SubsystemManager {

  private static SubsystemManager instance;

  private ScheduledThreadPoolExecutor threadPool;

  public static SubsystemManager getInstance() {
    if(instance == null){
      instance = new SubsystemManager();
    }
    return instance;
  }

  public SubsystemManager(){
    threadPool = new ScheduledThreadPoolExecutor(8);
  }

  public void registerSystem(Subsystem system){
    threadPool.scheduleAtFixedRate(system, 0, system.getMsExecutionTime(), TimeUnit.MILLISECONDS);
  }
}
