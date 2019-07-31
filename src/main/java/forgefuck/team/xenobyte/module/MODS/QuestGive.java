package forgefuck.team.xenobyte.module.MODS;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class QuestGive extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("betterquesting.blocks.TileSubmitStation").isInstance(checkTile)) {
                NBTTagCompound stackTag = new NBTTagCompound();
                NBTTagCompound tileTag = new NBTTagCompound();
                NBTTagCompound root = new NBTTagCompound();
                giveSelector().givedItem().writeToNBT(stackTag);
                checkTile.writeToNBT(tileTag);
                if (giveSelector().fillAllSlots()) {
                    tileTag.setTag("ouput", stackTag);
                }
                tileTag.setTag("input", stackTag);
                root.setTag("tile", tileTag);
                Class.forName("betterquesting.network.PacketSender").getMethod("sendToServer", Class.forName("betterquesting.api.network.QuestingPacket")).invoke(Class.forName("betterquesting.network.PacketSender").getField("INSTANCE").get(null), Class.forName("betterquesting.api.network.QuestingPacket").getConstructor(ResourceLocation.class, NBTTagCompound.class).newInstance(new ResourceLocation("betterquesting:edit_station"), root));
            }
        } catch (Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return "Выдача предмета в SubmitStation (OSS) на которую смотрит игрок";
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("betterquesting");
    }

}