package frc.lib.speedcontroller.encoder;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

public class SparkMaxEncoder implements Encoder {

    private CANEncoder nativeInstance;

    public SparkMaxEncoder(CANSparkMax nativeController){
        nativeInstance = new CANEncoder(nativeController);
    }

    public CANEncoder getNativeInstance(){
        return nativeInstance;
    }

    @Override
    public void reset() {
        nativeInstance.setPosition(0);
    }

    @Override
    public int getRawValue() {
        return (int)(nativeInstance.getPosition() / getUnitMultiplier());
    }

    @Override
    public double getUnitMultiplier() {
        return nativeInstance.getPositionConversionFactor();
    }

    @Override
    public void setUnitMultiplier(double multiplier) {
        nativeInstance.setPositionConversionFactor(multiplier);
        nativeInstance.setVelocityConversionFactor(multiplier);
    }

    @Override
    public int getSampleRate() {
        return 0;
    }

    @Override
    public void setSampleRate(int sampleRate) {
        //todo find a more efficient way to do this other than just hard coding
        //some array
    }

    @Override
    public double getDistance() {
        return nativeInstance.getPosition();
    }

    @Override
    public double getVelocity() {
        return nativeInstance.getVelocity();
    }
}
