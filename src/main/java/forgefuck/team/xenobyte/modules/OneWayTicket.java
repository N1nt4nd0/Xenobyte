package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class OneWayTicket extends CheatModule {
    
    public OneWayTicket() {
        super("OneWayTicket", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        ItemStack ckeckItem = utils.item();
        try {
            if (Class.forName("mods.railcraft.common.util.network.IEditableItem").isInstance(ckeckItem.getItem())) {
                NBTTagCompound nbt = giveSelector().givedNBT();
                if (!nbt.hasNoTags()) {
                    Class.forName("mods.railcraft.common.util.network.PacketDispatcher").getMethod("sendToServer", Class.forName("mods.railcraft.common.util.network.RailcraftPacket")).invoke(null, Class.forName("mods.railcraft.common.util.network.PacketCurrentItemNBT").getConstructor(EntityPlayer.class, ItemStack.class).newInstance(utils.player(), utils.item(ckeckItem, nbt)));
                }
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return "Применение NBT из Chanter'a к Билету или Таблице Маршрутизации находящейся в руке";
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("Railcraft");
    }

}
