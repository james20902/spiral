package frc.lib.utility;

import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.lib.control.ShutdownHook;
import frc.lib.control.TaskManager;
import frc.lib.fms.FMSTask;
import frc.lib.reader.MotorParser;
import frc.team5115.frc2020.Robot;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StartRobot {
    private final static TaskManager manager = TaskManager.getInstance();

    public static void start(){
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        if (!HAL.initialize(500, 0)) {
            throw new IllegalStateException("Failed to initialize HAL. Terminating");
        }

        HAL.report(FRCNetComm.tResourceType.kResourceType_Language, FRCNetComm.tInstances.kLanguage_Java);

        System.out.println("********** Spiral starting **********");

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

            }
        }
        manager.addTask(new FMSTask());
//        HAL.waitForDSData();
        HAL.observeUserProgramStarting();
    }
}
