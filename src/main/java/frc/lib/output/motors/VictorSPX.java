package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class VictorSPX extends com.ctre.phoenix.motorcontrol.can.VictorSPX implements MotorControllerBase {
    double power;
    double lastPower;
    public VictorSPX(int deviceNumber) {
        super(deviceNumber);
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
        return 0;
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
