package frc.lib.fms;

import frc.lib.control.Task;

import java.util.ArrayList;

public class FMSTask extends Task {

    public FMSTask(int delay) {
        super(delay);
    }

    private static ArrayList<FMSUpdateable> taskList;

    public void init(){
        taskList = new ArrayList<>();
        InputManager.getInstance();
        SystemState.getInstance();
        MatchInfo.getInstance();
    }

    @Override
    public void update() {
        for(FMSUpdateable task : taskList){
            task.update();
        }
    }

    @Override
    public void interrupted(){
        taskList.clear();
    }

    public static void registerUpdater(FMSUpdateable element){
        taskList.add(element);
        element.init();
    }
}
