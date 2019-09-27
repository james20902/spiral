package frc.lib.reader;

import edu.wpi.first.wpilibj.Filesystem;
import frc.lib.output.error.ErrorHandler;

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
        SubsystemData data = new SubsystemData();
        String[] temp;
        for (String[] line : lines) {
            data.reset();
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
                        data.input = new int[temp.length];
                        data.controller = Integer.parseInt(temp[0]);
                        if(temp[temp.length-1].equals("true")){
                            data.reversed = true;
                        } else data.reversed = false;
                        for(int j = 1; j < temp.length-1; j++) {
                            data.input[j] = Integer.parseInt(temp[j]);
                        }
                        continue;
                    } else if(mode == Mode.MOTOR){
                        temp = line[i].split(",");
                        data.motors = new int[temp.length];
                        for(int j = 0; j < temp.length; j++) {
                            data.motors[j] = Integer.parseInt(temp[j]);
                        }
                        continue;
                    }
                }
            }
            data.loopTime = Integer.parseInt(line[2]);
            data.name = line[1];
            data.type = line[0];
        }
    }
}