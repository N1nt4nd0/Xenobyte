package forgefuck.team.xenobyte.modules;

import java.util.Map;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.MouseEvent;

public class NCPanelEdit extends CheatModule {
    
    public NCPanelEdit() {
        super("NCPanelEdit", Category.MODS, PerformMode.TOGGLE);
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            TileEntity checkTile = utils.tile();
            try {
                Map blocks = (Map) Class.forName("shedar.mods.ic2.nuclearcontrol.blocks.BlockNuclearControlMain").getDeclaredField("subblocks").get(null);
                for (Object block : blocks.values()) {
                    if (block.getClass().getMethod("getTileEntity").invoke(block).getClass().isInstance(checkTile)) {
                        utils.openGui((GuiScreen) block.getClass().getMethod("getClientGuiElement", TileEntity.class, EntityPlayer.class).invoke(block, checkTile, utils.player()));
                        e.setCanceled(true);
                        break;
                    }
                }
            } catch (Exception ex) {}
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Opener of blocks from IC2NuclearControl in clamed area by right click", "Открывашка блоков из IC2NuclearControl в привате по ПКМ");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("IC2NuclearControl");
    }

}