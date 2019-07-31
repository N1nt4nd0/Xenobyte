package forgefuck.team.xenobyte.module.NONE;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.integration.NEI;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiOpenEvent;

public class NEIShowHidden extends CheatModule {
    
    @Override public boolean isWorking() {
        return NEI.isAvailable();
    }
    
    @Override public boolean forceEnabled() {
        return true;
    }
    
    @Override public boolean provideStateEvents() {
        return false;
    }
    
    @SubscribeEvent public void guiOpen(GuiOpenEvent e) {
        if (e.gui instanceof GuiContainer) {
            NEI.clearHiddenItems();
        }
    }

}
