package frc.lib.speedcontroller.basic;

import edu.wpi.first.wpilibj.PWMSpeedController;

public class BasicPWMController extends PWMSpeedController implements BasicSpeedController {

    int port;

    public BasicPWMController(int port){
        super(port);
        this.port = port;
    }

    @Override
    public void setSpeed(double speed) {
        super.setSpeed(speed);
    }

    @Override
    public void setVoltage(double voltage){
        super.setVoltage(voltage);
    }

    @Override
    public int getPort() {
        return port;
    }
}
