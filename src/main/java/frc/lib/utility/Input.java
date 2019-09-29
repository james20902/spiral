package frc.lib.utility;

import edu.wpi.first.wpilibj.Filesystem;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Input {
    private static Input instance;

    public byte lAxis;//throttle for cheesy and arcate, left component for tank
    public byte rAxis;//Turning axis for arcade,
    public byte button1;//Cheesy quickturn
    public float[][] deadzones;

    private Input(){
        deadzones = new float[6][12];
        instance = this;
    }

    public static Input getInstance(){
        if(instance == null) instance = new Input();
        return instance;
    }

    public void load(){
        Constructor constructor = new Constructor(Input.class);
        TypeDescription description = new TypeDescription(Input.class);
        constructor.addTypeDescription(description);
        Yaml yaml = new Yaml(constructor);
        instance = (Input) yaml.load(Filesystem.getDeployDirectory().getAbsolutePath()+"/robot.input");
    }
}
