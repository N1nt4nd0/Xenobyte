package forgefuck.team.xenobyte.module.PLAYER;

import org.lwjgl.input.Keyboard;

import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.block.BlockLiquid;

public class FluidWalk extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.block(utils.myCoords()) instanceof BlockLiquid) {
            utils.player().motionY = 0;
            if (Keyboard.isKeyDown(utils.mc().gameSettings.keyBindJump.getKeyCode())) {
                utils.player().motionY = 0.4;
            }
        }
    }
    
    @Override public String moduleDesc() {
        return "Прогулки по жидкостям";
    }

}
