package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.render.IDraw;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class FakeBreak extends CheatModule {
    
    private List<BreakedBlock> breakedBlocks;
    @Cfg("drawEsp") private boolean drawEsp;
    
    public FakeBreak() {
        super("FakeBreak", Category.WORLD, PerformMode.TOGGLE);
        breakedBlocks = new ArrayList<BreakedBlock>();
        drawEsp = true;
    }
    
    @Override public void onDisabled() {
        breakedBlocks.forEach(BreakedBlock::setBlock);
        breakedBlocks.clear();
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        if (drawEsp) {
            breakedBlocks.forEach(IDraw::draw);
        }
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 0 && e.buttonstate) {
            int[] mop = utils.mop();
            Block block = utils.block(mop);
            int meta = utils.blockMeta(mop);
            TileEntity checkTile = utils.tile(mop);
            if (block instanceof BlockAir && checkTile != null) {
                return;
            }
            BreakedBlock brBlock = new BreakedBlock(mop, block, meta);
            brBlock.setAir();
            utils.playSound(block.stepSound.getBreakSound(), 1);
            breakedBlocks.add(brBlock);
            e.setCanceled(true);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Fake destruction of the block (you can only go through them with BlinkCam)", "Фейк разрушение блока (пройти через них можно только с BlinkCam)");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("DrawEsp", drawEsp) {
                @Override public void onLeftClick() {
                    buttonValue(drawEsp = !drawEsp);
                }
                @Override public String elementDesc() {
                    return lang.get("Highlighting broken blocks", "Подсветка сломанных блоков");
                }
            }
        );
    }
    
    class BreakedBlock implements IDraw {
        
        Block block;
        int[] pos;
        int meta;
        
        BreakedBlock(int[] pos, Block block, int meta) {
            this.block = block;
            this.meta = meta;
            this.pos = pos;
        }
        
        void setAir() {
            utils.world().setBlockToAir(pos[0], pos[1], pos[2]);
        }
        
        void setBlock() {
            utils.world().setBlock(pos[0], pos[1], pos[2], block, meta, 3);
        }

        @Override public void draw() {
            render.WORLD.drawEspBlock(pos[0], pos[1], pos[2], 1, 0, 0, 0.06F, 1);
        }
        
    }

}