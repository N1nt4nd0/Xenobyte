package forgefuck.team.xenobyte.modules;

import java.lang.reflect.Array;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.item.ItemStack;

public class RFCellDupe extends CheatModule {
    
    public RFCellDupe() {
        super("RFCellDupe", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        ItemStack ckeckItem = utils.item();
        try {
            if (Class.forName("appeng.items.storage.ItemBasicStorageCell").isInstance(ckeckItem.getItem())) {
                Object args = Array.newInstance(Class.forName("mcjty.lib.network.Argument"), 63);
                for (int i = 0; i < 63; i++) {
                    Array.set(args, i, Class.forName("mcjty.lib.network.Argument").getConstructor(String.class, int.class).newInstance("@" + i, Integer.MAX_VALUE));
                }
                SimpleNetworkWrapper.class.getMethod("sendToServer", IMessage.class).invoke(Class.forName("mcjty.rftools.network.RFToolsMessages").getField("INSTANCE").get(null), Class.forName("mcjty.lib.network.PacketUpdateNBTItem").getConstructor(Class.forName("[Lmcjty.lib.network.Argument;")).newInstance(args));
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return lang.get("Creating infinite items in a ME cell in hand", "Создание бесконечных предметов в ME ячейке в руке");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("rftools") && Loader.isModLoaded("appliedenergistics2");
    }

}
