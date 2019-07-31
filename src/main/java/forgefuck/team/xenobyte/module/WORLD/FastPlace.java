package forgefuck.team.xenobyte.module.WORLD;

import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.client.Minecraft;

public class FastPlace extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            Reflections.setPrivateValue(Minecraft.class, utils.mc(), 0, 47);
        }
    }
    
    @Override public String moduleDesc() {
        return "Быстрая установка блоков";
    }

}