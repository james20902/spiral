package frc.lib.speedcontroller.smart;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import frc.lib.speedcontroller.basic.BasicSpeedController;
import frc.lib.speedcontroller.encoder.Encoder;

public class SmartControllerWrapper {

    private final BasicSpeedController controllerInstance;
    private final Encoder encoderInstance;
    private final SimpleMotorFeedforward feedforwardCalculator;
    private final PIDController pidController;

    public SmartControllerWrapper(BasicSpeedController speedController,
                                  Encoder encoder,
                                  SimpleMotorFeedforward feedforwardCalculator,
                                  PIDController pidController){
        this.controllerInstance = speedController;
        this.encoderInstance = encoder;
        this.feedforwardCalculator = feedforwardCalculator;
        this.pidController = pidController;
    }

    public SmartControllerWrapper(BasicSpeedController speedController,
                                  Encoder encoder,
                                  double kS, double kV, double kA,
                                  double kP, double kI, double kD){
        this(speedController, encoder, new SimpleMotorFeedforward(kS, kV, kA), new PIDController(kP, kI, kD));
    }

    public SmartControllerWrapper(BasicSpeedController speedController,
                                  Encoder encoder,
                                  double kS, double kV, double kA){
        this(speedController, encoder, kS, kV, kA, 0 ,0 ,0);
    }


    public SmartControllerWrapper(BasicSpeedController speedController,
                                  Encoder encoder){
        this(speedController, encoder, 0, 0, 0, 0, 0, 0);
    }

    public void setSpeed(double speed){
        controllerInstance.setSpeed(speed);
    }

    public void setVelocity(double velocity){
        controllerInstance.setVoltage(
                feedforwardCalculator.calculate(encoderInstance.getVelocity())
            + pidController.calculate(encoderInstance.getVelocity(), velocity));
    }

    public void setPosition(double position){
        controllerInstance.setVoltage(
                feedforwardCalculator.ks +
                        pidController.calculate(encoderInstance.getDistance(), position));
    }


}
