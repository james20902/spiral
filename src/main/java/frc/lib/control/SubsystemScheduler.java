package frc.lib.control;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.Stack;

public class SubsystemScheduler {

  Stack<Command> commandStack;

  public SubsystemScheduler(){
    commandStack = new Stack<>();
  }

  public void execute(){
    while(!commandStack.isEmpty()){
      commandStack.pop().execute();
    }
  }

  public void addCommand(Command command){
    commandStack.add(command);
  }

}
