package frc.lib.reader;

import frc.lib.control.Subsystem;
import frc.lib.output.error.ErrorHandler;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubsystemData {
    String name, type;
    int[] motors, input;
    int controller, loopTime;
    boolean reversed;
    private static String[] classNames;
    private static List<Class<?>> classes;
    public SubsystemData(){
        classes = ClassFinder.find(Subsystem.class.getPackage().getName());
        classNames = new String[classes.size()];
        String[] temp;
        for(int i = 0; i < classes.size(); i++) {
            temp = classes.get(i).getName().split(".");
            classNames[i] = temp[temp.length-1].toLowerCase();
        }
        motors = null;
        input = null;
        name = "";
        type = "";
        controller = 0;
        reversed = false;
        loopTime = 10;
    }

    public void intialize() {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(0).equals(type.toLowerCase())) {
                if (motors != null) {
                    if (input != null) {
                        try {
                            classes.get(i).getConstructor(long.class, boolean.class, int[].class, int.class, int[].class).newInstance(loopTime, reversed, input, controller, motors);
                            classes.get(i).getConstructor(long.class, boolean.class, int[].class).newInstance(loopTime, reversed, motors);
                            classes.get(i).getConstructor(long.class).newInstance(loopTime);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void reset(){
        motors = null;
        input = null;
        name = "";
        type = "";
        controller = 0;
        reversed = false;
        loopTime = 10;
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