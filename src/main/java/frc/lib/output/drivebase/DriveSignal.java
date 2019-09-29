package frc.lib.output.drivebase;

public class DriveSignal {

    double rightSpeed;
    double leftSpeed;
    boolean brakeMode;

    public DriveSignal(double left, double right){
       this(left, right, false);
    }

    public DriveSignal(double left, double right, boolean brake){
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
