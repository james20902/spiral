package frc.lib.output.error;

import frc.lib.control.Task;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler extends Task {

    private static ErrorHandler instance;

    public static ErrorHandler getInstance(){
        if(instance == null){
            instance = new ErrorHandler();
        }
        return instance;
    }

    private static List<Error> errors;

    public ErrorHandler(){
        super(100);
    }

    @Override
    public void init(){
        errors = new ArrayList<Error>();
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

    public void standardExecution() {
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
