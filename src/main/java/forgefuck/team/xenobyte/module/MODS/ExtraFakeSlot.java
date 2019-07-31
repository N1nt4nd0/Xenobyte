package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.tileentity.TileEntity;

public class ExtraFakeSlot extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("extracells.tileentity.TileEntityFluidFiller").isInstance(checkTile)) {
                utils.sendPacket("extracells", (byte) 8, (byte) 0, true, utils.worldId(), utils.myName().length(), utils.myName().getBytes(), utils.worldId(), utils.coords(checkTile), giveSelector().givedItem());
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return "Выдача фейк предмета в жидкостный заполнитель на который смотрит игрок";
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("extracells");
    }

}
