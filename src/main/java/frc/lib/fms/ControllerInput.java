package frc.lib.fms;

public class ControllerInput implements FMSUpdateable {

    private static ControllerInput instance;

    public static ControllerInput getInstance(){
        if(instance == null){
            instance = new ControllerInput();
            FMSTask.registerUpdater(instance);
        }
        return instance;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }
}
