package frc.lib.output;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Watchdog;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    //timedExecution(500, this::send);
    static Watchdog w = new Watchdog(500, Logger::send);
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
        reportErrorImpl(true, 1, error, false);
    }

    public synchronized static void reportError(String error, StackTraceElement[] stackTrace) {
        reportErrorImpl(true, 1, error, stackTrace);
    }

    public synchronized static void reportWarning(String error) {
        reportErrorImpl(false, 1, error, false);
    }

    public synchronized static void reportWarning(String error, StackTraceElement[] stackTrace) {
        reportErrorImpl(false, 1, error, stackTrace);
    }

    private static void reportErrorImpl(boolean isError, int code, String error, boolean
            printTrace) {
        reportErrorImpl(isError, code, error, printTrace, Thread.currentThread().getStackTrace(), 3);
    }

    private static void reportErrorImpl(boolean isError, int code, String error,
                                        StackTraceElement[] stackTrace) {
        reportErrorImpl(isError, code, error, true, stackTrace, 0);
    }

    private static void reportErrorImpl(boolean isError, int code, String error,
                                        boolean printTrace, StackTraceElement[] stackTrace, int stackTraceFirst) {
        String locString;
        if (stackTrace.length >= stackTraceFirst + 1) {
            locString = stackTrace[stackTraceFirst].toString();
        } else {
            locString = "";
        }
        StringBuilder traceString = new StringBuilder();
        if (printTrace) {
            boolean haveLoc = false;
            for (int i = stackTraceFirst; i < stackTrace.length; i++) {
                String loc = stackTrace[i].toString();
                traceString.append("\tat ").append(loc).append('\n');
                // get first user function
                if (!haveLoc && !loc.startsWith("edu.wpi.first")) {
                    locString = loc;
                    haveLoc = true;
                }
            }
        }
        HAL.sendError(isError, code, false, error, locString, traceString.toString(), true);
    }
}
