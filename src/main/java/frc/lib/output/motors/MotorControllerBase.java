package frc.lib.output.motors;

public interface MotorControllerBase {
    public void setPower(double power);

    abstract double getPower();

    public void sendPower();

    public double getRawPosition();
    public double getRawVelocity();
    public double getRawAcceleration();

    public double getPosition();
    public double getVelocity();
    public double getAcceleration();

    public void stopMotor();

    public double getCurrent();
}
