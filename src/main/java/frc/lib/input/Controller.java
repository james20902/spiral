package frc.lib.input;

public class Controller {

    private byte buttonCount;
    private byte axesCount;
    private byte POVcount;

    private int buttonStates;
    private int buttonStatesCache = 0;

    private float[] axesOutput;
    private short[] POV;

    private int xAxis = 0;
    private int yAxis = 1;
    private int zAxis = 2;

    private byte port;

    public enum ButtonState{
        NEUTRAL(0), PRESSED(0), HELD(1), RELEASED(1);
        float speed;
        ButtonState(float speed){
            this.speed = speed;
        }
        public float getSpeed(){
            return speed;
        }
    }

    public Controller(){
        this.port = -1;
        initialize((byte)12, (byte)12, (byte)12);
    }

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

    private boolean rawCacheState(int ID){
        return (buttonStatesCache & 1 << (ID - 1)) != 0;
    }

    public ButtonState getButtonState(int ID){
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
