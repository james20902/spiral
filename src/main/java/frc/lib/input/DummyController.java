package frc.lib.input;

public class DummyController extends Controller{
    private static DummyController dc;

    public DummyController() {
        super((byte)0);
    }

    public static DummyController getInstance(){
        if(dc != null) return dc;
        dc = new DummyController();
        return dc;
    }
    public byte getPort(){
        return 0;
    }

    public byte getButtonCount(){
        return 0;
    }

    public byte getAxesCount(){
        return 0;
    }

    public byte getPOVcount(){ return 0; }

    public boolean rawButtonState(int ID){
        return false;
    }

    private boolean rawCacheState(int ID){
        return false;
    }

    public ButtonState getButtonState(int ID){
        return ButtonState.NEUTRAL;
    }

    public float getAxis(int ID){
        return 0;
    }

    public int getPOV(){
        return 0;
    }
}
