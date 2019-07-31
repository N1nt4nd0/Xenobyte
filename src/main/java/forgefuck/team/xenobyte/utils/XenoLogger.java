package forgefuck.team.xenobyte.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import forgefuck.team.xenobyte.api.Xeno;

public class XenoLogger {
    
    private static FileProvider fileLog = new FileProvider(Config.getLogFile(), true);
    private static Logger log = LogManager.getLogger(Xeno.mod_name);
    
    public static void info(Object message) {
        String out = Xeno.utils.nullHelper(message).replaceAll("\n", "");
        fileLog.writeLine(Xeno.utils.formatTime() + " > " + out + "\n");
        log.info(out);
    }

}
