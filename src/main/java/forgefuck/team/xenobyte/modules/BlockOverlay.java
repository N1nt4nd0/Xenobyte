package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BlockOverlay extends CheatModule {
    
    public BlockOverlay() {
        super("BlockOverlay", Category.WORLD, PerformMode.TOGGLE);
    }

    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        MovingObjectPosition position = utils.mc().objectMouseOver;
        if(position != null && utils.world() != null) {
            Block block = utils.world().getBlock(position.blockX, position.blockY, position.blockZ);
            if (block != null && block.getMaterial() != Material.air) {
                utils.mc().entityRenderer.disableLightmap(0.0f);
                int x = position.blockX, y = position.blockY, z = position.blockZ;
                render.WORLD.drawOutlinedEspBlock(x, y, z, 0.0f, 0.0f, 0.0f, 0.4F, 1);
                render.WORLD.drawEspBlock(x, y, z, 0.0f, 0.4f, 0.0f, 0.4F, 1);
                utils.mc().entityRenderer.enableLightmap(0.0f);
                return;
            }
        }
        MovingObjectPosition mop = utils.mc().renderViewEntity.rayTrace(200, 1.0F);
        if(mop != null) {
            int blockHitSide = mop.sideHit;
            int x = mop.blockX, y = mop.blockY, z = mop.blockZ;
            if(utils.world().getBlock(x, y, z).getMaterial() != Material.air) {
                render.WORLD.drawOutlinedEspBlock(x, y, z, 0.0f, 0.0f, 0.0f, 0.4F, 1);
                render.WORLD.drawEspBlock(x, y, z, 0.4f, 0.0f, 0.0f, 0.4F, 1);
                utils.mc().entityRenderer.enableLightmap(0.0f);
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Shows whether you can interact with the block", "Показывает можно ли взаимодействовать с блоком");
    }

}
