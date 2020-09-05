package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;

public class TinkerGive extends CheatModule {
    
    public TinkerGive() {
        super("TinkerGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        int[] mop = utils.mop();
        utils.sendPacket("TConstruct", (byte) 8, mop[0], mop[1], mop[2], giveSelector().givedItem());
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("TConstruct");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Issuing an item to any inventory from TinkersConstruct that the player is looking at", "Выдача предмета в любой инвентарь из TinkersConstruct на который смотрит игрок");
    }
    
}
