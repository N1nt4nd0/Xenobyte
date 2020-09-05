package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.utils.Keys;

public class Spider extends CheatModule {
    
    @Cfg("vSpeed") private float vSpeed;
    private boolean spiding = false;
    
    public Spider() {
        super("Spider", Category.MOVE, PerformMode.TOGGLE);
        vSpeed = 1F;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.player().isCollidedHorizontally && (Keys.isPressed(utils.mc().gameSettings.keyBindForward) || Keys.isPressed(utils.mc().gameSettings.keyBindBack) || Keys.isPressed(utils.mc().gameSettings.keyBindLeft) || Keys.isPressed(utils.mc().gameSettings.keyBindRight))) {
            utils.player().motionY = vSpeed;
            spiding = true;
        } else if(inGame && spiding) {
            utils.player().motionY = Math.min(0.4F, vSpeed);
            spiding = false;
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Climbing the walls", "Лазание по стенам");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Speed", (int) (vSpeed * 10), 30) {
                @Override public void onScroll(int dir, boolean withShift) {
                    vSpeed = (float) processSlider(dir, withShift) / 10;
                }
                @Override public String elementDesc() {
                    return lang.get("Climbing speed", "Cкорость взбирания");
                }
            }
        );
    }
    
}