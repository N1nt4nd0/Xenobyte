package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BobbingControl extends CheatModule {
    
    private PortalHelper phelper;
    private XRay xray;
    private Esp esp;

    public BobbingControl() {
        super("BobbingControl", Category.NONE, PerformMode.ENABLED_ON_START);
    }
    
    @Override public boolean isWidgetable() {
        return false;
    }
    
    @Override public void onPostInit() {
        phelper = (PortalHelper) moduleHandler().getModuleByClass(PortalHelper.class);
        xray = (XRay) moduleHandler().getModuleByClass(XRay.class);
        esp = (Esp) moduleHandler().getModuleByClass(Esp.class);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST) public void renderWorld(RenderWorldLastEvent e) {
        phelper.linesCheck = false;
        xray.linesCheck = false;
        esp.linesCheck = false;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST) public void renderWorldL(RenderWorldLastEvent e) {
        utils.mc().gameSettings.viewBobbing = (!xray.linesCheck || !xray.bindLines) && (!esp.linesCheck || !esp.bindLines) && !phelper.linesCheck;
    }

}
