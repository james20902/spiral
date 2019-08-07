package frc.lib.utility;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.MatchInfoData;

public class MatchInfo {

    public enum Alliance{ RED, BLUE }

    public enum Position{ RIGHT, CENTER, LEFT }

    private MatchInfoData currentMatch;

    private static MatchInfo instance;

    public static MatchInfo seekMatchInfo(){
        if(instance == null){
            instance = new MatchInfo();
        }
        return instance;
    }

    public Position getDSPosition(){
        switch(HAL.getAllianceStation()){
            case Red1:
            case Blue3:
                return Position.RIGHT;
            case Red2:
            case Blue2:
                return Position.CENTER;
            case Red3:
            case Blue1:
                return Position.LEFT;
            default:
                return null;
        }
    }

    public Alliance getAlliance(){
        switch(HAL.getAllianceStation()){
            case Red1:
            case Red2:
            case Red3:
                return Alliance.RED;
            case Blue1:
            case Blue2:
            case Blue3:
                return Alliance.BLUE;
            default:
                return null;
        }
    }


}
