package frc.lib.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SubsystemManager {

    private static SubsystemManager instance;

    public static SubsystemManager getInstance(){
        if(instance == null){
            instance = new SubsystemManager();
        }
        return instance;
    }

    private ScheduledThreadPoolExecutor systemExecutor;
    private Map<String, Subsystem> systems;

    public SubsystemManager(){
        systemExecutor = new ScheduledThreadPoolExecutor(8);
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
        systemExecutor.shutdownNow();
        //todo for each system in lists call shutdown
    }


}
