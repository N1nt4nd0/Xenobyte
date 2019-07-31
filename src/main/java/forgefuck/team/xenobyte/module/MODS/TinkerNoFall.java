package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.CheatModule;

public class TinkerNoFall extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.sendPacket("TConstruct", (byte) 4);
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("TConstruct");
    }

    @Override public String moduleDesc() {
        return "Убирает урон от падения";
    }
    
}
