package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.tileentity.TileEntity;

public class EIOXpGrab extends CheatModule {

    public EIOXpGrab() {
        super("EIOXpGrab", Category.MODS, PerformMode.SINGLE);
    }
    
    private void sendGrab(TileEntity tile) {
        try {
            if (Class.forName("crazypants.enderio.machine.obelisk.xp.TileExperienceObelisk").isInstance(tile)) {
                utils.sendPacket("enderio", (byte) 69, utils.coords(tile), Short.MAX_VALUE);
            }
        } catch(Exception e) {}
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.nearTiles().forEach(this::sendGrab);
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("EnderIO");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Drains experience from all experience obelisks in the radius", "Высасывает опыт из всех обелисков опыта в радиусе");
    }

}