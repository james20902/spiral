package frc.lib.speedcontroller.encoder;

import edu.wpi.first.wpilibj.Encoder;

public class WPIEncoderWrapper extends Encoder implements BasicQuadratureEncoder{

    public WPIEncoderWrapper(int channelA, int channelB){
        super(channelA, channelB);
    }

    public WPIEncoderWrapper(int channelA, int channelB, boolean reverseDirection, EncodingType encodingType) {
        super(channelA, channelB, reverseDirection, encodingType);
    }

    @Override
    public int getRawValue() {
        return getRaw();
    }

    @Override
    public double getUnitMultiplier() {
        return getDistancePerPulse();
    }

    @Override
    public void setUnitMultiplier(double multiplier) {
        setDistancePerPulse(multiplier);
    }

    @Override
    public int getSampleRate() {
        return getSamplesToAverage();
    }

    @Override
    public void setSampleRate(int sampleRate) {
        setSamplesToAverage(sampleRate);
    }

    @Override
    public double getVelocity() {
        return getRate();
    }

    //this needs to be constantly updated
    @Override
    public double getAcceleration() {
        return 0;
    }
}
