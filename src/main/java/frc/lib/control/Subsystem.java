package frc.lib.control;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public abstract class Subsystem implements Runnable{

  private Command defaultCommand;
  private SubsystemScheduler scheduler;
  private SystemWatchdog watchdog;
  public Subsystem(){
    this(null);
  }

  public Subsystem(Command defaultCommand) {
    this(defaultCommand, 20);
  }

  public Subsystem(Command defaultCommand, int msExecutionTime) {
    this.defaultCommand = defaultCommand;
    scheduler = new SubsystemScheduler();
    watchdog = new SystemWatchdog(msExecutionTime);
  }
}
