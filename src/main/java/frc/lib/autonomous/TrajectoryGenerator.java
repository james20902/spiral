package frc.lib.autonomous;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.TrajectoryConstraint;

import java.util.List;

public class TrajectoryGenerator {

    private static TrajectoryGenerator instance;
    private TrajectoryConfig configuration;

    public static TrajectoryGenerator getInstance(){
        if(instance == null){
            instance = new TrajectoryGenerator();
        }
        return instance;
    }

    private TrajectoryGenerator(){
        configuration = null;
    }

    public Trajectory generateCubicTrajectory(Pose2d start, List<Translation2d> interiorHeadings, Pose2d end){
        if(configuration == null){
            throw new NullPointerException("Configuration not provided for trajectory generator!");
        }
        return edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
                .generateTrajectory(start, interiorHeadings, end, configuration);
    }

    public Trajectory generateQuinticTrajectory(List<Pose2d> poses){
        if(configuration == null){
            throw new NullPointerException("Configuration not provided for trajectory generator!");
        }
        return edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
                .generateTrajectory(poses, configuration);
    }

    public void setConfiguration(TrajectoryConfig newConfiguration){
        this.configuration = newConfiguration;
    }

    public TrajectoryConfig getConfiguration() {
        return configuration;
    }

    public String getConfigurationAsString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("Current selected trajectory generation configuration:").append("\n")
                .append("Starting Speed: ").append(configuration.getStartVelocity()).append("\n")
                .append("Ending Speed: ").append(configuration.getEndVelocity()).append("\n")
                .append("Max Speed: ").append(configuration.getMaxVelocity()).append("\n")
                .append("Max Acceleration: ").append(configuration.getMaxAcceleration()).append("\n")
                .append("Reveresed: ").append(configuration.isReversed()).append("\n")
                .append("\n Constraints:").append(getConstraintsAsString());

        return buffer.toString();
    }

    public String getConstraintsAsString(){
        StringBuffer buffer = new StringBuffer();
        for(TrajectoryConstraint c : configuration.getConstraints()){
            buffer.append(c.getClass().getSimpleName()).append("\n");
        }

        return buffer.toString();
    }
}
