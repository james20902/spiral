package frc.lib.input;

public class ControllerPacket {

    int portNumber;

    float[] axes;
    short[] pov;
    int buttonStates;

    public ControllerPacket(int portNumber, float[] axes, short[] pov, int buttonStates){
        this.portNumber = portNumber;
        this.axes = axes;
        this.pov = pov;
        this.buttonStates = buttonStates;
    }


}
