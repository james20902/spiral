package frc.lib.output.motors;

import edu.wpi.first.wpilibj.PWMSpeedController;

public class GenericPWM extends PWMSpeedController implements MotorControllerBase {
    double power;

    protected GenericPWM(int channel) {
        super(channel);
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public void sendPower() {
        super.set(power);
    }

    @Override
    public double getRawPosition() {
        return 0;
    }

    @Override
    public double getRawVelocity() {
        return 0;
    }

    @Override
    public double getRawAcceleration() {
        return 0;
    }

    @Override
    public double getVelocity() {
        return 0;
    }

    @Override
    public double getAcceleration() {
        return 0;
    }

    @Override
    public double getCurrent() {
        return 0;
    }
}
