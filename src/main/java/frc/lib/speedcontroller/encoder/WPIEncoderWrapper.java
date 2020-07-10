package frc.lib.speedcontroller.encoder;

public class WPIEncoderWrapper extends edu.wpi.first.wpilibj.Encoder implements Encoder {

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

}
