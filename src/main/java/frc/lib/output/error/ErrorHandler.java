package frc.lib.output.error;

import edu.wpi.first.wpilibj.Watchdog;
import frc.team5115.frc2020.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ErrorHandler implements Runnable{
    private static List<Error> errors;
    public void init(){
        errors = new ArrayList<Error>();
        Robot.robotInstance.addTask(this, 2, TimeUnit.MILLISECONDS);
    }

    public static void report(Error e) {
        if(errors.isEmpty() || !e.equals(errors.get(errors.size()-1))) {
            errors.add(e);
        }
    }

    public static void report(Exception e, String advice, String module) {
        Error err = new Error(e, advice, module);
        if(errors.isEmpty() || !err.equals(errors.get(errors.size()-1))) {
            errors.add(err);
        }
    }

    public static void report(String advice, String module) {
        Error e = new Error(null, advice, module);
        if(errors.isEmpty() || !e.equals(errors.get(errors.size()-1))) {
            errors.add(e);
        }
    }

    public void run() {
        if(errors.size() > 0){
            String message = "";
            message += "Error in Spiral. Details below. \n";
            for(Error e : errors) {
                message += e.print() + "\n";
            }
            errors.clear();
            message += "End of errors. Good luck debugging! Remember that the top error could cause the others, so take care of that first!";
            System.out.println(message);
        }
    }
}
