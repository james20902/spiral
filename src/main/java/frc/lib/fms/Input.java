package frc.lib.fms;

public class Input implements FMSUpdateable {

    private static Input instance;

    public static Input getInstance(){
        if(instance == null){
            instance = new Input();
        }
        return instance;
    }

    @Override
    public void init() {
        FMSTask.registerUpdater(instance);
    }

    @Override
    public void update() {

    }
}
