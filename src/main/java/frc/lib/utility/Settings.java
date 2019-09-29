package frc.lib.utility;

import edu.wpi.first.wpilibj.Filesystem;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Settings {
    private static Settings instance;

    public float maxVelocity;
    public float ticksPerRevolutionWB;
    public float wheelDiameter;

    private Settings(){
        maxVelocity = 1;
        ticksPerRevolutionWB = -1;
        wheelDiameter = -1;
        instance = this;
    }

    public static Settings getInstance(){
        if(instance == null) instance = new Settings();
        return instance;
    }

    public void load(){
        Constructor constructor = new Constructor(Settings.class);
        TypeDescription description = new TypeDescription(Settings.class);
        constructor.addTypeDescription(description);
        Yaml yaml = new Yaml(constructor);
        instance = (Settings) yaml.load(Filesystem.getDeployDirectory().getAbsolutePath()+"/robot.settings");
    }
}

