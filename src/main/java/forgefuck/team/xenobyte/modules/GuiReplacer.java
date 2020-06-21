package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.utils.Rand;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;

public class GuiReplacer extends CheatModule {
    
    public GuiReplacer() {
        super("GuiReplacer", Category.NONE, PerformMode.ENABLED_ON_START);
    }
    
    private void replaceSplash(GuiScreen gui) {
        if (gui instanceof GuiMainMenu) {
            try {
                Reflections.setPrivateValue(GuiMainMenu.class, (GuiMainMenu) gui, Rand.splash(), 3);
            } catch(Exception e) {}
        }
    }
    
    @Override public void onPostInit() {
        replaceSplash(utils.currentScreen());
    }
    
    @Override public boolean isWidgetable() {
        return false;
    }
    
    @SubscribeEvent public void guiOpen(GuiOpenEvent e) {
        replaceSplash(e.gui);
    }

}