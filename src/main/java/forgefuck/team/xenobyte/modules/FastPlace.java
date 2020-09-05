package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.client.Minecraft;

public class FastPlace extends CheatModule {
    
    public FastPlace() {
        super("FastPlace", Category.WORLD, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            Reflections.setPrivateValue(Minecraft.class, utils.mc(), 0, 47);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Quick block placing", "Быстрая установка блоков");
    }

}