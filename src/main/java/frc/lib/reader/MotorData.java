package frc.lib.reader;

class MotorData {
    enum Type{Talon, Victor, Spark, Blinkin, EMPTY};
    boolean integratedEncoder, hasEncoder;
    int pdpPos = -1;
    int canPos = -1;
    int pwmPos = -1;
    int encPos = -1;
    Type type = Type.NONE;

    public void initialize() {
        
    }
}