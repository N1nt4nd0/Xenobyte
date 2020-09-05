package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.utils.Rand;
import net.minecraft.tileentity.TileEntity;

public class MachineChaos extends CheatModule {
    
    public MachineChaos() {
        super("MachineChaos", Category.MODS, PerformMode.SINGLE);
    }
    
    private void sendBatthurt(TileEntity tile) {
        try {
            if (Class.forName("crazypants.enderio.machine.IRedstoneModeControlable").isInstance(tile)) {
                utils.sendPacket("enderio", (byte) 96, utils.coords(tile), (short) Rand.num(4));
            }
            if (Class.forName("crazypants.enderio.machine.IIoConfigurable").isInstance(tile)) {
                for (int side = 0; side <= 5; side++) {
                    utils.sendPacket("enderio", (byte) 1, utils.coords(tile), (short) Rand.num(5), (short) side);
                }
            }
            if (Class.forName("crazypants.enderio.conduit.TileConduitBundle").isInstance(tile)) {
                Class enumTypeClass = Class.forName("crazypants.enderio.conduit.packet.ConTypeEnum");
                Object[] conTypes = (Object[]) enumTypeClass.getMethod("values").invoke(null);
                for (Object type : conTypes) {
                    if ((boolean) Class.forName("crazypants.enderio.conduit.TileConduitBundle").getMethod("hasType", Class.class).invoke(tile, enumTypeClass.getMethod("getBaseType").invoke(type))) {
                        for (int side = 0; side <= 5; side++) {
                            short typeId = ((Integer) enumTypeClass.getMethod("ordinal").invoke(type)).shortValue();
                            utils.sendPacket("enderio", (byte) 76, utils.coords(tile), (short) typeId, (short) side, (short) Rand.num(4));
                        }
                    }
                }
            }
        } catch(Exception e) {}
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.nearTiles().forEach(this::sendBatthurt);
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("EnderIO");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Randomising configurations of EnderIO mechanisms and pipes in radius", "Рандом конфигураций механизмов и труб EnderIO в радиусе");
    }

}
