package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.utils.Rand;
import net.minecraft.tileentity.TileEntity;

public class BiblioTableGive extends CheatModule {
    
    private final String[] mcbNames;
    
    public BiblioTableGive() {
        super("BiblioTableGive", Category.MODS, PerformMode.SINGLE);
        mcbNames = new String[] { "book", "map", "journal", "plan", "thaumonomicon", "necronomicon", "lexicon", "print", "notes", "spell", "library", "tome", "encyclopedia" };
    }
    
    @Override public void onPerform(PerformSource src) {
        TileEntity checkTile = utils.tile();
        try {
            if (Class.forName("jds.bibliocraft.tileentities.TileEntityWritingDesk").isInstance(checkTile)) {
                utils.sendPacket("BiblioMCBEdit", utils.rename(giveSelector().givedItem(), mcbNames[Rand.num(mcbNames.length)]), utils.coords(checkTile), 0);
            }
        } catch(Exception e) {}
    }
    
    @Override public String moduleDesc() {
        return lang.get("Item spawn to the Writing Table which the player is looking at (there must be a book in the central slot)", "Выдача предмета в Письменный Стол на который смотрит игрок (в центральном слоте должна находиться книга)");
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("BiblioCraft");
    }

}
