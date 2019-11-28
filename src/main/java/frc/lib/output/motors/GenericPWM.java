package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.PWMSpeedController;

public class GenericPWM extends PWMSpeedController implements MotorControllerBase {
    double power;
    double lastPower;

    protected GenericPWM(int channel) {
        super(channel);
        MotorManager.getInstance().addMotor(this);
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
        if(power != this.getPower()) {
            super.set(this.getPower());
            lastPower = this.getPower();
        }
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
