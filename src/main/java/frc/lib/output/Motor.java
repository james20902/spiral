package frc.lib.output;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

import java.lang.reflect.Method;

public class Motor {
    Object motor;
    Encoder e;
    Method set;
    public Motor(Object motor){
        this.motor = motor;
        Motors.motors.add(this);
        try {
            set = motor.getClass().getMethod("set");
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("There was a spiral problem initializing the Motor class. Please create an issue in the repository.");
        }
    }

    public Motor(Object motor, int encoderPosA, int encoderPosB){
        this(motor);
        e = new Encoder(encoderPosA, encoderPosB);
    }

    public void setPercent(double value){
        try {
            if (motor instanceof TalonSRX || motor instanceof VictorSPX) {
                set.invoke(ControlMode.PercentOutput, value);
            } else {
                set.invoke(value);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("There was a problem setting motor speed. This is most likely a spiral problem. Create an issue on the repository and include the error report and your robot.motors file.");
        }
    }

    public double getRotations() {//todo ticks/rev is needed for these due to gear ratios.
        if(e != null){//todo casting bad
            return e.get();
        } else if(motor instanceof TalonSRX) {
            return ((TalonSRX)motor).getSelectedSensorPosition(0);
        } else if(motor instanceof CANSparkMax){
            return ((CANSparkMax)motor).getEncoder().getPosition();
        }
        System.err.println("There was an error getting the encoder position for a motor/motors, please check cable connections and if using integrated encoder make sure it is either a Spark Max or TalonSRX. If you want more motor controllers with integrated encoders supported, create a feature request.");
        return 0;
    }
}
