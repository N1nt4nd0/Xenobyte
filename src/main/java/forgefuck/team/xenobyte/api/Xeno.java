package forgefuck.team.xenobyte.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import forgefuck.team.xenobyte.render.Renderer;
import forgefuck.team.xenobyte.utils.Utils;

public interface Xeno {

    String mod_id = "xenobyte";
    String author = "N1nt3nd0";
    String mod_name = "X3N0BYT3";
    String mod_version = "1.0.7";
    String format_prefix = "§8[§4" + mod_name + "§8]§r ";

    Logger logger = LogManager.getLogger(mod_name);
    Renderer render = new Renderer();
    Utils utils = new Utils();
    
}