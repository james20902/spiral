package frc.lib.input;

public class Controller {

    byte buttonCount;
    byte axesCount;
    byte POVcount;

    private int buttonStates;
    private int buttonStatesCache = 0;
    private float[] axesOutput;
    private float[] deadbands;
    private short[] POV;

    private byte port;

    public enum ButtonState{NEUTRAL, PRESSED, HELD, RELEASED}

    public Controller(byte port){
        this.port = port;
    }

    public byte getPort(){
        return port;
    }

    public void initialize(byte buttonCount, byte axesCount, byte POVcount){
        this.buttonCount = buttonCount;
        this.axesCount = axesCount;
        this.POVcount = POVcount;

        axesOutput = new float[axesCount];
        deadbands = new float[axesCount];
        POV = new short[POVcount];
    }

    public byte getButtonCount(){
        return buttonCount;
    }

    public byte getAxesCount(){
        return axesCount;
    }

    public byte getPOVcount(){ return POVcount; }

    public boolean rawButtonState(int ID){
        return (buttonStates & 1 << (ID - 1)) != 0;
    }

    public boolean rawCacheState(int ID){
        return (buttonStatesCache & 1 << (ID - 1)) != 0;
    }

    public ButtonState buttonState(int ID){
        boolean current = rawButtonState(ID);
        boolean cached = rawCacheState(ID);

        if(!current){
            if(!cached){
                return ButtonState.NEUTRAL;
            }
            return ButtonState.RELEASED;
        } else {
            if(cached){
                return ButtonState.HELD;
            }
            return ButtonState.PRESSED;
        }
    }

    public void setDeadband(int ID, float deadband){
        deadbands[ID] = deadband;
    }

    public float getAxis(int ID){
        return axesOutput[ID];
    }

    public int getPOV(){
        return POV[0];
    }

    public void updateData(int buttonStates, float[] axesOutput, short[] POV){
        this.buttonStatesCache = this.buttonStates;
        this.buttonStates = buttonStates;
        this.axesOutput = axesOutput;
        this.POV = POV;
    }
}
