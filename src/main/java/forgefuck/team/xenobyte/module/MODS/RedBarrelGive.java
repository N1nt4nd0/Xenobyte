package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.tileentity.TileEntity;

public class RedBarrelGive extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("mrtjp.projectred.exploration.TileBarrel").isInstance(checkTile)) {
                utils.sendPacket("MrTJPCoreMod", (byte) 1, utils.coords(checkTile), (byte) 2, giveSelector().givedItem(), giveSelector().fillAllSlots() ? Integer.MAX_VALUE : giveSelector().itemCount());
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return "Выдача предмета в Бочку на которую смотрит игрок";
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("ProjRed|Exploration");
    }

}