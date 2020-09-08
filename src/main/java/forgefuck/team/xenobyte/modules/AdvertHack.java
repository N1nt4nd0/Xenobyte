package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import forgefuck.team.xenobyte.utils.Rand;

public class AdvertHack extends CheatModule {
    
    @Cfg("urls") private List<String> urls;
    
    public AdvertHack() {
        super("AdvertHack", Category.MODS, PerformMode.SINGLE);
        urls = new ArrayList<String>();
        urls.add("");
    }
    
    @Override public void onPerform(PerformSource src) {
        String url = urls.get(0);
        if (!url.isEmpty()) {
            for (int id = 0; id <= 100; id++) {
                utils.sendPacket("malisisadvert", (byte) 7, id, Rand.str(), url);
            }
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("malisisadvert");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Replacing all advertising images in MalisisAdvert with an image from the link", "Замена всех рекламных картинок в MalisisAdvert на картинку из ссылки");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("AdvertUrl") {
                @Override public void onLeftClick() {
                    new UserInput(lang.get("Links", "Ссылки"), urls, InputType.SINGLE_STRING).showFrame();
                }
            }
        );
    }

}
