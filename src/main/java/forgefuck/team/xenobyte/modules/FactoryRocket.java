package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.MouseEvent;

public class FactoryRocket extends CheatModule {
    
    @Cfg("inRadius") private boolean inRadius;
    
    public FactoryRocket() {
        super("FactoryRocket", Category.MODS, PerformMode.TOGGLE);
    }
    
    private void sendRocketPacket(int sourceId, int targetId) {
        utils.sendPacket("MFReloaded", (byte) 0, utils.worldId(), (short) 11, sourceId, targetId);
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 0 && e.buttonstate) {
            if (inRadius) {
                utils.nearEntityes().filter(ent -> ent instanceof EntityLivingBase).forEach(ent -> sendRocketPacket(utils.myId(), ent.getEntityId()));
            } else {
                sendRocketPacket(utils.myId(), -1);
            }
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("MineFactoryReloaded");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Launching rocket on left mouse click", "Запуск ракет по ЛКМ");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("InRadius", inRadius) {
                @Override public void onLeftClick() {
                    buttonValue(inRadius = !inRadius);
                }
                @Override public String elementDesc() {
                    return lang.get("By living creatures in radius or sight", "По живности в радиусе или взгляду");
                }
            }
        );
    }

}