package frc.lib.output.error;

public class Error {
    String advice, module;
    Exception e;
    Error(Exception e, String advice, String module){
        this.e = e;
        this.advice = advice;
        this.module = module;
    }

    public void print(){
        System.out.println("There was an error in the " + module + "module. " + advice + " Stacktrace is below.");
        e.printStackTrace();
    }
}
