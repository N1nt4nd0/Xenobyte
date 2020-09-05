package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class ClientSpeed extends CheatModule {
    
    @Cfg("tickRate") private int tickRate; 
    private Timer vanilaTimer;
    
    public ClientSpeed() {
        super("ClientSpeed", Category.PLAYER, PerformMode.TOGGLE);
        tickRate = 5;
    }
    
    @Override public void onPostInit() {
        vanilaTimer = Reflections.getPrivateValue(Minecraft.class, utils.mc(), 16);
    }
    
    @Override public void onDisabled() {
        vanilaTimer.timerSpeed = 1;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            vanilaTimer.timerSpeed = tickRate;
        }    
    }
    
    @Override public String moduleDesc() {
        return lang.get("Changing the speed of client ticks", "Изменение скорости клиентских тиков");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("TickRate", tickRate, 20) {
                @Override public void onScroll(int dir, boolean withShift) {
                    tickRate = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Tick modifier", "Модификатор частоты тиков");
                }
            }
        );
    }

}