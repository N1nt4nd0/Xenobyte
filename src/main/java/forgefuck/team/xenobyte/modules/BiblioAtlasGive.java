package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class BiblioAtlasGive extends CheatModule {
    
    public BiblioAtlasGive() {
        super("BiblioAtlasGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        ItemStack ckeckItem = utils.item();
        try {
            if (Class.forName("jds.bibliocraft.items.ItemAtlas").isInstance(ckeckItem.getItem())) {
                NBTTagCompound root = new NBTTagCompound();
                NBTTagList list = new NBTTagList();
                for (int i = 0; i < (giveSelector().fillAllSlots() ? 48 : 1); i++) {
                    list.appendTag(utils.nbtItem(giveSelector().givedItem(), i, "Slot"));
                }
                root.setTag("Inventory", list);
                utils.sendPacket("BiblioAtlasSWP", utils.item(ckeckItem, root));
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return lang.get("Issuing an item to the Atlas in hand (to re-give you need to craft a new atlas)", "Выдача предмета в Атлас находящийся в руке (для повторного гива нужно скрафтить новый атлас)");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("BiblioCraft");
    }

}
