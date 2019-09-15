package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import frc.lib.control.Task;

import java.util.Stack;

public class Console extends Task {
    private static Stack<String> messages = new Stack<>();
    private static Console instance;
    //todo watchdog
    /*
    * Adds messages to a queue that is printed every 10ms
    * This reduces lag caused by many print messages
     */

    public static Console getInstance(){
        if(instance == null){
            instance = new Console();
        }
        return instance;
    }
    public static void print(Object m){
        if(SystemState.getInstance().FMSPresent())
            return;
        messages.push(m.toString());
    }

    @Override
    public void run() {
        super.run();
        String message = "";
        while(!messages.isEmpty()){
            message += messages.pop();
            message += "\n";
        }
        System.out.print(message);
    }

    public synchronized static void reportError(String error, int errorCode) {
        HALErrorReport(true, errorCode, error, null);
    }

    public synchronized static void reportError(String error, int errorCode, StackTraceElement[] stackTrace) {
        HALErrorReport(true, errorCode, error, stackTrace);
    }

    public synchronized static void reportWarning(String error) {
        HALErrorReport(false, 0, error, null);
    }

    public synchronized static void reportWarning(String error, StackTraceElement[] stackTrace) {
        HALErrorReport(false, 0, error, stackTrace);
    }

    private synchronized static void HALErrorReport(boolean error, int errorCode, String message, StackTraceElement[] stackTrace){
        if(stackTrace == null){
            HAL.sendError(error, errorCode ,false, message, "", "", false);
            return;
        }
        String locString;
        if (stackTrace.length >= 1) {
            locString = stackTrace[0].toString();
        } else {
            locString = "";
        }
        StringBuilder traceString = new StringBuilder();
        boolean haveLoc = false;
        for (int i = 0; i < stackTrace.length; i++) {
            String loc = stackTrace[i].toString();
            traceString.append("\tat ").append(loc).append('\n');
            // get first user function
            if (!haveLoc && !loc.startsWith("edu.wpi.first")) {
                locString = loc;
                haveLoc = true;
            }
        }
        HAL.sendError(error, errorCode, false, message, locString, traceString.toString(), false);
    }

    @Override
    public void init() {

    }

    @Override
    public void logSlowdown() {

    }
}
