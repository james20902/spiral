package frc.lib.fms;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.MatchInfoData;

public class MatchInfo implements FMSUpdateable {

    public enum Alliance{ RED, BLUE }

    public enum Position{ RIGHT, CENTER, LEFT }

    public enum MatchType{ NONE, PRACTICE, QUALIFICATION, ELIMINATION }

    private MatchInfoData currentMatch;

    private static MatchInfo instance;

    public static MatchInfo getInstance(){
        if(instance == null){
            instance = new MatchInfo();
            FMSTask.registerUpdater(instance);
        }
        return instance;
    }

    @Override
    public void init() {
        currentMatch = new MatchInfoData();
    }

    @Override
    public void update(){ HAL.getMatchInfo(currentMatch);}

    public String getEventName(){
        return currentMatch.eventName;
    }

    public String getGameMessage(){
        return currentMatch.gameSpecificMessage;
    }

    public int getMatchNumber(){
        return currentMatch.matchNumber;
    }

    public int getReplayNumber(){
        return currentMatch.replayNumber;
    }

    public MatchType getMatchType(){
        switch (currentMatch.matchType) {
            case 1:
                return MatchType.PRACTICE;
            case 2:
                return MatchType.QUALIFICATION;
            case 3:
                return MatchType.ELIMINATION;
            default:
                return MatchType.NONE;
        }
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

