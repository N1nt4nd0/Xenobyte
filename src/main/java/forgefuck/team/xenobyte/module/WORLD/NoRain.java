package forgefuck.team.xenobyte.module.WORLD;

import forgefuck.team.xenobyte.api.module.CheatModule;

public class NoRain extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.world().setRainStrength(0);
        }
    }
    
    @Override public String moduleDesc() {
        return "Когда комп говно";
    }

}
