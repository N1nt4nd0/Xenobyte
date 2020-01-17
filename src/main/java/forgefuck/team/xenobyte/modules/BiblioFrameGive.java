package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BiblioFrameGive extends CheatModule {
	
	private int[] coords; 
	
	public BiblioFrameGive() {
		super("BiblioFrameGive", Category.MODS, PerformMode.SINGLE);
	}
	
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("jds.bibliocraft.tileentities.TileEntityMapFrame").isInstance(checkTile)) {
            	coords = new int[] {
            		checkTile.xCoord,
            		checkTile.yCoord,
            		checkTile.zCoord
            	};
            	widgetMessage(String.format("блок карты сохранён по координатам [%s, %s, %s]", coords[0], coords[1], coords[2]), WidgetMode.INFO);
            } else if (coords != null) {
            	TileEntity frameTile = utils.tile(coords);
            	if (frameTile != null) {
                	NBTTagCompound frameTag = new NBTTagCompound();
                	frameTile.writeToNBT(frameTag);
                	if (frameTag.getByte("hasMap") == 1) {
                		ItemStack out = giveSelector().givedItem();
                		utils.item(out, "{Inventory:[]}");
                		utils.sendPacket("BiblioAtlasWPT", false, coords[0], coords[1], coords[2], out);
                	} else {
                		widgetMessage("карта в рамке не найдена!", WidgetMode.FAIL);
                	}
            	} else {
            		widgetMessage("блок рамки не найден в пределах клиента!", WidgetMode.FAIL);
            	}
            } else {
            	widgetMessage("блок рамки не задан! (задать по взгляду и кейбинду)", WidgetMode.FAIL);
            }
        } catch(Exception e) {}
    }
	
    @Override public String moduleDesc() {
        return "Выдача предмета в руку с использованием рамки с картой";
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("BiblioCraft");
    }

}