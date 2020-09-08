package forgefuck.team.xenobyte.modules;

import java.util.UUID;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class CacheGive extends CheatModule {
    
    public CacheGive() {
        super("CacheGive", Category.MODS, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("cofh.thermalexpansion.block.cache.TileCache").isInstance(checkTile)) {
                Object packet = Class.forName("cofh.core.network.PacketTile").getConstructor(TileEntity.class).newInstance(checkTile);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addString", String.class).invoke(packet, "");
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", byte.class).invoke(packet, (byte) 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addUUID", UUID.class).invoke(packet, UUID.randomUUID());
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addString", String.class).invoke(packet, "");
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", boolean.class).invoke(packet, true);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", byte.class).invoke(packet, (byte) 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", boolean.class).invoke(packet, true);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addInt", int.class).invoke(packet, 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByteArray", byte[].class).invoke(packet, new byte[6]);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", byte.class).invoke(packet, (byte) 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", boolean.class).invoke(packet, false);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addItemStack", ItemStack.class).invoke(packet, giveSelector().givedItem());
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addInt", int.class).invoke(packet, giveSelector().fillAllSlots() ? Integer.MAX_VALUE : giveSelector().itemCount());
                Class.forName("cofh.core.network.PacketHandler").getMethod("sendToServer", Class.forName("cofh.core.network.PacketBase")).invoke(null, packet);
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return lang.get("Issuance of an item to the cache that the player is looking at", "Выдача предмета в Тайник на который смотрит игрок");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("ThermalExpansion");
    }

}
