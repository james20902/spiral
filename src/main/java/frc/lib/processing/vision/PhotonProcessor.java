package frc.lib.processing.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.common.SimplePipelineResult;
import org.photonvision.common.SimpleTrackedTarget;
import org.photonvision.lib.PhotonCamera;

import java.util.List;

public class PhotonProcessor implements Subsystem {

    private final PhotonCamera cameraProcessor;
    private SimplePipelineResult cachedResults;
    private List<SimpleTrackedTarget> targets;

    public PhotonProcessor(String ntName){
        cameraProcessor = new PhotonCamera(ntName);
    }

    public PhotonProcessor(NetworkTable networkTable){
        cameraProcessor = new PhotonCamera(networkTable);
    }

    public PhotonProcessor(LimelightProcessor limelight){
        this(LimelightToPhoton.getTranslatedLimelightTable(limelight));
    }

    public void setCameraPipeline(int index){
        cameraProcessor.setPipelineIndex(index);
    }

    public void setCameraDriverMode(boolean driverMode){
        cameraProcessor.setDriverMode(driverMode);
    }

    public void toggleCameraDriverMode(){
        setCameraDriverMode(!cameraProcessor.getDriverMode());
    }

    public List<SimpleTrackedTarget> getTargetList(){
        return targets;
    }

    public double getXDegrees(){
        return targets.get(0).getYaw();
    }

    public double getYDegrees(){
        return targets.get(0).getPitch();
    }

    public double getArea(){
        return targets.get(0).getArea();
    }

    public Pose2d getRobotPoseRelative(){
        return targets.get(0).getRobotRelativePose();
    }

    @Override
    public void periodic() {
        if(cameraProcessor.hasTargets()){
            cachedResults = cameraProcessor.getLastResult();
            targets = cachedResults.targets;
        }
    }
}
