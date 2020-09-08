package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class MatterGiveItem extends CheatModule {
    
    public MatterGiveItem() {
        super("MatterGiveItem", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("matteroverdrive.tile.TileEntityTritaniumCrate").isInstance(checkTile)) {
                NBTTagCompound root = new NBTTagCompound();
                NBTTagList list = new NBTTagList();
                int slots = giveSelector().fillAllSlots() ? utils.slots(checkTile) : 1;
                for (int i = 0; i < slots; i++) {
                    list.appendTag(utils.nbtItem(giveSelector().givedItem(), i, "Slot"));
                }
                root.setTag("Items", list);
                utils.sendPacket("mo_channel", (byte) 24, utils.coords(checkTile), root, 4, true);
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return lang.get("Issuing an item to the Tritanium Crate that the player is looking at", "Выдача предмета в Тритановый Ящик на который смотрит игрок");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("mo");
    }

}
