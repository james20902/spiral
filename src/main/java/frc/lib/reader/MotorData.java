package frc.lib.reader;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Spark;
import frc.lib.output.Blinkin;
import frc.lib.output.motors.Motor;

import java.util.ArrayList;
import java.util.List;

public class MotorData {
    public enum Type{TALON, VICTOR, SPARK, BLINKIN, BLINKININD, EMPTY};
    boolean integratedEncoder = false;
    boolean encReverse = false;
    int ticksPerRev = -1;
    int pdpPos = -1;
    int canPos = -1;
    int pwmPos = -1;
    String drive;
    List<Integer> encPos = new ArrayList<Integer>();
    Type type = Type.EMPTY;

    public void reset() {
        integratedEncoder = false;
        ticksPerRev = -1;
        pdpPos = -1;
        canPos = -1;
        pwmPos = -1;
        drive = "";
        encReverse = false;
        encPos = new ArrayList<Integer>();
        type = Type.EMPTY;
    }

    /*
    * if you use pwm talons and sparks don't because they aren't gonna be supported
    * this method initializes and adds all the motors to the master list
     */
    public void initialize() {
        switch(drive){
            case "dl":
                switch(type){//could probably make it better but eh it'll work
                    case TALON:
                        TalonSRX tal = new TalonSRX(canPos);
                        Motors.left.add(new Motor(tal, ticksPerRev, encReverse));
                    case VICTOR:
                        VictorSPX vic = new VictorSPX(canPos);
                        Motors.left.add(new Motor(vic, ticksPerRev, encReverse));
                    case SPARK:
                        if(canPos != -1) {
                            CANSparkMax max = new CANSparkMax(canPos, CANSparkMaxLowLevel.MotorType.kBrushless);
                            Motors.left.add(new Motor(max, ticksPerRev, encReverse));
                        } else {
                            Spark sp = new Spark(pwmPos);
                            if(!encPos.isEmpty()) {
                                Motors.left.add(new Motor(sp, encPos.get(0), encPos.get(1), ticksPerRev, encReverse));
                                break;
                            }
                            Motors.left.add(new Motor(sp, ticksPerRev, encReverse));
                        }
                    case BLINKIN:
                        Blinkin.initSpark(pwmPos, false);
                    case BLINKININD:
                        Blinkin.initSpark(pwmPos, true);
                }
            case "dr":
                switch(type){//could probably make it better but eh it'll work
                    case TALON:
                        TalonSRX tal = new TalonSRX(canPos);
                        Motors.right.add(new Motor(tal, ticksPerRev, encReverse));
                    case VICTOR:
                        VictorSPX vic = new VictorSPX(canPos);
                        Motors.right.add(new Motor(vic, ticksPerRev, encReverse));
                    case SPARK:
                        if(canPos != -1) {
                            CANSparkMax max = new CANSparkMax(canPos, CANSparkMaxLowLevel.MotorType.kBrushless);
                            Motors.right.add(new Motor(max, ticksPerRev, encReverse));
                        } else {
                            Spark sp = new Spark(pwmPos);
                            if(!encPos.isEmpty()) {
                                Motors.right.add(new Motor(sp, encPos.get(0), encPos.get(1), ticksPerRev, encReverse));
                                break;
                            }
                            Motors.right.add(new Motor(sp, ticksPerRev, encReverse));
                        }
                    case BLINKIN:
                        Blinkin.initSpark(pwmPos, false);
                    case BLINKININD:
                        Blinkin.initSpark(pwmPos, true);
                }
            default:
                switch(type){//could probably make it better but eh it'll work
                    case TALON:
                        TalonSRX tal = new TalonSRX(canPos);
                        Motors.motors.add(new Motor(tal, ticksPerRev, encReverse));
                    case VICTOR:
                        VictorSPX vic = new VictorSPX(canPos);
                        Motors.motors.add(new Motor(vic, ticksPerRev, encReverse));
                    case SPARK:
                        if(canPos != -1) {
                            CANSparkMax max = new CANSparkMax(canPos, CANSparkMaxLowLevel.MotorType.kBrushless);
                            Motors.motors.add(new Motor(max, ticksPerRev, encReverse));
                        } else {
                            Spark sp = new Spark(pwmPos);
                            if(!encPos.isEmpty()) {
                                Motors.motors.add(new Motor(sp, encPos.get(0), encPos.get(1), ticksPerRev, encReverse));
                                break;
                            }
                            Motors.motors.add(new Motor(sp, ticksPerRev, encReverse));
                        }
                    case BLINKIN:
                        Blinkin.initSpark(pwmPos, false);
                    case BLINKININD:
                        Blinkin.initSpark(pwmPos, true);
                }
        }

    }
}