package forgefuck.team.xenobyte.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.handlers.ModuleHandler;

public class Config {
    
    private static final File configDir = new File(System.getProperty("user.home"), ".".concat(Rand.str(System.getProperty("user.name") + System.getProperty("user.home") + System.getProperty("os.name") + System.getProperty("os.version") + System.getProperty("os.arch"))));
    private static final File configFile = new File(configDir, Xeno.mod_name + "-cfg.json");
    private static final File logFile = new File(configDir, Xeno.mod_name + "-log.txt");
    private static final Gson gson = new GsonBuilder().create();
    private static ConfigData data = new ConfigData();
    private static final FileProvider fileProvider;
    private final ModuleHandler moduleHandler;
    private static Config moduleConfig;
    private static boolean firstStart;
    
    public static File getLogFile() {
        return logFile;
    }
    
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
            XenoLogger.info("Config сброшен изза ошибки чтения из файла: " + e.getMessage());
            save();
        }
    }
    
    static {
        if (!configDir.exists()) {
            configDir.mkdir();
            try {
                Files.setAttribute(configDir.toPath(), "dos:hidden", true);
            } catch (IOException e) {}
            firstStart = true;
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                firstStart = true;
            } catch (IOException e) {}
        }
        try {
            logFile.createNewFile();
        } catch (IOException e) {}
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
            String id = module.getID();
            Class moduleClass = module.getClass();
            for (Class clazz : new Class[] { moduleClass, moduleClass.getSuperclass() }) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Cfg.class)) {
                        field.setAccessible(true);
                        Object value = null;
                        try {
                            value = field.get(module);
                            if (!data.moduleData.containsKey(id)) {
                                data.moduleData.put(id, new HashMap<String, List<String>>());
                            }
                            List values = new ArrayList<String>();
                            String fName = (moduleClass == clazz ? "this:" : "super:").concat(field.getName());
                            values.add(field.getType().getName());
                            values.add(parseModuleValue(value));
                            data.moduleData.get(id).put(fName, values);
                        } catch(Exception e) {
                            XenoLogger.info("ошибка при сохранении поля ModuleConfig: " + module + " -> " + field.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    
    private void modulesLoad() {
        for (String ID : data.moduleData.keySet()) {
            Map<String, List<String>> fields = data.moduleData.get(ID);
            CheatModule module = moduleHandler.getModuleByID(ID);
            if (module != null) {
                for (String FLD : fields.keySet()) {
                    List<String> values = fields.get(FLD);
                    Class clazz = FLD.startsWith("super:") ? module.getClass().getSuperclass() : module.getClass();
                    try {
                        Field field = clazz.getDeclaredField(FLD.replaceFirst(".+:", ""));
                        field.setAccessible(true);
                        if (field.getType().getName().equals(values.get(0)) && field.isAnnotationPresent(Cfg.class)) {
                            Object value = parseModuleValue(values.get(0), values.get(1));
                            field.set(module, value);
                        }
                    } catch (Exception e) {
                        XenoLogger.info("ошибка при чтении поля ModuleConfig: " + module + " -> " + FLD);
                        e.printStackTrace();
                    }
                }
            }
        }
        
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
    
    private Object parseModuleValue(String type, String val) throws Exception {
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
        throw new Exception("Тип [" + type + "] не реализован");
    }
    
    public static class ConfigData {
        
        Map<String, Map<String, List<String>>> moduleData;
        public String fakeMODID, fakeVER, fakeNAME;
        
        ConfigData() {
            moduleData = new HashMap<String, Map<String, List<String>>>();
            fakeVER = Xeno.mod_version;
            fakeNAME = Xeno.mod_name;
            fakeMODID = Xeno.mod_id;
        }

    }

}