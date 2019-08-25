package frc.lib.input;

public class Joystick {

    byte buttonCount;
    byte axesCount;
    byte POVcount;

    private int buttonStates;
    private float[] axesOutput = new float[12];
    private short[] POV = new short[12];

    private byte port;

    public Joystick(byte port){
        this.port = port;
    }

    public byte getPort(){
        return port;
    }

    public void initialize(byte buttonCount, byte axesCount, byte POVcount){
        this.buttonCount = buttonCount;
        this.axesCount = axesCount;
        this.POVcount = POVcount;
    }

    public byte getButtonCount(){
        return buttonCount;
    }

    public byte getAxesCount(){
        return axesCount;
    }

    public byte getPOVcount(){
        return POVcount;
    }

    public void updateData(int buttonStates, float[] axesOutput, short[] POV){
        this.buttonStates = buttonStates;
        this.axesOutput = axesOutput;
        this.POV = POV;
    }



}
