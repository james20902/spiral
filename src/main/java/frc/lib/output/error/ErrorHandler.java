package frc.lib.output.error;

import edu.wpi.first.wpilibj.Watchdog;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private static List<Error> errors;
    private static Watchdog thog;
    public static void init(){
        errors = new ArrayList<Error>();
        thog = new Watchdog(1000, ErrorHandler::print);
    }

    public static void report(Error e) {
        if(errors.isEmpty() || !e.equals(errors.get(errors.size()-1))) {
            errors.add(e);
        }
    }

    private static void print() {
        if(errors.size() > 0){
            System.out.println("Error in Spiral. Details below.");
            for(Error e : errors) {
                e.print();
            }
            errors.clear();
            System.out.println("End of errors. Good luck debugging!");
        }
    }
}
