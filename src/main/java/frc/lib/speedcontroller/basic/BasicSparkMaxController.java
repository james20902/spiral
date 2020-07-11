package frc.lib.speedcontroller.basic;

import com.revrobotics.CANSparkMax;

public class BasicSparkMaxController extends CANSparkMax implements BasicSpeedController {

    public BasicSparkMaxController(int port, MotorType type) {
        super(port, type);
    }

    @Override
    public void setSpeed(double speed) { super.set(speed * 12); }

    @Override
    public void setVoltage(double voltage) { super.setVoltage(voltage); }

    @Override
    public int getPort() {
        return getDeviceId();
    }

}
