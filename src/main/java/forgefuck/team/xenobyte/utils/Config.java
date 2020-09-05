package forgefuck.team.xenobyte.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.exceptions.ConfigTypeException;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.handlers.ModuleHandler;

public class Config {
    
    private static final File configDir = new File(System.getProperty("user.home") + "/" + Xeno.mod_id);
    private static final File configFile = new File(configDir, "config.cfg");
    private static final Gson gson = new GsonBuilder().create();
    private static ConfigData data = new ConfigData();
    private static final FileProvider fileProvider;
    private final ModuleHandler moduleHandler;
    private static Config moduleConfig;
    private static boolean firstStart;
    
    public static ConfigData getData() {
        return data;
    }
    
    public static void save() {
        if (moduleConfig != null) {
            moduleConfig.modulesSave();
        }
        fileProvider.writeFile(gson.toJson(data));
    }
    
    public static void load() {
        try {
            data = gson.fromJson(fileProvider.readFile(), ConfigData.class);
        } catch (JsonSyntaxException e) {
            Xeno.logger.error("Config reset due to error reading from file: " + e.getMessage());
            save();
        }
    }
    
    static {
        if (!configDir.exists()) {
            configDir.mkdir();
            firstStart = true;
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                firstStart = true;
            } catch (IOException e) {}
        }
        fileProvider = new FileProvider(configFile);
        if (firstStart) {
            save();
        } else {
            load();
        }
    }
    
    public Config(ModuleHandler handler) {
        moduleHandler = handler;
        moduleConfig = this;
        if (firstStart) {
            modulesSave();
        } else {
            modulesLoad();
        }
    }
    
    private void modulesSave() {
        data.moduleData.clear();
        moduleHandler.allModules().forEach(module -> {
            String id = module.getName();
            Class moduleClass = module.getClass();
            for (Class clazz : new Class[] { moduleClass, moduleClass.getSuperclass() }) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Cfg.class)) {
                        String name = field.getAnnotation(Cfg.class).value();
                        Object value = null;
                        try {
                            field.setAccessible(true);
                            value = field.get(module);
                            if (!data.moduleData.containsKey(id)) {
                                data.moduleData.put(id, new HashMap<String, List<String>>());
                            }
                            List values = new ArrayList<String>();
                            String fName = (moduleClass == clazz ? "this:" : "super:").concat(name);
                            values.add(field.getType().getName());
                            values.add(parseModuleValue(value));
                            data.moduleData.get(id).put(fName, values);
                        } catch(Exception e) {
                            Xeno.logger.error("error while saving the ModuleConfig field: " + module + " -> " + field.getName() + " [" + e.getMessage() + "]");
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    
    private void modulesLoad() {
        data.moduleData.forEach((ID, FLDS) -> {
            CheatModule module = moduleHandler.getModuleByName(ID);
            if (module != null) {
                FLDS.forEach((FLD, DATA) -> {
                    Class clazz = FLD.startsWith("super:") ? module.getClass().getSuperclass() : module.getClass();
                    String cField = FLD.replaceFirst(".+:", "");
                    try {
                        for (Field field : clazz.getDeclaredFields()) {
                            if (field.isAnnotationPresent(Cfg.class)) {
                                String realField = field.getAnnotation(Cfg.class).value();
                                if (cField.equals(realField) && field.getType().getName().equals(DATA.get(0))) {
                                    Object value = parseModuleValue(DATA.get(0), DATA.get(1));
                                    field.setAccessible(true);
                                    field.set(module, value);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Xeno.logger.error("error while reading the ModuleConfig field: " + module + " -> " + cField + " [" + e.getMessage() + "]");
                        e.printStackTrace();
                    }
                });
            }
        });
    }
    
    private String parseModuleValue(Object val) throws Exception {
        if (val instanceof List) {
            return gson.toJson(val, List.class);
        } else if (val instanceof Map) {
            return gson.toJson(val, Map.class);
        } else {
            return String.valueOf(val);
        }
    }
    
    private Object parseModuleValue(String type, String val) throws ConfigTypeException {
        switch (type) {
        case "char":
            return Charset.forName(val);
        case "boolean":
            return Boolean.parseBoolean(val);
        case "byte":
            return Byte.parseByte(val);
        case "double":
            return Double.parseDouble(val);
        case "float":
            return Float.parseFloat(val);
        case "int":
            return Integer.parseInt(val);
        case "long":
            return Long.parseLong(val);
        case "short":
            return Short.parseShort(val);
        case "java.lang.String":
            return val;
        case "java.util.List":
            return gson.fromJson(val, List.class);
        case "java.util.Map":
            return gson.fromJson(val, Map.class);
        }
        throw new ConfigTypeException(type);
    }
    
    static class ConfigData {
        
        @SerializedName("moduleData") Map<String, Map<String, List<String>>> moduleData;
        
        ConfigData() {
            moduleData = new HashMap<String, Map<String, List<String>>>();
        }

    }

}