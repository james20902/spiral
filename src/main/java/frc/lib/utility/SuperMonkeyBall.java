package frc.lib.utility;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.MatchInfoData;
import frc.lib.control.Subsystem;
import frc.lib.control.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SuperMonkeyBall extends Task {
    private static SuperMonkeyBall instance;

    public enum Alliance{ RED, BLUE }

    public enum Position{ RIGHT, CENTER, LEFT }

    public enum MatchType{ NONE, PRACTICE, QUALIFICATION, ELIMINATION }

    private MatchInfoData currentMatch;

    public static SuperMonkeyBall getInstance(){
        if(instance == null) {
            instance = new SuperMonkeyBall();
        }
        return instance;
    }

    public static final int DISABLED = 0;
    public static final int AUTONOMOUS = 1;
    public static final int OPERATOR = 2;
    public static final int TEST = 3;

    private ControlWord globalState = new ControlWord();
    private AtomicInteger translatedState = new AtomicInteger();

    private ScheduledThreadPoolExecutor systemExecutor;
    private Map<String, Subsystem> systems;

    @Override
    public void init(){
        globalState = new ControlWord();
        translatedState = new AtomicInteger();
        currentMatch = new MatchInfoData();
        systemExecutor = new ScheduledThreadPoolExecutor(4);
        systems = new HashMap<>();
    }

    public void standardExecution(){
        if(!HAL.isNewControlData()){
            return;
        }
        HAL.getControlWord(globalState);
        translateState();
        fetchMatchInfo();
    }

    public void addSubsystem(Subsystem system){
        systems.put(system.getName(), system);
    }

    public void addSubsystems(List<Subsystem> list){
        for(Subsystem system : list){
            addSubsystem(system);
        }
    }

    public void startSubsystem(String name){
        startSubsystem(systems.get(name));
    }

    public void startSubsystem(Subsystem system){
        systemExecutor.scheduleAtFixedRate(system, 0, system.getTiming(), TimeUnit.MILLISECONDS);
    }

    public void startSubsystems(){
        systems.forEach((name, subsystem)->startSubsystem(subsystem));
    }

    public Subsystem getSystem(String name){
        try{
            Subsystem s = systems.get(name);
            return s;
        } catch (NullPointerException e){
            System.out.println("No subsystem with the name " + name + "!");
        }
        return null;
    }

    public void stopSystem(String name){
        //getSystem(name).stop();
    }

    public void shutdown(){
        /*Iterator it = systems.entrySet().iterator();
        while(it.hasNext()){
            Subsystem system = (Subsystem)it.next();
            //put shutdown in there
            it.remove();
        }*/
        systemExecutor.shutdownNow();
        //todo for each system in lists call shutdown
    }

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

    public void fetchMatchInfo(){
        HAL.getMatchInfo(currentMatch);
    }

    public int getState(){ return translatedState.get(); }

    public void translateState(){
        if(!isEnabled()){
            translatedState.set(DISABLED);
            HAL.observeUserProgramDisabled();
        } else if(isAutonomous()){
            translatedState.set(AUTONOMOUS);
            HAL.observeUserProgramAutonomous();
        } else if(isTest()){
            translatedState.set(TEST);
            HAL.observeUserProgramTest();
        } else {
            translatedState.set(OPERATOR);
            HAL.observeUserProgramTeleop();
        }
    }

    public boolean isEnabled(){
        return globalState.getEnabled();
    }

    public boolean isAutonomous(){
        return globalState.getAutonomous();
    }

    public boolean isTest(){
        return globalState.getTest();
    }

    public boolean emergencyStopped(){
        return globalState.getEStop();
    }

    public boolean FMSPresent(){
        return globalState.getFMSAttached();
    }

    public boolean DSPresent(){
        return globalState.getDSAttached();
    }
}
