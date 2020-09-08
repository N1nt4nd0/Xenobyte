package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CrayfishGive extends CheatModule {
    
    public CrayfishGive() {
        super("CrayfishGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        ItemStack outStack = new ItemStack(Items.apple);
        NBTTagCompound root = new NBTTagCompound();
        NBTTagCompound pack = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < (giveSelector().fillAllSlots() ? 6 : 1); i++) {
            list.appendTag(utils.nbtItem(giveSelector().givedItem(), i, "Slot"));
        }
        pack.setTag("Items", list);
        root.setTag("Package", pack);
        outStack.setTagCompound(root);
        utils.sendPacket("cfm", (byte) 10, outStack);
    }
    
    @Override public String moduleDesc() {
        return lang.get("Issuing an item in a package to the active slot (will replace the current item)", "Выдача предмета в посылке в активный слот (заменит текущий предмет)");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("cfm");
    }

}