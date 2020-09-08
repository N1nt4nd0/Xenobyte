package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;

public class ThaumicFinger extends CheatModule {
    
    public ThaumicFinger() {
        super("ThaumicFinger", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.sendPacket("thaumichorizons", (byte) 9, utils.myId(), utils.worldId());
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("ThaumicHorizons");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Opening the dupe workbench", "Открытие дюп - верстака");
    }

}
