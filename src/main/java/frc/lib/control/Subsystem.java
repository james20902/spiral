package frc.lib.control;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import java.util.List;

public abstract class Subsystem implements Runnable{

  private String systemName = this.getClass().getSimpleName();
  private Command defaultCommand;
  private SubsystemScheduler scheduler;
  private SystemWatchdog watchdog;
  private int msExecutionTime;

  interface SystemState{
    void setContext(SystemState state);

    SystemState getContext();

    List<SystemState> getValidStates();

    boolean validStateChange(SystemState desiredState);

    boolean validStateChangeContext(SystemState desiredState);
  }

  private enum internalState implements SystemState{
    ENABLED, DISABLED;

    private List<SystemState> validStates;

    private SystemState context;

    @Override
    public void setContext(SystemState state){
      context = state;
    }

    @Override
    public SystemState getContext() {
      return context;
    }

    @Override
    public List<SystemState> getValidStates() {
      return validStates;
    }

    @Override
    public boolean validStateChange(SystemState desiredState) {
      return validStates == null || validStates.contains(desiredState);
    }

    @Override
    public boolean validStateChangeContext(SystemState desiredState) {
      //todo this
      return false;
    }
  }


  public Subsystem(){
    this(null);
  }

  public Subsystem(Command defaultCommand) {
    this(defaultCommand, 20);
  }

  public Subsystem(Command defaultCommand, int msExecutionTime) {
    if(defaultCommand instanceof InstantCommand){
      DriverStation.reportWarning("The default command for system " + systemName + " is an instant command, which could lead to unpredictable behavior!", false);
    }
    this.defaultCommand = defaultCommand;
    scheduler = new SubsystemScheduler();
    watchdog = new SystemWatchdog(msExecutionTime);
    this.msExecutionTime = msExecutionTime;
  }

  public int getMsExecutionTime(){
    return msExecutionTime;
  }
}
