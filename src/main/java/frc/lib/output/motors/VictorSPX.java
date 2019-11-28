package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class VictorSPX extends com.ctre.phoenix.motorcontrol.can.VictorSPX implements MotorControllerBase {
    double power;
    public VictorSPX(int deviceNumber) {
        super(deviceNumber);
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
        super.set(ControlMode.PercentOutput, this.getPower());
    }

    @Override
    public int getRawPosition() {
        return super.getSelectedSensorPosition(0);
    }

    @Override
    public int getRawVelocity() {
        return super.getSelectedSensorVelocity(0);
    }

    @Override
    public int getRawAcceleration() {
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
