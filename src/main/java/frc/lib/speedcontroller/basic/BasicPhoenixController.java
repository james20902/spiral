package frc.lib.speedcontroller.basic;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class BasicPhoenixController extends BaseMotorController implements BasicSpeedController{

    public BasicPhoenixController(int port) {
        super(port, "generic");
    }

    @Override
    public void setSpeed(double speed) {
        set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void setVoltage(double voltage){
        setSpeed(voltage / getBusVoltage());
    }

    @Override
    public int getPort() {
        return getDeviceID();
    }
}
