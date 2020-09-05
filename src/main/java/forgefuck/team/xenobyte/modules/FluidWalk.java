package forgefuck.team.xenobyte.modules;

import org.lwjgl.input.Keyboard;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.block.BlockLiquid;

public class FluidWalk extends CheatModule {
    
    public FluidWalk() {
        super("FluidWalk", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.block(utils.myCoords()) instanceof BlockLiquid) {
            utils.player().motionY = 0;
            if (Keyboard.isKeyDown(utils.mc().gameSettings.keyBindJump.getKeyCode())) {
                utils.player().motionY = 0.4;
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Walking on liquids", "Прогулки по жидкостям");
    }

}
