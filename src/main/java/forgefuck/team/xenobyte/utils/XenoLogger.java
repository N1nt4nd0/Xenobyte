package forgefuck.team.xenobyte.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import forgefuck.team.xenobyte.api.Xeno;

public class XenoLogger {
    
    private static Logger log = LogManager.getLogger(Xeno.mod_name);
    
    public static Logger getLogger() {
        return log;
    }

}