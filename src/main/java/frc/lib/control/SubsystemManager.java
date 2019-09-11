package frc.lib.control;

import frc.lib.control.Subsystems.Subsystem;
import frc.team5115.frc2020.Robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SubsystemManager {
    private Map<String, Subsystem> systems;
    private ScheduledThreadPoolExecutor executor;

    private static SubsystemManager instance;

    public static SubsystemManager getInstance(){
        if(instance == null){
            instance = new SubsystemManager();
        }
        return instance;
    }

    private SubsystemManager(){
        systems = new HashMap<String, Subsystem>();
        executor = new ScheduledThreadPoolExecutor(10);
    }

    public void addSubsystem(Subsystem system){
        systems.put(system.getSystemName(), system);
    }

    public void addSubsystems(List<Subsystem> list){
        for(Subsystem system : list){
            addSubsystem(system);
        }
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
        try{
            Subsystem s = systems.get(name);
        } catch (NullPointerException e){
            System.out.println("No subsystem with the name " + name + "!");
        }
    }

    public void printSystems(){
        systems.forEach((name, system) -> System.out.println(name));
    }
}
