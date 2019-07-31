package forgefuck.team.xenobyte.module.WORLD;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class VanilaNuker extends CheatModule {
    
    @Cfg private boolean onView, handshake;
    @Cfg private int radius;
    
    @Override public void onPreInit() {
        radius = 1;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.nukerList(onView ? utils.mop() : utils.myCoords(), radius).forEach(pos -> {
                int x = pos[0];
                int y = pos[1];
                int z = pos[2];
                if (utils.isInCreative()) {
                    utils.sendPacket(new C07PacketPlayerDigging(0, x, y, z, 0));
                } else {
                    utils.sendPacket(new C07PacketPlayerDigging(0, x, y, z, 0));
                    utils.sendPacket(new C07PacketPlayerDigging(2, x, y, z, 0));
                }
            });
            if (handshake) {
                utils.player().swingItem();
            }
        }
    }
    
    @Override public String moduleDesc() {
        return "Ломание блоков в радиусе (в тике)";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Radius", radius, 6) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
                }
            },
            new Button("OnView", onView) {
                   @Override public void onLeftClick() {
                       buttonValue(onView = !onView);
                }
                   @Override public String elementDesc() {
                    return "По взгляду или вокруг игрока";
                }
            },
            new Button("HandShake", handshake) {
                   @Override public void onLeftClick() {
                       buttonValue(handshake = !handshake);
                }
                   @Override public String elementDesc() {
                    return "Взмах руки";
                }
            }
        );
    }

}
