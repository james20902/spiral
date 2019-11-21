package frc.lib.output.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

public class TalonSRX extends MotorControllerBase {
    com.ctre.phoenix.motorcontrol.can.TalonSRX controller;
    int pastVelocity;
    long pastTime;
    public TalonSRX(){
        controller = new com.ctre.phoenix.motorcontrol.can.TalonSRX(-1);
    }

    public void setInverted(boolean inverted){
        super.setInverted(inverted);
        controller.setInverted(inverted);
    }

    @Override
    protected void sendPower() {
        controller.set(ControlMode.PercentOutput, this.getPower());
        pastVelocity = getRawVelocity();
        pastTime = System.currentTimeMillis();
    }

    @Override
    public int getRawPosition() {
        return controller.getSelectedSensorPosition(0);
    }

    @Override
    public int getRawVelocity() {
        return controller.getSelectedSensorVelocity(0);
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
}
