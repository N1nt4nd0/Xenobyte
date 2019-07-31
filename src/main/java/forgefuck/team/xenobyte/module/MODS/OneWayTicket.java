package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class OneWayTicket extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        ItemStack ckeckItem = utils.item();
        try {
            if (Class.forName("mods.railcraft.common.util.network.IEditableItem").isInstance(ckeckItem.getItem())) {
                Class.forName("mods.railcraft.common.util.network.PacketDispatcher").getMethod("sendToServer", Class.forName("mods.railcraft.common.util.network.RailcraftPacket")).invoke(null, Class.forName("mods.railcraft.common.util.network.PacketCurrentItemNBT").getConstructor(EntityPlayer.class, ItemStack.class).newInstance(utils.player(), utils.item(ckeckItem, giveSelector().givedNBT())));
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
