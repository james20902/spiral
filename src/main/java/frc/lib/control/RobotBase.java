package frc.lib.control;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.lib.input.ControllerManager;
import frc.lib.output.error.ErrorHandler;
import frc.lib.reader.MotorParser;
import frc.lib.reader.SubsystemParser;
import frc.lib.utility.*;

/**
 * Implement a Robot Program framework. The RobotBase class is intended to be subclassed by a user
 * creating a robot program. Overridden autonomous() and operatorControl() methods are called at the
 * appropriate time as the match proceeds. In the current implementation, the Autonomous code will
 * run to completion before the OperatorControl code could start. In the future the Autonomous code
 * might be spawned as a task, then killed at the end of the Autonomous period.
 */
public abstract class RobotBase implements AutoCloseable {
    /**
     * The ID of the main Java thread.
     */
    private final TaskManager manager;
    /**
     * Constructor for a generic robot program. User code should be placed in the constructor that
     * runs before the Autonomous or Operator Control period starts. The constructor will run to
     * completion before Autonomous is entered.
     *
     * <p>This must be used to ensure that the communications code starts. In the future it would be
     * nice
     * to put this code into it's own task that loads on boot so ensure that it runs.
     */
    protected RobotBase() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        inst.setNetworkIdentity("Robot");
        inst.startServer("/home/lvuser/networktables.ini");
        manager = TaskManager.getInstance();
//        Settings.getInstance().load();
        inst.getTable("LiveWindow").getSubTable(".status").getEntry("LW Enabled").setBoolean(false);

        LiveWindow.setEnabled(false);
        Shuffleboard.disableActuatorWidgets();
//        MotorParser.getInstance().parse();
//        SubsystemParser.getInstance().parse();

    }

    @Override
    public void close() {
    }

    public abstract void start();

    public abstract void loop();

    public void startCompetition(){
        double checkpoint = 0;
        Console.reportWarning("Waiting for DS connection");
        HAL.waitForDSData();
        Console.reportWarning("DriverStation connected, initializing");

        manager.executeTask(MatchInfo.getInstance());
        manager.schedulePeriodicTask(ErrorHandler.getInstance(), 100);
        manager.schedulePeriodicTask(ControllerManager.getInstance());
        manager.schedulePeriodicTask(SystemState.getInstance());
        manager.schedulePeriodicTask(Console.getInstance());

        start();
        HAL.observeUserProgramStarting();

        while(!SystemState.getInstance().emergencyStopped() || !Thread.currentThread().isInterrupted()){
            if(SystemClock.getSystemTime() > checkpoint + 20){
                loop();
                checkpoint = SystemClock.getSystemTime();
            }
        }
    }


    /**
     * Starting point for the applications.
     * Unmodified WPILib method, except for a few things for speed
     */
    public static <T extends RobotBase> void startRobot(Supplier<T> robotSupplier) {
        if (!HAL.initialize(500, 0)) {
            throw new IllegalStateException("Failed to initialize HAL. Terminating");
        }

        HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Java);

        System.out.println("********** Spiral starting **********");

        T robot;
        try {
            robot = robotSupplier.get();
        } catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                throwable = cause;
            }
            String robotName = "Unknown";
            StackTraceElement[] elements = throwable.getStackTrace();
            if (elements.length > 0) {
                robotName = elements[0].getClassName();
            }
            Console.reportError("Unhandled exception instantiating robot " + robotName + " "
                    + throwable.toString(), 1, elements);
            Console.reportWarning("Robots should not quit, but yours did!");
            Console.reportError("Could not instantiate robot " + robotName + "!", 1);
            System.exit(1);
            return;
        }

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
            robot.startCompetition();
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
}
