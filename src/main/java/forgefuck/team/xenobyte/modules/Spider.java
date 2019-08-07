package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.utils.Keys;

public class Spider extends CheatModule {
    
    public Spider() {
        super("Spider", Category.PLAYER, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.player().isCollidedHorizontally && Keys.isPressed(utils.mc().gameSettings.keyBindForward)) {
            utils.player().motionY = 0.4;
        }        
    }
    
    @Override public String moduleDesc() {
        return "Лазание по стенам";
    }
    
}
