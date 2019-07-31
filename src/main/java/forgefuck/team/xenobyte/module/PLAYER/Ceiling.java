package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;

public class Ceiling extends CheatModule {
    
    private boolean isAir(int x, int y, int z) {
        return utils.block(x, y, z) instanceof BlockAir;
    }
    
    private boolean isLava(int x, int y, int z) {
        return utils.block(x, y, z).getMaterial() == Material.lava;
    }
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        int[] coords = utils.myCoords();
        int x = coords[0];
        int y = coords[1];
        int z = coords[2];
        if (utils.player().rotationPitch > 0) {
            do y --; while (isAir(x, y, z) && y > 0);
            do y --; while (!isAir(x, y, z) && y > 0);
            do y --; while (isAir(x, y, z) && y > 0);
            if (y > 0 && !isLava(x, y, z)) {
                utils.verticalTeleport(y + 1, true);
            }
        } else if (utils.player().rotationPitch < 0) {
            do y ++; while (isAir(x, y, z) && y < 256);
            do y ++; while (!isAir(x, y, z) && y < 256);
            if (y < 256 && !isLava(x, y, z)) {
                utils.verticalTeleport(y, true);
            }
        }
    }
    
    @Override public String moduleDesc() {
        return "Телепортация по оси Y через блоки вверх/низ по взгляду";
    }

}
