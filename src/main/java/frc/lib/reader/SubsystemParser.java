package frc.lib.reader;

import edu.wpi.first.wpilibj.Filesystem;
import frc.lib.control.Subsystems.Subsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubsystemParser {
    //todo types of subsystem: arm, spinner(avocado, and support for scissor style thing), pneumatic(toggle). Must use class based introspection in case another one is added by teams. Need to allow for motor groups
    //todo allow grammar in robot.motors and subsystems, and just .replace it with ""
    enum Mode{HID, MOTORS};
    BufferedReader reader;
    List<String[]> lines;
    List<Class<?>> types;

    public void init() {
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
            //the problem catcher thing
        }
        types = ClassFinder.find(Subsystem.class.getPackage().getName());
        types = Collections.unmodifiableList(types);
    }
    public void parse() {
        SubsystemData data;
        for (String[] line : lines) {
            data = new SubsystemData();//todo reset because memory allocation slow(er)
            for (int i = 2; i < line.length; i++) {//line[0] determines subsystem type, skip it

            }
            data.name = line[1];
            data.type = line[0];
        }
    }
}

class ClassFinder {

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

}