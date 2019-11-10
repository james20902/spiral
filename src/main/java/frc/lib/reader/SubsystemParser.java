package frc.lib.reader;

import edu.wpi.first.wpilibj.Filesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SubsystemParser {
    enum Mode{INPUT, MOTOR, NONE};
    BufferedReader reader;
    List<String[]> lines;
    List<Class<?>> types;
    Mode mode = Mode.NONE;
    static SubsystemParser instance;

    public static SubsystemParser getInstance(){
        if(instance == null) instance = new SubsystemParser();
        return instance;
    }

    private SubsystemParser() {
        try {
            reader = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().getAbsolutePath()+"/robot.subsystems"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        lines = new ArrayList<String[]>();
        String line;
        try {
            while((line = reader.readLine()) != null) {
                if(line.charAt(0) != '/' && line.charAt(1) != '/')
                    lines.add(line.split("//")[0].split(" "));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void parse() {
        String[] temp;
        for (String[] line : lines) {
            for (int i = 3; i < line.length; i++) {//line[0-2] determines subsystem type, skip it
                if(line[i].equals("input")) {
                    mode = Mode.INPUT;
                    continue;
                } else if(line[i].equals("motor")) {
                    mode = Mode.MOTOR;
                    continue;
                } else {
                    if(mode == Mode.INPUT){
                        temp = line[i].split(",");
                        if(temp[temp.length-1].equals("true")){

                        }
                        for(int j = 1; j < temp.length-1; j++) {
                        }
                        continue;
                    } else if(mode == Mode.MOTOR){
                        temp = line[i].split(",");
                        for(int j = 0; j < temp.length; j++) {
                        }
                        continue;
                    }
                }
            }
        }
    }
}