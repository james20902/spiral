package frc.lib.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubsystemManager {

    Map<String, Subsystem> systems;

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
        systems.put(system.getName(), system);
    }

    public void addSubsystems(List<Subsystem> list){
        for(Subsystem system : list){
            addSubsystem(system);
        }
    }



}
