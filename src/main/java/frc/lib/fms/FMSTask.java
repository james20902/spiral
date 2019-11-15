package frc.lib.fms;

import frc.lib.control.Task;

import java.util.ArrayList;

public class FMSTask extends Task {

    public FMSTask(int delay) {
        super(delay);
    }

    private static ArrayList<FMSUpdateable> dynamicList;

    public void init(){
        dynamicList = new ArrayList<>();
        ControllerInput.getInstance();
        SystemState.getInstance();
        MatchInfo.getInstance();
    }

    @Override
    public void update() {
        for(FMSUpdateable fms : dynamicList){
            fms.update();
        }
    }

    @Override
    public void interrupted(){
        dynamicList.clear();
    }

    public static void registerUpdater(FMSUpdateable element){
        dynamicList.add(element);
        element.init();
    }
}
