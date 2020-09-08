package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BuildMarkerGive extends CheatModule {
    
    public BuildMarkerGive() {
        super("BuildMarkerGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("buildcraft.builders.TileConstructionMarker").isInstance(checkTile)) {
                int x = checkTile.xCoord;
                int y = checkTile.yCoord;
                int z = checkTile.zCoord;
                ByteBuf buf = utils.bufWriter((short)0, x, (short)y, z, (byte)0, x, (short)y, z, x, (short)y, z, (byte)2);
                Class.forName("buildcraft.core.lib.utils.NetworkUtils").getMethod("writeStack", ByteBuf.class, ItemStack.class).invoke(null, buf, giveSelector().givedItem());
                utils.sendPacket("BC-CORE", buf);
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return lang.get("Issuing an item to the Construction Marker that the player is looking at", "Выдача предмета в Строительную Метку на которую смотрит игрок");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("BuildCraft|Builders");
    }

}
