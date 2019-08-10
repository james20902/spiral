package frc.lib.reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import frc.lib.reader.MotorData.Type;
class SubsystemParser {
    enum Mode{ON, ENCODER}

    BufferedReader reader;
    List<String[]> lines;
    void init() {
        try {
            reader = new BufferedReader(new FileReader("robot.motors"));
        } catch(Exception e) {
            //idk how to throw problem properly and print it correctly
        }
        lines = new ArrayList<String[]>();
        String line;
        try {
            while((line = reader.readLine()) != null) {
                if(line.charAt(0) != '/' && line.charAt(1) != '/')
                    lines.add(line.split(" "));// edge case, the comment exactly like this one, end of line. just detect a // and remove everything after
            }
        } catch(Exception e) {
            //idk how to throw problem properly and print it correctly
        }
    }
    void parse() {
        for (String[] line : lines) {
            Mode mode = Mode.ON;
            MotorData data = new MotorData();
            for(int i = 1; i < line.length; i++) {//line[0] determines motor type
                if(line[i].equals("on")) continue;//for cases of encoder or something, set different modes
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
                        if(line[i].equals("motor"))
                            data.integratedEncoder = true;
                        else {
                            data.encPos = Integer.parseInt(line[i + 1]);
                        }
                    default:
                        //mental break;
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