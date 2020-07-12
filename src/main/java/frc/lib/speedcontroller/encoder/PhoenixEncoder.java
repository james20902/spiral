package frc.lib.speedcontroller.encoder;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class PhoenixEncoder implements Encoder{

    private final BaseMotorController nativeInstance;
    private double unitMultiplier = 1;

    public PhoenixEncoder(BaseMotorController motorControllerInstance){
        this.nativeInstance = motorControllerInstance;
    }

    public BaseMotorController getNativeInstance(){
        return nativeInstance;
    }

    @Override
    public void reset() {
        nativeInstance.setSelectedSensorPosition(0);
    }

    @Override
    public int getRawValue() {
        return nativeInstance.getSelectedSensorPosition();
    }

    @Override
    public double getUnitMultiplier() {
        return unitMultiplier;
    }

    @Override
    public void setUnitMultiplier(double multiplier) {
        this.unitMultiplier = multiplier;
    }

    @Override
    public int getSampleRate() {
        return 0;
    }

    @Override
    public void setSampleRate(int sampleRate) {
        //todo this
    }

    @Override
    public double getDistance() {
        return nativeInstance.getSelectedSensorPosition() * unitMultiplier;
    }

    @Override
    public double getVelocity() {
        return nativeInstance.getSelectedSensorVelocity() * unitMultiplier;
    }

    @Override
    public void setReversed(boolean invert) {
        nativeInstance.setSensorPhase(invert);
    }
}
