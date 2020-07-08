package frc.lib.speedcontroller.smart;

import frc.lib.speedcontroller.basic.BasicSpeedController;
import frc.lib.speedcontroller.encoder.BasicQuadratureEncoder;

public class SmartControllerWrapper {

    private BasicSpeedController controllerInstance;
    private BasicQuadratureEncoder encoderInstance;

    public SmartControllerWrapper(BasicSpeedController speedController, BasicQuadratureEncoder encoder){
        this.controllerInstance = speedController;
        this.encoderInstance = encoder;
    }
}
