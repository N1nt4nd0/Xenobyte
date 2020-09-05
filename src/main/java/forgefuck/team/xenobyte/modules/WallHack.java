package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.relauncher.ReflectionHelper;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;

public class WallHack extends CheatModule {
    
    private WorldRenderer[] chunkCache;
    
    public WallHack() {
        super("WallHack", Category.WORLD, PerformMode.TOGGLE);
        chunkCache = new WorldRenderer[4];
    }
    
    private void renderChunks(boolean isVisible) {
        WorldRenderer[] renderChunks = ReflectionHelper.getPrivateValue(RenderGlobal.class, utils.mc().renderGlobal, 9);
        if (renderChunks.length > 4 && renderChunks[3] != null) {
            for (WorldRenderer ch : chunkCache) {
                if (ch != null) {
                    ch.isVisible = true;
                }
            }
            chunkCache[0] = renderChunks[0];
            chunkCache[1] = renderChunks[1];
            chunkCache[2] = renderChunks[2];
            chunkCache[3] = renderChunks[3];
            renderChunks[0].isVisible = isVisible;
            renderChunks[1].isVisible = true;
            renderChunks[2].isVisible = true;
            renderChunks[3].isVisible = true;
            for (int i = 1; i <= 3; i++) {
                if (renderChunks[i].posX == renderChunks[0].posX && renderChunks[i].posZ == renderChunks[0].posZ && renderChunks[i].posY < renderChunks[0].posY) {
                    renderChunks[i].isVisible = isVisible;
                }
            }
        }
    }
    
    @Override public void onDisabled() {
        renderChunks(true);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.mc().gameSettings.advancedOpengl = false;
            renderChunks(false);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Translucent chunk in which the player is", "Просвечивание чанка в котором находится игрок");
    }

}