package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.MouseEvent;

public class BiblioSignEdit extends CheatModule {
    
    public BiblioSignEdit() {
        super("BiblioSignEdit", Category.MODS, PerformMode.TOGGLE);
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            TileEntity checkTile = utils.tile();
            try {
                if (Class.forName("jds.bibliocraft.tileentities.TileEntityFancySign").isInstance(checkTile)) {
                    utils.openGui((GuiScreen) Class.forName("jds.bibliocraft.gui.GuiFancySign").getConstructor(InventoryPlayer.class, Class.forName("jds.bibliocraft.tileentities.TileEntityFancySign")).newInstance(utils.player().inventory, checkTile));
                    e.setCanceled(true);
                }
            } catch(Exception ex) {}
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("BiblioFancySign opener in claimed area on right click", "Открывашка BiblioFancySign в привате по ПКМ");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("BiblioCraft");
    }

}