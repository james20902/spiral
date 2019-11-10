package frc.lib.control;

public abstract class Task implements Runnable {

    private int timing;
    private String name;

    public Task(){
        this(20);
    }

    public Task(int timing){
        this(timing, null);
    }

    public Task(String name){
        this(20, name);
    }

    public Task(int timing, String name){
        this.timing = timing;
        if(name == null){
            this.name = this.getClass().getSimpleName();
        } else {
            this.name = name;
        }
        init();
    }

    public int getTiming(){
        return timing;
    }

    public String getName(){
        return name;
    }

    public void init(){}

    public abstract void standardExecution();

    public void competitionExecution(){
        standardExecution();
    }

    public void run(){

    }


}
