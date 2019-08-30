package frc.lib.output;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import frc.lib.output.error.ErrorHandler;

import java.lang.reflect.Method;

public class Motor {
    Object motor;
    Encoder e;
    Method set;
    int ticksPerRev;
    public Motor(Object motor, int ticksPerRev){
        this.motor = motor;
        Motors.motors.add(this);
        try {
            set = motor.getClass().getMethod("set");
        } catch(Exception e){
            ErrorHandler.report(e, "Please create an issue in the repository.", "Motors");
        }
        this.ticksPerRev = ticksPerRev;
    }

    public Motor(Object motor, int encoderPosA, int encoderPosB, int ticksPerRev){
        this(motor, ticksPerRev);
        e = new Encoder(encoderPosA, encoderPosB);
    }

    public void setPercent(double value){
        try {
            if (motor instanceof TalonSRX || motor instanceof VictorSPX) {
                set.invoke(motor, ControlMode.PercentOutput, value);
            } else {
                set.invoke(motor, value);
            }
        } catch(Exception e) {
            ErrorHandler.report(e, "There was a problem setting motor speed. This is most likely a spiral problem. Check your robot.motors, then create an issue on the repository including the error report and your robot.motors file.", "Motors");
        }
    }

    public double getRotations() {
        if(e != null){//todo casting bad
            return e.get()/ticksPerRev;
        } else if(motor instanceof TalonSRX) {
            return ((TalonSRX)motor).getSelectedSensorPosition(0)/ticksPerRev;
        } else if(motor instanceof CANSparkMax){
            return ((CANSparkMax)motor).getEncoder().getPosition()/ticksPerRev;
        }
        ErrorHandler.report("There was an error getting the encoder position for a motor/motors, please check cable connections and if using integrated encoder make sure it is either a Spark Max or TalonSRX. If you want more motor controllers with integrated encoders supported, create a feature request.", "Motors");
        return -1;
    }
}
