package frc.lib.output;

import edu.wpi.first.wpilibj.Watchdog;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    //timedExecution(500, this::send);
    static Watchdog w = new Watchdog(500, Logger::send);
    private static List<String> messages = new ArrayList<String>();
    //todo watchdog time
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
}
