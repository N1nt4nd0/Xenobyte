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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;

public class TainedAura extends CheatModule {
    
    @Cfg("withSound") private boolean withSound;
    @Cfg("inRadius") private boolean inRadius;
    
    public TainedAura() {
        super("TainedAura", Category.MODS, PerformMode.TOGGLE);
    }
    
    private void sendTainedPacket(Entity e) {
        if (e != null) {
            int sourceId = (e instanceof EntityPlayer) ? e.getEntityId() : utils.myId();
            utils.sendPacket("taintedmagic", (byte) 0, e.getEntityId(), sourceId, utils.worldId(), Float.MAX_VALUE, withSound);    
        }
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 0 && e.buttonstate) {
            if (inRadius) {
                utils.nearEntityes().filter(ent -> ent instanceof EntityLivingBase).forEach(ent -> {
                    sendTainedPacket(ent);
                });
            } else {
                sendTainedPacket(utils.entity());
            }
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("TaintedMagic");
    }

    @Override public String moduleDesc() {
        return lang.get("Killing all living creatures by left mouse click", "Ваншотает живность по ЛКМ");
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
            },
            new Button("WithSound", withSound) {
                @Override public void onLeftClick() {
                    buttonValue(withSound = !withSound);
                }
                @Override public String elementDesc() {
                    return lang.get("Some weird sound", "Какой-то стрёмный звук");
                }
            }
        );
    }

}
