package frc.lib.output;

public class DriveSignal {

    double rightSpeed;
    double leftSpeed;
    boolean brakeMode;

    public DriveSignal(double right, double left){
       this(right, left, false);
    }

    public DriveSignal(double right, double left, boolean brake){
        rightSpeed = right;
        leftSpeed = left;
        brakeMode = brake;
    }

    public double getRightSpeed(){
        return rightSpeed;
    }

    public double getLeftSpeed(){
        return leftSpeed;
    }

    public boolean isBrakeMode(){
        return brakeMode;
    }

}
