package frc.lib.chassis.drivemethod;

import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import frc.lib.operator.OperatorInterface;

public interface DriveMethod {

    void setOI(OperatorInterface OI);

    PerpetualCommand getDefaultCommand();

}
