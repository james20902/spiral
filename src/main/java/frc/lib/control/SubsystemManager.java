package frc.lib.control;

import frc.lib.control.Subsystems.Subsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SubsystemManager {

    //todo, look into scheduledthreadpoolexecutor

    //https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html
    private Map<String, Subsystem> systems;
    private ScheduledThreadPoolExecutor scheduler;

    private static SubsystemManager instance;

    public static SubsystemManager getInstance(){
        if(instance == null){
            instance = new SubsystemManager();
        }
        return instance;
    }

    public SubsystemManager(){
        systems = new HashMap<>();
    }

    public void addSubsystem(Subsystem system){
        systems.put(system.getSystemName(), system);
    }

    public void addSubsystems(List<Subsystem> list){
        for(Subsystem system : list){
            addSubsystem(system);
        }
    }

    public void startScheduler(){
        int size = systems.size();
        if(size == 0){
            throw new NullPointerException("No systems present!");
        }
        scheduler = new ScheduledThreadPoolExecutor(size);
    }

    public void startSystems(){

    }

    public void stopSystem(String name){
        try{
            systems.get(name).interrupt();
        } catch (NullPointerException e){
            System.out.println("No subsystem with the name " + name + "!");
        }
    }

    public void printSystems(){
        systems.forEach((name, system) -> System.out.println(name));
    }

}
