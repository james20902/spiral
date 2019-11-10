package frc.lib.reader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;

public class MotorParser {
    enum Mode{ON, ENCODER}

    static MotorParser instance;
    BufferedReader reader;
    List<String[]> lines;

    public static MotorParser getInstance() {
        if(instance == null) instance = new MotorParser();
        return instance;
    }

    private MotorParser() {
        try {
            reader = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().getAbsolutePath()+"/robot.motors"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lines = new ArrayList<String[]>();
        String line;
        while(true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
                if(line.charAt(0) != '/' && line.charAt(1) != '/')
                    lines.add(line.split("//")[0].split(" "));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void parse() {
        Mode mode;
        for (String[] line : lines) {
            mode = Mode.ON;
            for(int i = 1; i < line.length; i++) {//line[0] determines motor type, skip it
                if(line[i].equals("reversed")) {
                    continue;
                }
                if(i == 1 && line[i].charAt(0) == 'd') {
                    continue;
                }
                if(line[i].equals("on")) {
                    mode = Mode.ON;
                    continue;
                }
                if(line[i].equals("encoder")) {
                    mode = Mode.ENCODER;
                    continue;
                }
                switch (mode) {
                    case ON:
                        switch (line[i]) {
                            case "can":
                                i += 1;
                            case "pdp":
                                i += 1;
                            case "pwm":
                                i += 1;
                            default:
                                break;
                        }
                    case ENCODER:
                        if(line[i].equals("motor")) {
                            i += 1;
                        } else {
                            i += 2;
                        }
                }
            }
            switch (line[0]) {
                case "talon":

                case "victor":

                case "spark":

                case "blinkin":

                case "blinkin_ind":

            }

        }
    }
}