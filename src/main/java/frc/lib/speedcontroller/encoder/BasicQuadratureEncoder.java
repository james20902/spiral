package frc.lib.speedcontroller.encoder;

public interface BasicQuadratureEncoder {

    void reset();

    int getRawValue();

    double getUnitMultiplier();

    void setUnitMultiplier(double multiplier);

    int getSampleRate();

    void setSampleRate(int sampleRate);

    double getDistance();

    double getVelocity();

    double getAcceleration();

}
