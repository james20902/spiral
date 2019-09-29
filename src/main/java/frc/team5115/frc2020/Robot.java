package frc.team5115.frc2020;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.control.Subsystem;
import frc.lib.input.Controller;
import frc.lib.input.ControllerManager;
import frc.lib.output.Motors;
import frc.lib.output.drivebase.ArcadeDrive;
import frc.lib.output.drivebase.DriveSystem;
import frc.lib.utility.Console;

public class Robot extends Subsystem {

    Controller stick;
    DriveSystem drive;

    TalonSRX right1;
    TalonSRX left1;
    TalonSRX right2;
    TalonSRX left2;

    @Override
    public void init(){
        drive = new ArcadeDrive(ControllerManager.getJoystick(0));
//        stick = ControllerManager.getJoystick();
//these should not be needed, you should be able to use the config
        /*right1 = new TalonSRX(2);
        left1 = new TalonSRX(1);
        right2 = new TalonSRX(4);
        left2 = new TalonSRX(3);

        right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        right2.set(ControlMode.Follower, 2);
        left2.set(ControlMode.Follower, 1);
        right1.setInverted(true);
        right2.setInverted(true);
        left1.setInverted(false);
        left2.setInverted(false);
        right1.setSelectedSensorPosition(0);
        left1.setSelectedSensorPosition(0);*/

//        ErrorHandler.getInstance().crash("Test");
    }

    @Override
    public void teleopPeriodic() {
//        Console.print(stick.getAxis(0));
        drive.teleopPeriodic();
        Motors.setDrive(drive.getSignal());
    }
}
