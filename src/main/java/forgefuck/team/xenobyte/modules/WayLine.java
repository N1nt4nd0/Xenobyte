package forgefuck.team.xenobyte.modules;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.ColorPicker;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.ColorPickerGui;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class WayLine extends CheatModule {
    
    @Cfg("color") private int color;
    private List<double[]> poses;
    private ColorPicker picker;
    
    public WayLine() {
        super("WayLine", Category.WORLD, PerformMode.TOGGLE);
        poses = new CopyOnWriteArrayList<double[]>();
        color = new Color(0, 255, 255).getRGB();
    }
    
    @Override public void onPostInit() {
        picker = new ColorPicker(color) {
            @Override public void onColorUpdate() {
                WayLine.this.color = rgba;
            }
        };
    }
    
    @Override public int tickDelay() {
        return 5;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            EntityPlayer pl = utils.player();
            if (!pl.isDead && !pl.isPlayerSleeping() && !utils.isAfk(pl)) {
                poses.add(new double[] { RenderManager.renderPosX, RenderManager.renderPosY - pl.height, RenderManager.renderPosZ });
            }
        }
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        render.WORLD.drawWayLine(poses, picker.rf, picker.gf, picker.bf, picker.af, 3);
    }
    
    @Override public String moduleDesc() {
        return lang.get("Drawing the trackline behind the player", "Отрисовка путевой линии за игроком");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("LineColor") {
                @Override public void onLeftClick() {
                    new ColorPickerGui("Цвет линии", picker).showFrame();
                }
                @Override public String elementDesc() {
                    return lang.get("Line color", "Цвет линии");
                }
            },
            new Button("Clear") {
                @Override public void onLeftClick() {
                    poses.clear();
                }
                @Override public String elementDesc() {
                    return lang.get("Clear line", "Очистить линию");
                }
            }
        );
    }

}
