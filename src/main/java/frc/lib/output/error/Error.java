package frc.lib.output.error;

public class Error {
    String advice, module;
    Exception e;
    Error(Exception e, String advice, String module){
        this.e = e;
        this.advice = advice;
        this.module = module;
    }

    public String print(){
        if(e != null)
        return "There was an error in the " + module + " module. " + advice + " Stacktrace is below.\n" + e.toString();
        else return "There was an error in the " + module + " module. " + advice + " Stacktrace is below.\n";
    }
}
