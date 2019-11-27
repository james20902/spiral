package frc.lib.input;

public class ControllerPacket {

    int portNumber;

    float[] axes;
    int buttonStates;

    public ControllerPacket(int portNumber, float[] axes, short[] pov, int buttonStates){
        this.portNumber = portNumber;
        this.axes = axes;
        this.buttonStates = buttonStates;
    }


}
