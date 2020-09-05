package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.MouseEvent;

public class GalacticFire extends CheatModule {
    
    @Cfg("inRadius") private boolean inRadius;
    
    public GalacticFire() {
        super("GalacticFire", Category.MODS, PerformMode.TOGGLE);
    }
    
    private void sendFirePacket(Entity e) {
        if (e != null) {
            utils.sendPacket("GalacticraftCore", (byte) 0, 7, e.getEntityId());    
        }
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 0 && e.buttonstate) {
            if (inRadius) {
                utils.nearEntityes().filter(ent -> ent instanceof EntityLivingBase).forEach(this::sendFirePacket);
            } else {
                sendFirePacket(utils.entity());
            }
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("GalacticraftCore");
    }

    @Override public String moduleDesc() {
        return lang.get("Sets fire to living creatures by left mouse click", "Поджигает живность по ЛКМ");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("InRadius", inRadius) {
                @Override public void onLeftClick() {
                    buttonValue(inRadius = !inRadius);
                }
                @Override public String elementDesc() {
                    return lang.get("By radius or sight", "По радиусу или взгляду");
                }
            }
        );
    }

}
