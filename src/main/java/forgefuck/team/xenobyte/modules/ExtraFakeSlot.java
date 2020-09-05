package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.tileentity.TileEntity;

public class ExtraFakeSlot extends CheatModule {
    
    public ExtraFakeSlot() {
        super("ExtraFakeSlot", Category.MODS, PerformMode.SINGLE);
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
        return lang.get("Issuing a fake item into a liquid filler that the player is looking at", "Выдача фейк предмета в жидкостный заполнитель на который смотрит игрок");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("extracells");
    }

}
