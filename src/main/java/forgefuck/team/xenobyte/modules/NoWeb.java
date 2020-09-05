package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.entity.Entity;

public class NoWeb extends CheatModule {
    
    public NoWeb() {
        super("NoWeb", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            Reflections.setPrivateValue(Entity.class, utils.player(), false, 27);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("The web no longer clings", "Паутина больше не цепляет");
    }

}
