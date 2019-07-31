package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.module.CheatModule;

public class AutoSprint extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.player().setSprinting(true);
        }
    }
    
    @Override public String moduleDesc() {
        return "Постоянный спринт";
    }

}
