package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Watchdog;

import java.util.ArrayList;
import java.util.List;

public class Console {
    static Watchdog w = new Watchdog(1000, Console::send);
    private static List<String> messages = new ArrayList<String>();
    //todo watchdog
    /*
    * Adds messages to a queue that is printed every 10ms
    * This reduces lag caused by many print messages
     */
    public static void print(String m){
        messages.add(m);
    }

    private static void send() {
        String message = "";
        for (String m : messages) {
            message += m;
            message += "\n";
        }
        System.out.println(message);
    }

    public synchronized static void reportError(String error) {
        HALErrorReport(true, error, null);
    }

    public synchronized static void reportError(String error, StackTraceElement[] stackTrace) {
        HALErrorReport(true, error, stackTrace);
    }

    public synchronized static void reportWarning(String error) {
        HALErrorReport(false, error, null);
    }

    public synchronized static void reportWarning(String error, StackTraceElement[] stackTrace) {
        HALErrorReport(false, error, stackTrace);
    }

    private synchronized static void HALErrorReport(boolean error, String message, StackTraceElement[] stackTrace){
        if(stackTrace == null){
            HAL.sendError(error, 1 ,false, message, "", "", false);
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
        HAL.sendError(error, 1, false, message, locString, traceString.toString(), false);
    }
}
