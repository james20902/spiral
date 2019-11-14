package frc.lib.output.motors;

public class MotorControllerBase {

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
    }

    double getPower(){
        return power;
    }

    public int getRawPosition(){return 0;}
    public int getRawVelocity(){return 0;}
    public int getRawAcceleration(){return 0;}

    public double getPosition(){return 0;}
    public double getVelocity(){return 0;}
    public double getAcceleration(){return 0;}

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
