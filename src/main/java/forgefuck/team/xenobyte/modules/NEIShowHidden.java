package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.integration.NEI;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiOpenEvent;

public class NEIShowHidden extends CheatModule {
    
    public NEIShowHidden() {
        super("NEIShowHidden", Category.NONE, PerformMode.ENABLED_ON_START);
    }
    
    @Override public boolean isWorking() {
        return NEI.isAvailable();
    }
    
    @Override public boolean isWidgetable() {
        return false;
    }
    
    @SubscribeEvent public void guiOpen(GuiOpenEvent e) {
        if (e.gui instanceof GuiContainer) {
            NEI.clearHiddenItems();
        }
    }

}
