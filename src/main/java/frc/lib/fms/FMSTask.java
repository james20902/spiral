package frc.lib.fms;

import frc.lib.control.Task;
import frc.lib.control.TaskType;

import java.util.ArrayList;

public class FMSTask extends Task {

    public FMSTask() {
        super(20, TaskType.REPEATED);
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
