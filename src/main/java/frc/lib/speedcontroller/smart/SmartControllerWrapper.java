package frc.lib.speedcontroller.smart;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import frc.lib.speedcontroller.basic.BasicSpeedController;
import frc.lib.speedcontroller.encoder.Encoder;

public class SmartControllerWrapper {

    private BasicSpeedController controllerInstance;
    private Encoder encoderInstance;
    private SimpleMotorFeedforward feedforwardCalculator;

    public SmartControllerWrapper(BasicSpeedController speedController,
                                  Encoder encoder,
                                  SimpleMotorFeedforward feedforwardCalculator){
        this.controllerInstance = speedController;
        this.encoderInstance = encoder;
        this.feedforwardCalculator = feedforwardCalculator;
    }

    public SmartControllerWrapper(BasicSpeedController speedController,
                                  Encoder encoder,
                                  double ks, double kv, double ka){
        this(speedController, encoder, new SimpleMotorFeedforward(ks, kv, ka));
    }


}
