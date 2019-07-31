package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;

public class ThaumicInvise extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.sendPacket("thaumichorizons", (byte) 13, utils.myId(), utils.worldId());
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("ThaumicHorizons");
    }
    
    @Override public String moduleDesc() {
        return "Переключение невидимости игрока";
    }

}
