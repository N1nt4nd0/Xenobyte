package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.block.material.Material;

public class Ceiling extends CheatModule {
    
    public Ceiling() {
        super("Ceiling", Category.MOVE, PerformMode.SINGLE);
    }
    
    private boolean isAcceptableMaterial(Material material) {
        return material == Material.air || material == Material.water || material == Material.web || material == Material.lava;
    }

    private boolean isAcceptableBlocks(int x, int y, int z) {
        return isAcceptableMaterial(utils.block(x, y, z).getMaterial()) && isAcceptableMaterial(utils.block(x, y + 1, z).getMaterial());
    }
    
    private boolean isAcceptableCoordinates(int x, int y, int z) {
        return 0 < y && y < 255;
    }
    
    @Override public void onPerform(PerformSource src) {
        int[] coords = utils.myCoords();
        int x = coords[0];
        int y = coords[1];
        int z = coords[2];
        int step = utils.player().rotationPitch < 0 ? 1 : -1;
        do y += step; while( isAcceptableBlocks(x, y, z) && isAcceptableCoordinates(x, y, z));
        do y += step; while(!isAcceptableBlocks(x, y, z) && isAcceptableCoordinates(x, y, z));
        do y--;       while( isAcceptableBlocks(x, y, z) && isAcceptableCoordinates(x, y, z));
        if (isAcceptableCoordinates(x, y++, z)) {
            utils.verticalTeleport(y, true);
        }
    }
    
    @Override public String moduleDesc() {
        return "Телепортация по оси Y сквозь блоки вверх/вниз по взгляду";
    }

}