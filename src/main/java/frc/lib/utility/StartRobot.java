package frc.lib.utility;

import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.lib.control.ShutdownHook;
import frc.lib.control.Task;
import frc.lib.control.TaskManager;
import frc.lib.input.ControllerManager;
import frc.lib.output.error.ErrorHandler;
import frc.team5115.frc2020.TestSystem;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StartRobot {
    private final static TaskManager manager = TaskManager.getInstance();

    public static void start(){
        init();
        if (!HAL.initialize(500, 0)) {
            throw new IllegalStateException("Failed to initialize HAL. Terminating");
        }

        HAL.report(FRCNetComm.tResourceType.kResourceType_Language, FRCNetComm.tInstances.kLanguage_Java);

        System.out.println("********** Spiral starting **********");
        Console.reportWarning("Gooooooooood morning... GAMERS!");

        if (HALUtil.getHALRuntimeType() == 0) {
            try {
                final File file = new File("/tmp/frc_versions/FRC_Lib_Version.ini");

                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();

                try (OutputStream output = Files.newOutputStream(file.toPath())) {
                    output.write("Java ".getBytes(StandardCharsets.UTF_8));
                    output.write(WPILibVersion.Version.getBytes(StandardCharsets.UTF_8));
                }

            } catch (IOException ex) {
                Console.reportError("Could not write FRC_Lib_Version.ini: " + ex.toString(), 1,
                        ex.getStackTrace());
            }
        }

        boolean errorOnExit = false;
        try {
            startCompetition();
        } catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                throwable = cause;
            }
            Console.reportError("Unhandled exception: " + throwable.toString(),
                    1, throwable.getStackTrace());
            errorOnExit = true;
        } finally {
            // startCompetition never returns unless exception occurs....
            Console.reportWarning("Robots should not quit, but yours did!");
            if (errorOnExit) {
                Console.reportError(
                        "The startCompetition() method (or methods called by it) should have "
                                + "handled the exception above.", 1);
            } else {
                Console.reportError("Unexpected return from startCompetition() method.", 1);
            }
        }
        System.exit(1);
    }

    private static void init(){
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        inst.setNetworkIdentity("Robot");
        inst.startServer("/home/lvuser/networktables.ini");
//        Settings.getInstance().load();

        LiveWindow.setEnabled(false);
        Shuffleboard.disableActuatorWidgets();
//        MotorParser.getInstance().parse();
//        SubsystemParser.getInstance().parse();
        NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
        limelight.getEntry("ledMode").setNumber(1);
        limelight.getEntry("camMode").setNumber(1);
        TaskManager.getInstance().schedulePeriodicTask(new TestSystem(), 5);
    }

    private static void startCompetition(){
        double checkpoint = 0;
        Console.reportWarning("Waiting for DriverStation connection");
        HAL.waitForDSData();
        Console.reportWarning("DriverStation connected");

        manager.schedulePeriodicTask(ErrorHandler.getInstance());
        manager.schedulePeriodicTask(ControllerManager.getInstance());
        manager.schedulePeriodicTask(SystemState.getInstance());
        manager.schedulePeriodicTask(Console.getInstance());

        HAL.observeUserProgramStarting();

        while(!SystemState.getInstance().emergencyStopped() || !Thread.currentThread().isInterrupted()){
            if(SystemClock.getSystemTime() > checkpoint + 20){
                checkpoint = SystemClock.getSystemTime();
            }
        }
    }
}
