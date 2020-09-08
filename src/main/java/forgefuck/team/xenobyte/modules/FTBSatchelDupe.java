package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FTBSatchelDupe extends CheatModule{

    public FTBSatchelDupe() {
        super("FTBSatchelDupe", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        try {
            if (Class.forName("cofh.thermalexpansion.gui.container.ContainerSatchel").isInstance(utils.player().openContainer)) {
                Class.forName("ftb.lib.api.net.MessageLM").getMethod("sendToServer").invoke(Class.forName("ftb.lib.mod.net.MessageClientItemAction").getConstructor(String.class, NBTTagCompound.class).newInstance("", new NBTTagCompound()));
                int dropCount = (int) Class.forName("cofh.thermalexpansion.item.ItemSatchel").getMethod("getStorageIndex", ItemStack.class).invoke(null, utils.item());
                for(int slot = utils.mySlotsCount(); slot < utils.mySlotsCount() + dropCount * 9; slot++) {
                    utils.dropSlot(slot, true);
                }
                utils.player().closeScreen();
            }
        } catch (Exception e) {}
    }
    
    @Override public boolean inGuiPerform() {
        return true;
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("ThermalExpansion") && Loader.isModLoaded("FTBL");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Open any ThermalExpansion bag with Items and press keybind of function", "Открыть любую сумку из ThermalExpansion с вещами и нажать кейбинд функции");
    }

}
