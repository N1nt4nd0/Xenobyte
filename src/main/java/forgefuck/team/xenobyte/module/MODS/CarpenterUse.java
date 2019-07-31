package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraftforge.client.event.MouseEvent;

public class CarpenterUse extends CheatModule {
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            int[] mop = utils.mop();
            utils.sendPacket("CarpentersBlocks", 0, mop[0], mop[1], mop[2], mop[3]);
            e.setCanceled(true);
        }
    }
    
    @Override public String moduleDesc() {
        return "В большинстве случаев позволяет использовать ПКМ в привате";
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("CarpentersBlocks");
    }
    
}