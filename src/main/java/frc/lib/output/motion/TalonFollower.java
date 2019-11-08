package frc.lib.output.motion;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import java.io.IOException;

public class TalonFollower {

    TalonSRX motorL, motorR, alternateL, alternateR;

    AHRS navxInstance;

    double wheelDiameter, maxVelocity;

    int ticksPerRev;

    Trajectory pathL, pathR;

    EncoderFollower followerL, followerR;

    public TalonFollower(TalonSRX left,
                         TalonSRX right,
                         TalonSRX followerL,
                         TalonSRX followerR,
                         AHRS navxInstance,
                         int ticksPerRev,
                         double wheelDiameter,
                         double maxVelocity){
        this.motorL = left;
        this.motorR = right;
        this.alternateL = followerL;
        this.alternateR = followerR;
        this.navxInstance = navxInstance;
        this.ticksPerRev = ticksPerRev;
        this.wheelDiameter = wheelDiameter;
        this.maxVelocity = maxVelocity;
    }

    public void loadTrajectory(String pathName){
        try {
            pathL = PathfinderFRC.getTrajectory(pathName + ".left");
            pathR = PathfinderFRC.getTrajectory(pathName + ".right");
        } catch (IOException e) {
            DriverStation.reportError("Path file not found!", false);
            return;
        }
        followerL = new EncoderFollower(pathL);
        followerR = new EncoderFollower(pathR);
    }

    public void primeFollower(double P, double D){
        if(pathL == null || pathR == null){
            throw new NullPointerException("loadTrajectory failed!");
        }

        followerL.configureEncoder(motorL.getSelectedSensorPosition(), ticksPerRev, wheelDiameter);
        followerR.configureEncoder(motorR.getSelectedSensorPosition(), ticksPerRev, wheelDiameter);

        followerL.configurePIDVA(P, 0, D, 1 / maxVelocity, 0);
        followerR.configurePIDVA(P, 0, D, 1 / maxVelocity, 0);
    }

    private double processTurn(){
        double angleDifference = Pathfinder.boundHalfDegrees(followerL.getHeading() - navxInstance.getYaw());
        angleDifference %= 360.0;
        if (Math.abs(angleDifference) > 180.0) {
            if(angleDifference > 0){
                angleDifference -= 360;
            } else {
                angleDifference += 360;
            }
        }
        return 0.8 * (-1.0/80.0) * angleDifference;
    }

    public void drive(){
        if(followerL.isFinished() || followerR.isFinished()) {
            motorL.set(ControlMode.PercentOutput, 0);
            motorR.set(ControlMode.PercentOutput, 0);
            alternateL.set(ControlMode.PercentOutput, 0);
            alternateR.set(ControlMode.PercentOutput, 0);
            return;
        }
        double outputL = followerL.calculate(motorL.getSelectedSensorPosition());
        double outputR = followerR.calculate(motorR.getSelectedSensorPosition());
        double turn = processTurn();
        motorL.set(ControlMode.PercentOutput, outputL + turn);
        motorR.set(ControlMode.PercentOutput, outputR - turn);
        alternateL.set(ControlMode.PercentOutput, outputL + turn);
        alternateR.set(ControlMode.PercentOutput, outputR - turn);
    }
}