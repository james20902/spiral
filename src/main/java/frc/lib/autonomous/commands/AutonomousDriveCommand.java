package frc.lib.autonomous.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.lib.chassis.drivebase.DriveChassis;

import java.util.HashSet;
import java.util.Set;

public abstract class AutonomousDriveCommand implements Command {

    Set<Subsystem> requirements = new HashSet<>();
    protected DriveChassis driveChassis;

    public void setDriveChassis(DriveChassis chassis){
        this.driveChassis = chassis;
        requirements.add(driveChassis);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return requirements;
    }

}
