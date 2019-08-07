package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;

public class ZtonesMeta extends CheatModule {
    
    @Cfg private boolean metaUp;
    
    public ZtonesMeta() {
        super("ZtonesMeta", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource type) {
        utils.sendPacket("Ztones", 0, metaUp);
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("Ztones");
    }
    
    @Override public String moduleDesc() {
        return "Меняет мету предмета вверх до 15 или вниз до 0 (главное не переборщить)";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("MetaUP", metaUp) {
                @Override public void onLeftClick() {
                    buttonValue(metaUp = !metaUp);
                }
                @Override public String elementDesc() {
                    return "Листать мету вверх или вниз";
                }
            }    
        );
    }

}