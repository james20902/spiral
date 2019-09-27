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
    Method set, getRotations;
    int ticksPerRev;
    boolean encReverse;
    public Motor(Object motor, int ticksPerRev, boolean encReversed){
        this.encReverse = encReversed;
        this.motor = motor;
        Motors.motors.add(this);
        try {
            set = motor.getClass().getMethod("set");
            if(motor instanceof TalonSRX) {
                getRotations = ((TalonSRX)motor).getClass().getMethod("getSelectedSensorPosition", int.class);
            } else if(motor instanceof CANSparkMax){
                getRotations = ((CANSparkMax)motor).getEncoder().getClass().getMethod("getPosition");
            }
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        this.ticksPerRev = ticksPerRev;
    }

    public Motor(Object motor, int encoderPosA, int encoderPosB, int ticksPerRev, boolean encReversed){
        this(motor, ticksPerRev, encReversed);
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
            e.printStackTrace();
        }
    }

    public double getRotations() {
        int ticks = -1;
        try {
            if (e != null) {
                ticks = e.get() / ticksPerRev;
            } else if (motor instanceof TalonSRX) {
                ticks = (int)getRotations.invoke(motor,0) / ticksPerRev;
            } else if (motor instanceof CANSparkMax) {
                ticks = (int)getRotations.invoke(motor) / ticksPerRev;
            }
        } catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        if(encReverse) return ticks*-1;
        return ticks;
    }
}
