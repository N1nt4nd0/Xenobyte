package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;

public class Step extends CheatModule {
    
    @Cfg("stepHeight") private int stepHeight;
    
    public Step() {
        super("Step", Category.MOVE, PerformMode.TOGGLE);
        stepHeight = 5;
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
        return lang.get("High step a given number of blocks", "Мгновенное взбирание на заданное количество блоков");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Height", stepHeight, 30) {
                @Override public void onScroll(int dir, boolean withShift) {
                    stepHeight = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Step height", "Высота взбирания");
                }
            }
        );
    }

}