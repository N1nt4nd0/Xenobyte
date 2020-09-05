package forgefuck.team.xenobyte.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import forgefuck.team.xenobyte.render.Renderer;
import forgefuck.team.xenobyte.utils.LangProvider;
import forgefuck.team.xenobyte.utils.Utils;

public interface Xeno {

    String mod_id = "xenobyte";
    String mod_name = "X3N0BYT3";
    String mod_version = "1.0.9";
    String mod_author = "N1nt4nd0";
    String format_prefix = "§8[§4" + mod_name + "§8]§r ";
    
    String ds_link = "N1nt4nd0#0613";
    String tg_link = "t.me/N1nt4nd0";
    String gh_link = "github.com/N1nt4nd0/Xenobyte";
    String yt_link = "youtube.com/channel/UClXGh0w1BiBEyxn7iFI4dsA";

    Utils utils = new Utils();
    Renderer render = new Renderer();
    LangProvider lang = new LangProvider();
    Logger logger = LogManager.getLogger(mod_name);
    
}