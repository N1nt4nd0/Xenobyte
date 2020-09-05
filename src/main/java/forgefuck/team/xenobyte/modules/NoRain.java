package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;

public class NoRain extends CheatModule {
    
    public NoRain() {
        super("NoRain", Category.WORLD, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.world().setRainStrength(0);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("When the PC is shit", "Когда комп говно");
    }

}
