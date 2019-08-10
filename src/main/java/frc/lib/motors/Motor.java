package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

public class Motor {
    Object motor;
    Encoder e;

    Motor(Object motor){
        this.motor = motor;
    }

    Motor(Object motor, int encoderPosA, int encoderPosB){
        this.motor = motor;
        e = new Encoder(encoderPosA, encoderPosB);
    }

    public void setPercent(double value){
        if(motor instanceof TalonSRX) {
            ((TalonSRX)motor).set(ControlMode.PercentOutput, value);
        } else if(motor instanceof VictorSPX) {
            ((VictorSPX)motor).set(ControlMode.PercentOutput, value);
        } else if(motor instanceof Spark) {
            ((Spark)motor).set(value);
        } else if(motor instanceof CANSparkMax) {
            ((CANSparkMax)motor).set(value);
        }
    }

    public double getRotations() {
        if(e != null){
            return e.get();
        } else if(motor instanceof TalonSRX) {
            return ((TalonSRX)motor).getSelectedSensorPosition(0);
        } else if(motor instanceof CANSparkMax){
            return ((CANSparkMax)motor).getEncoder().getPosition();
        }
        return 0;
    }
}
