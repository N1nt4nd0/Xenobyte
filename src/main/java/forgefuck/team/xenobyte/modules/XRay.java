package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.render.IDraw;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.modules.XRaySelect.SelectedBlock;
import forgefuck.team.xenobyte.utils.TickHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class XRay extends CheatModule {
    
    @Cfg private int radius, height;
    private List<XRayBlock> blocks;
    private XRaySelect selector;
    
    public XRay() {
        super("XRay", Category.WORLD, PerformMode.TOGGLE);
        blocks = new CopyOnWriteArrayList<XRayBlock>();
    }
    
    private void updateBlocks() {
        List<XRayBlock> out = new ArrayList<XRayBlock>();
        int[] pos = utils.myCoords();
        World world = utils.world();
        new Thread(() -> {
            for (int y = 0; y <= height; y++) {
                for (int x = pos[0] - radius; x <= pos[0] + radius; x++) {
                    for (int z = pos[2] - radius; z <= pos[2] + radius; z++) {
                        Block block = world.getBlock(x, y, z);
                        if (block instanceof BlockAir || block instanceof BlockStone || block instanceof BlockDirt) {
                            continue;
                        }
                        int meta = world.getBlockMetadata(x, y, z);
                        SelectedBlock selected = selector.getBlock(block, selector.resetMetaFor(block, meta));
                        if (selected != null) {
                            out.add(new XRayBlock(selected, x, y, z));
                        }
                    }
                }
            }
            blocks.clear();
            blocks.addAll(out);
        }).start();
    }

    @Override public void onPreInit() {
        selector = (XRaySelect) moduleHandler().getModuleByClass(XRaySelect.class);
        height = 100;
        radius = 25;
    }
    
    @Override public int tickDelay() {
        return TickHelper.oneSecond();
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            updateBlocks();
        }
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        blocks.forEach(XRayBlock::draw);
    }
    
    @Override public String moduleDesc() {
        return "Отрисовка блоков в мире выбранных в NEI/XRaySelect";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Radius", radius, 100) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Радиус проверки блоков";
                }
            },
            new ScrollSlider("Height", height, 256) {
                @Override public void onScroll(int dir, boolean withShift) {
                    height = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Высота проверки блоков";
                }
            }
        );
    }
    
    class XRayBlock implements IDraw {
        
        final SelectedBlock sel;
        final int x, y, z;
        
        XRayBlock(SelectedBlock sel, int x, int y, int z) {
            this.sel = sel;
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        @Override public void draw() {
            render.WORLD.drawEspBlock(x, y, z, sel.rf, sel.gf, sel.bf, sel.af, sel.scale);
        }
        
        @Override public String toString() {
            return sel + "[" + x + ", " + y + ", " + z + "]";
        }
        
    }

}