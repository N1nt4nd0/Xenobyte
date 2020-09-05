package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraftforge.client.event.MouseEvent;

public class CarpenterUse extends CheatModule {
    
    public CarpenterUse() {
        super("CarpenterUse", Category.MODS, PerformMode.TOGGLE);
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            int[] mop = utils.mop();
            utils.sendPacket("CarpentersBlocks", 0, mop[0], mop[1], mop[2], mop[3]);
            e.setCanceled(true);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("In most cases, allows you to use right click in clamed area", "В большинстве случаев позволяет использовать ПКМ в привате");
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("CarpentersBlocks");
    }
    
}