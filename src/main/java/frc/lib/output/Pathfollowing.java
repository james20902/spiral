package frc.lib.output;

import edu.wpi.first.wpilibj.Notifier;
import frc.lib.utility.Settings;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class Pathfollowing {
    private EncoderFollower leftFoll, rightFoll;
    private Trajectory leftTrajectory, rightTrajectory;
    private Notifier notifier;

    public Pathfollowing(String pathName) throws Exception{
        leftTrajectory = PathfinderFRC.getTrajectory(pathName + ".left");
        rightTrajectory = PathfinderFRC.getTrajectory(pathName + ".right");

        leftFoll = new EncoderFollower(leftTrajectory);
        rightFoll = new EncoderFollower(rightTrajectory);

        leftFoll.configureEncoder((int)Motors.left.get(0).getRotations(), (int) Settings.ticksPerRevolution, Settings.wheelDiameter);
        leftFoll.configurePIDVA(1, 0, 0, 1/Settings.maxVelocity, 0);//todo auto tuning

        rightFoll.configureEncoder((int)Motors.right.get(0).getRotations(), (int)Settings.ticksPerRevolution, Settings.wheelDiameter);
        rightFoll.configurePIDVA(1, 0, 0, 1/Settings.maxVelocity, 0);

        notifier = new Notifier(this::followPath);//todo i think notifiers needed a rewrite?
        notifier.startPeriodic(leftTrajectory.get(0).dt);
    }

    private void followPath() {
        if (leftFoll.isFinished() || rightFoll.isFinished()) {
            notifier.stop();
        } else {
            double left_speed = leftFoll.calculate((int)Motors.left.get(0).getRotations());
            double right_speed = rightFoll.calculate((int)Motors.left.get(0).getRotations());
            double heading = 0;
            double desired_heading = Pathfinder.r2d(leftFoll.getHeading());
            double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
            double turn =  0.8 * (-1.0/80.0) * heading_difference;
            for(Motor m : Motors.left) {
                m.setPercent(left_speed + turn);
            }
            for(Motor m : Motors.right) {
                m.setPercent(right_speed + turn);
            }
        }
    }
}
