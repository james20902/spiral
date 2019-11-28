package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;

public class SparkMAX extends CANSparkMax implements MotorControllerBase {
    double power;
    double pastVelocity;
    double lastPower;
    long pastTime;

    public SparkMAX(int deviceID) {
        super(deviceID, MotorType.kBrushless);
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
        pastVelocity = getRawVelocity();
        pastTime = System.currentTimeMillis();
    }

    @Override
    public double getRawPosition() {
        return super.getEncoder().getPosition();
    }

    @Override
    public double getRawVelocity() {
        return super.getEncoder().getVelocity();
    }

    @Override
    public double getRawAcceleration() {
        return (int)((getRawVelocity()-pastVelocity)/(System.currentTimeMillis()-pastTime));
    }

    @Override
    public double getPosition() {
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
        return super.getOutputCurrent();
    }
}
