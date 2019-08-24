package frc.lib.reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;
import frc.lib.reader.MotorData.Type;
class MotorParser {
    enum Mode{ON, ENCODER}

    BufferedReader reader;
    List<String[]> lines;
    void init() {
        try {
            reader = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().getAbsolutePath()+"/robot.motors"));
        } catch(Exception e) {
            //todo the problem catcher thing
        }
        lines = new ArrayList<String[]>();
        String line;
        try {
            while((line = reader.readLine()) != null) {
                if(line.charAt(0) != '/' && line.charAt(1) != '/')
                    lines.add(line.split(" "));//todo edge case, the comment exactly like this one, at the end of line. just detect a // and remove everything after
            }
        } catch(Exception e) {
            //idk how to throw problem properly and print it correctly
        }
    }
    void parse() {//need to add support for different encoders, right now its just whatever WPILib likes by default
        Mode mode;
        MotorData data;
        for (String[] line : lines) {
            mode = Mode.ON;
            data = new MotorData();
            for(int i = 1; i < line.length; i++) {//line[0] determines motor type, skip it
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
                        } else {
                            data.encPos.add(Integer.parseInt(line[i]));
                        }
                }
            }
            switch (line[0]) {
                case "talon":
                    data.type = Type.TALON;
                case "victor":
                    data.type = Type.VICTOR;
                case "spark":
                    data.type = Type.SPARK;//Make it use motor sensor by default unless set to something else or PWM
                case "blinkin":
                    data.type = Type.BLINKIN;
                case "blinkin_ind":
                    data.type = Type.BLINKININD;
            }
            data.initialize();
        }
    }
}