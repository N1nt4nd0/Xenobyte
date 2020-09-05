package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;

public class MekFire extends CheatModule {
    
    public MekFire() {
        super("MekFire", Category.MODS, PerformMode.TOGGLE);
    }
    
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
        return lang.get("Mekanism backpack fire lights", "Огни ранца Mekanism");
    }

}
