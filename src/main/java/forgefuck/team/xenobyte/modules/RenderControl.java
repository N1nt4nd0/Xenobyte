package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;

public class RenderControl extends CheatModule {
    
    @Cfg("living") private boolean living;
    
    public RenderControl() {
        super("RenderControl", Category.WORLD, PerformMode.TOGGLE);
        living = true;
    }

    @SubscribeEvent public void worldRender(RenderLivingEvent.Pre e) {
        if (!living && !(e.entity instanceof EntityPlayer)) {
            e.setCanceled(true);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Enabling rendering of objects in the world", "Включение рендера объектов в мире");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("LivingBase", living) {
                @Override public void onLeftClick() {
                    buttonValue(living = !living);
                }
                @Override public String elementDesc() {
                    return lang.get("Render living base", "Рендер живности");
                }
            }
        );
    }

}
