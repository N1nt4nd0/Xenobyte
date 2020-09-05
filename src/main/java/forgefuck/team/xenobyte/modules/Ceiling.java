package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.block.material.Material;

public class Ceiling extends CheatModule {
    
    public Ceiling() {
        super("Ceiling", Category.MOVE, PerformMode.SINGLE);
    }

    private boolean isHeight(int y) {
        return y > 0 && y < 255;
    }
    
    private Material getMaterial(int x, int y, int z) {
        return utils.block(x, y, z).getMaterial();
    }

    private boolean isAir(int x, int y, int z) {
        return getMaterial(x, y, z) == Material.air && getMaterial(x, y + 1, z) == Material.air;
    }
    
    private boolean isLava(int x, int y, int z) {
        return getMaterial(x, y - 1, z) == Material.lava || getMaterial(x, y, z) == Material.lava || getMaterial(x, y + 1, z) == Material.lava;
    }
    
    @Override public void onPerform(PerformSource src) {
        int[] coords = utils.myCoords();
        int x = coords[0];
        int y = coords[1];
        int z = coords[2];
        int step = utils.player().rotationPitch < 0 ? 1 : -1;
        do y += step; while( isAir(x, y, z) && isHeight(y));
        do y += step; while(!isAir(x, y, z) && isHeight(y));
        do y --;      while( isAir(x, y, z) && isHeight(y));
        if (isHeight(y)) {
            if (isLava(x, y, z)) {
                widgetMessage("- " + lang.get("lava on the way!", "лава на пути!"), WidgetMode.FAIL);
            } else {
                utils.verticalTeleport(y + 1, true);
            }
        } else {
            widgetMessage("- " + lang.get("no matching block found", "подходящий блок не найден"), WidgetMode.FAIL);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Teleportation along the Y axis through blocks up/down by sight", "Телепортация по оси Y сквозь блоки вверх/вниз по взгляду");
    }

}