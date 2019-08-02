package forgefuck.team.xenobyte.api;

import forgefuck.team.xenobyte.render.Renderer;
import forgefuck.team.xenobyte.utils.Utils;

public interface Xeno {

    String mod_id = "xenobyte";
    String author = "N1nt3nd0";
    String mod_name = "X3N0BYT3";
    String mod_version = "1.0.1";
    String format_prefix = "§8[§4" + mod_name + "§8]§r ";
    String modules_package = "forgefuck.team.xenobyte.module";

    Renderer render = new Renderer();
    Utils utils = new Utils();
    
}