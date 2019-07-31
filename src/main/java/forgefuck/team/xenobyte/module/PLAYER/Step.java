package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;

public class Step extends CheatModule {
    
    @Cfg private int stepHeight;
    
    @Override public void onPreInit() {
        stepHeight = 1;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.player().stepHeight = stepHeight;
        }
    }
    
    @Override public void onDisabled() {
        utils.player().stepHeight = 0.5F;
    }
    
    @Override public String moduleDesc() {
        return "Мгновенное взбирание на заданное количество блоков";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Height", stepHeight, 64) {
                @Override public void onScroll(int dir, boolean withShift) {
                    stepHeight = processSlider(dir, withShift);
                }
            }
        );
    }

}
