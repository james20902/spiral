package frc.lib.reader;

import frc.lib.motors.Blinkin;

public class MotorData {
    public enum Type{TALON, VICTOR, SPARK, BLINKIN, BLINKININD, EMPTY};
    boolean integratedEncoder;
    int pdpPos = -1;
    int canPos = -1;
    int pwmPos = -1;
    int encPos = -1;
    Type type = Type.EMPTY;
    /*
    * if you use pwm talons and sparks you dumb so don't because they aren't gonna be supported
    * this method initializes and adds all the motors to the master list
     */
    public void initialize() {
        switch(type){
            case TALON:
            case VICTOR:
            case SPARK:
                if(canPos != -1) {

                } else {
                    if(encPos != -1) {

                    }
                }
            case BLINKIN:
                Blinkin.initSpark(pwmPos, false);
            case BLINKININD:
                Blinkin.initSpark(pwmPos, true);
        }
    }
}