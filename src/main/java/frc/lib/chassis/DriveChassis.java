package frc.lib.chassis;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.lib.chassis.drivemethod.DriveMethod;

public interface DriveChassis extends Subsystem {

    void setDriveMethod(DriveMethod method);

}
