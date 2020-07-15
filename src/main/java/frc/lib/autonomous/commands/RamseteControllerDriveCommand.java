package frc.lib.autonomous.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import frc.lib.autonomous.TrajectoryGenerator;

import java.util.List;

public class RamseteControllerDriveCommand extends AutonomousDriveCommand {

    private final Trajectory trajectory;
    private RamseteController feedbackController;
    private double startingTime;
    private double currentTime;

    public RamseteControllerDriveCommand(Pose2d start,
                                         List<Translation2d> interiorHeadings,
                                         Pose2d end,
                                         TrajectoryConfig configuration){
        if(configuration != null){
            TrajectoryGenerator.getInstance().setConfiguration(configuration);
        }
        trajectory = TrajectoryGenerator.getInstance()
                .generateCubicTrajectory(start, interiorHeadings, end);
        feedbackController = new RamseteController();
    }

    public RamseteControllerDriveCommand(Pose2d start,
                                         List<Translation2d> interiorHeadings,
                                         Pose2d end){
        this(start, interiorHeadings, end, null);
    }

    public RamseteControllerDriveCommand(List<Pose2d> poses, TrajectoryConfig configuration){
        if(configuration != null){
            TrajectoryGenerator.getInstance().setConfiguration(configuration);
        }
        trajectory = TrajectoryGenerator.getInstance().generateQuinticTrajectory(poses);
        feedbackController = new RamseteController();
    }

    public RamseteControllerDriveCommand(List<Pose2d> poses){
        this(poses, null);
    }

    @Override
    public void initialize(){
        startingTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute(){
        currentTime = Timer.getFPGATimestamp() - startingTime;

        driveChassis.processChassisSpeeds(
                feedbackController.calculate(driveChassis.getCurrentPose(),
                trajectory.sample(currentTime)));
    }

    @Override
    public boolean isFinished(){
        return feedbackController.atReference() || (currentTime > trajectory.getTotalTimeSeconds());
    }
}
