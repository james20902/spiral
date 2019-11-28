package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

public class TalonSRX extends com.ctre.phoenix.motorcontrol.can.TalonSRX implements MotorControllerBase {
    double power;
    double pastVelocity;
    double lastPower;
    long pastTime;

    public TalonSRX(int can){
        super(can);
        MotorManager.getInstance().addMotor(this);
    }

    public void setInverted(boolean inverted){
        super.setInverted(inverted);
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
            super.set(ControlMode.PercentOutput, this.getPower());
            lastPower = this.getPower();
        }
        pastVelocity = getRawVelocity();
        pastTime = System.currentTimeMillis();
    }

    @Override
    public double getRawPosition() {
        return super.getSelectedSensorPosition(0);
    }

    @Override
    public double getRawVelocity() {
        return super.getSelectedSensorVelocity(0);
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
    public void stopMotor() {
        power = 0;
        sendPower();
    }

    @Override
    public double getCurrent() {
        return super.getOutputCurrent();
    }
}
