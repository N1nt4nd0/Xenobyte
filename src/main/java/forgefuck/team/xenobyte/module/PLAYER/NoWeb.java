package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.entity.Entity;

public class NoWeb extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            Reflections.setPrivateValue(Entity.class, utils.player(), false, 27);
        }
    }
    
    @Override public String moduleDesc() {
        return "Паутина больше не цепляет";
    }

}
