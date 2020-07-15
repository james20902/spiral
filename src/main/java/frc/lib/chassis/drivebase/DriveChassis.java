package frc.lib.chassis.drivebase;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.lib.autonomous.commands.AutonomousDriveCommand;
import frc.lib.chassis.drivemethod.DriveMethod;

public interface DriveChassis extends Subsystem {

    void setDriveMethod(DriveMethod method);

    AutonomousDriveCommand injectAutonomousCommand();

    void processChassisSpeeds(ChassisSpeeds chassisSpeeds);

    Pose2d getCurrentPose();

}
