package frc.lib.reader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;
import frc.lib.output.error.ErrorHandler;
import frc.lib.reader.MotorData.Type;
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
        MotorData data = new MotorData();
        for (String[] line : lines) {
            mode = Mode.ON;
            data.reset();
            for(int i = 1; i < line.length; i++) {//line[0] determines motor type, skip it
                if(line[i].equals("reversed")) {
                    data.encReverse = true;
                    continue;
                }
                if(i == 1 && line[i].charAt(0) == 'd') {
                    data.drive = line[i];
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
                                data.canPos = Integer.parseInt(line[i + 1]);
                                i += 1;
                            case "pdp":
                                data.pdpPos = Integer.parseInt(line[i + 1]);
                                i += 1;
                            case "pwm":
                                data.pwmPos = Integer.parseInt(line[i + 1]);
                                i += 1;
                            default:
                                break;
                        }
                    case ENCODER:
                        if(line[i].equals("motor")) {
                            data.integratedEncoder = true;
                            data.ticksPerRev = Integer.parseInt(line[i+1]);
                            i += 1;
                        } else {
                            data.encPos.add(Integer.parseInt(line[i]));
                            data.encPos.add(Integer.parseInt(line[i+1]));
                            data.ticksPerRev = Integer.parseInt(line[i+2]);
                            i += 2;
                        }
                }
            }
            switch (line[0]) {
                case "talon":
                    data.type = Type.TALON;
                case "victor":
                    data.type = Type.VICTOR;
                case "spark":
                    data.type = Type.SPARK;
                case "blinkin":
                    data.type = Type.BLINKIN;
                case "blinkin_ind":
                    data.type = Type.BLINKININD;
            }
            data.initialize();
        }
    }
}