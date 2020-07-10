package frc.lib.speedcontroller.encoder;

public interface Encoder {

    void reset();

    int getRawValue();

    double getUnitMultiplier();

    void setUnitMultiplier(double multiplier);

    int getSampleRate();

    void setSampleRate(int sampleRate);

    double getDistance();

    double getVelocity();

    void setReversed(boolean invert);

}
