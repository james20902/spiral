package frc.lib.output.motors;

public interface MotorControllerBase {
    public void setPower(double power);

    abstract double getPower();

    public void sendPower();

    public int getRawPosition();
    public int getRawVelocity();
    public int getRawAcceleration();

    public double getPosition();
    public double getVelocity();
    public double getAcceleration();

    public void stopMotor();
}
