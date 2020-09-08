package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class NanoTechGive extends CheatModule {
    
    public NanoTechGive() {
        super("NanoTechGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        if (checkTile instanceof TileEntityChest) {
            try {
                Class yogpstop = Class.forName("com.yogpc.qp.YogpstopPacket");
                Class.forName("com.yogpc.qp.PacketHandler").getMethod("sendPacketToServer", yogpstop).invoke(null, yogpstop.getConstructor(TileEntity.class).newInstance(utils.tile(checkTile, utils.chestGiveHelper((TileEntityChest) checkTile, giveSelector()))));
            } catch(Exception e) {}
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Item give into a vanilla chest that the player is looking at", "Выдача предмета в обычный сундук на который смотрит игрок");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("NanoTech_Machine");
    }

}