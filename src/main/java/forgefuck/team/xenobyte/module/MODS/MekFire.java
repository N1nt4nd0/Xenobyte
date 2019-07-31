package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.CheatModule;

public class MekFire extends CheatModule {
    
    @Override public int tickDelay() {
        return 4;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            byte[] name = utils.myName().getBytes();
            utils.sendPacket("MEK", (byte) 20, 0, name.length, name, true);
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("Mekanism");
    }

    @Override public String moduleDesc() {
        return "Огни ранца Mekanism";
    }

}
