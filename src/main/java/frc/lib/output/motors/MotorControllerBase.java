package frc.lib.output.motors;

public abstract class MotorControllerBase {

    private int ID;
    private boolean invert = false;
    private boolean lock = false;
    private double power;

    public void setID(int id){
        this.ID = id;
    }

    public void setPower(double power){
        if(power == this.power)
            return;
        this.power = power;
    }

    double getPower(){
        return power;
    }

    protected abstract void sendPower();

    public abstract int getRawPosition();
    public abstract int getRawVelocity();
    public abstract int getRawAcceleration();

    public abstract double getPosition();
    public abstract double getVelocity();
    public abstract double getAcceleration();

    public void setInverted(boolean isInverted){
        invert = isInverted;
    }

    public boolean getInverted(){
        return invert;
    }

    public void stopMotor(){
        setPower(0);
    }

}
