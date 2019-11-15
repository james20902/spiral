package frc.lib.fms;

import frc.lib.control.Task;

import java.util.ArrayList;

public class FMSTask extends Task {

    private static ArrayList<FMSUpdateable> dynamicList;

    public void init(){
        dynamicList = new ArrayList<>();
    }

    @Override
    public void run() {
        for(FMSUpdateable fms : dynamicList){
            fms.update();
        }
    }

    public static void registerUpdater(FMSUpdateable element){
        dynamicList.add(element);
    }
}
