package frc.team5115.frc2020;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.lib.input.Controller;
import frc.lib.input.ControllerManager;
import frc.lib.utility.Console;

public class TestSystem extends TaskSubsystem {

    Controller stick = ControllerManager.getJoystick();

    TalonSRX right1;
    TalonSRX left1;
    TalonSRX right2;
    TalonSRX left2;

    @Override
    public void init(){
        right1 = new TalonSRX(2);
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
        left1.setSelectedSensorPosition(0);
    }

    @Override
    public void teleopPeriodic() {
        right1.set(ControlMode.PercentOutput, (-stick.getAxis(1) - stick.getAxis(4)) * 0.5);
        left1.set(ControlMode.PercentOutput, (-stick.getAxis(1) + stick.getAxis(4)) * 0.5);
//        Console.print(right1.getSelectedSensorPosition());
//        Console.print(left1.getSelectedSensorPosition());
    }
}
