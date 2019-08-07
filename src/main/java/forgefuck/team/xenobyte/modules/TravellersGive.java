package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class TravellersGive extends CheatModule {

    public TravellersGive() {
        super("TravellersGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        if (checkTile instanceof TileEntityChest) {
            utils.sendPacket("TravellersGear", (byte) 8, utils.worldId(), utils.coords(checkTile), utils.chestGiveHelper((TileEntityChest) checkTile, giveSelector()));
        }
    }
    
    @Override public String moduleDesc() {
        return "Выдача предмета в обычный сундук на который смотрит игрок";
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("TravellersGear");
    }
    
}
