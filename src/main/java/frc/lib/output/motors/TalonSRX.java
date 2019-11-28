package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

public class TalonSRX extends com.ctre.phoenix.motorcontrol.can.TalonSRX implements MotorControllerBase {
    double power;
    int pastVelocity;
    long pastTime;

    public TalonSRX(int can){
        super(can);
    }

    public void setInverted(boolean inverted){
        this.setInverted(inverted);
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
        this.set(ControlMode.PercentOutput, this.getPower());
        pastVelocity = getRawVelocity();
        pastTime = System.currentTimeMillis();
    }

    @Override
    public int getRawPosition() {
        return this.getSelectedSensorPosition(0);
    }

    @Override
    public int getRawVelocity() {
        return this.getSelectedSensorVelocity(0);
    }

    @Override
    public int getRawAcceleration() {
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
}
