package frc.lib.autonomous.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import frc.lib.chassis.drivebase.DriveChassis;

public class RamseteControllerDriveCommand extends AutonomousDriveCommand {

    private final Trajectory trajectory;
    private RamseteController feedbackController;
    private double startingTime;
    private double currentTime;

    public RamseteControllerDriveCommand(DriveChassis driveChassis, Trajectory trajectory){
        super(driveChassis);
        this.trajectory = trajectory;
        feedbackController = new RamseteController();
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
