package forgefuck.team.xenobyte.utils;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class Reflections extends ReflectionHelper {
    
    public static boolean exists(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {}
        return false;
    }

}
