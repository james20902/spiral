package frc.lib.speedcontroller.basic;

import com.revrobotics.CANSparkMax;

public class BasicSparkMaxController extends CANSparkMax implements BasicSpeedController {

    public BasicSparkMaxController(int port, MotorType type) {
        super(port, type);
    }

    @Override
    public void setSpeed(double speed) {
        setSpeed(speed);
    }

    @Override
    public int getPort() {
        return getDeviceId();
    }

}
