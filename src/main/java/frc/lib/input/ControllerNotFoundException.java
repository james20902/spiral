package frc.lib.input;

public class ControllerNotFoundException extends RuntimeException {

    ControllerNotFoundException(int port){
        super("Controller was not found at port " + port);
    }
}