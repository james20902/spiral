package frc.lib.control;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SubsystemManager {

    //todo, look into scheduledthreadpoolexecutor
    //https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html

    //todo, remove system state handling here

    private Map<String, Subsystem> systems;

    public AtomicInteger masterState = new AtomicInteger();

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

    public void startSystems(){
        systems.forEach((name, system) -> system.start());
    }

    public void stopSystem(String name){
        try{
            systems.get(name).interrupt();
        } catch (NullPointerException e){
            System.out.println("No subsystem with that specific name!");
        }
    }

    public void printSystems(){
        systems.forEach((name, system) -> System.out.println(name));
    }

    public void pollMasterState(){
        masterState.set(HAL.nativeGetControlWord());
        System.out.println((masterState.get() & 1));
    }


}
