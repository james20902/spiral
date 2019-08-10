package frc.lib.control;

public class ControllerNotFoundException extends RuntimeException {

    ControllerNotFoundException(int port){
        super("Controller was not found at port " + port);
    }
}