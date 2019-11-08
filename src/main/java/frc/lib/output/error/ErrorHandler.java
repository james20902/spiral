package frc.lib.output.error;


import edu.wpi.first.wpilibj.Filesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ErrorHandler implements Thread.UncaughtExceptionHandler{
    private static ErrorHandler instance;

    public static ErrorHandler getInstance(){
        if(instance == null){
            instance = new ErrorHandler();
        }
        return instance;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        crash(t, e);
    }

    public void crash(String reason){
        Console.reportError(errorQuip(), 1);
        Console.reportError("Spiral has crashed :(" + reason, 1);
        System.exit(1);
    }

    public void crash(Thread t, Throwable e){
        Console.reportError(errorQuip(), 1);
        Console.reportError("Spiral has crashed :(", 1, e.getStackTrace());
        System.exit(1);
    }

    public String errorQuip(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().getAbsolutePath()+"/robot.errors"));
            int randomLine = (int)(Math.random() * 20);
            for(int i = 0; i < randomLine; i++){
                reader.readLine();
            }
            return reader.readLine();
        } catch (IOException e){
            Console.reportWarning("Error file is missing or file length is wrong!");
        }
        return null;
    }
}
