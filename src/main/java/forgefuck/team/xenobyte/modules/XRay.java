package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.render.IDraw;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.modules.XRaySelect.SelectedBlock;
import forgefuck.team.xenobyte.utils.TickHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.RotationHelper;

public class XRay extends CheatModule {
    
    @Cfg("bindLines") public boolean bindLines;
    @Cfg("lines") private boolean lines;
    @Cfg("radius") private int radius;
    @Cfg("height") private int height;
    private List<IDraw> blocks;
    public boolean linesCheck;
    private double lx, ly, lz;
    
    public XRay() {
        super("XRay", Category.WORLD, PerformMode.TOGGLE);
        blocks = new ArrayList<IDraw>();
        bindLines = true;
        lines = true;
        height = 100;
        radius = 25;
    }
    
    private boolean ignoreMetaFor(Block block) {
        return RotationHelper.getValidVanillaBlockRotations(block) != ForgeDirection.VALID_DIRECTIONS || block instanceof BlockTorch || block instanceof BlockLever || block instanceof BlockDropper || block instanceof BlockDispenser;
    }
    
    private void updateBlocks() {
        List<IDraw> out = new ArrayList<IDraw>();
        int[] pos = utils.myCoords();
        World world = utils.world();
        new Thread(() -> {
            for (int y = 0; y <= height; y++) {
                for (int x = pos[0] - radius; x <= pos[0] + radius; x++) {
                    for (int z = pos[2] - radius; z <= pos[2] + radius; z++) {
                        Block block = world.getBlock(x, y, z);
                        if (block instanceof BlockAir || block instanceof BlockDirt) {
                            continue;
                        }
                        SelectedBlock sel = xraySelector().getBlock(block, ignoreMetaFor(block) ? 0 : world.getBlockMetadata(x, y, z));
                        if (sel != null) {
                            double dX = (double) x;
                            double dY = (double) y;
                            double dZ = (double) z;
                            out.add(() -> {
                                lx = bindLines ? RenderManager.instance.viewerPosX : lx;
                                ly = bindLines ? RenderManager.instance.viewerPosY : ly;
                                lz = bindLines ? RenderManager.instance.viewerPosZ : lz;
                                if (!sel.hidden) {
                                    if (lines && sel.tracer) {
                                        render.WORLD.drawEspLine(lx, ly, lz, dX + 0.5, dY + 0.5, dZ + 0.5, sel.rf, sel.gf, sel.bf, sel.af, 3);
                                        linesCheck = true;
                                    }
                                    render.WORLD.drawEspBlock(dX, dY, dZ, sel.rf, sel.gf, sel.bf, sel.af, sel.scale);
                                }
                            });
                        }
                    }
                }
            }
            blocks = out;
        }).start();
    }
    
    @Override public void onDisabled() {
        blocks.clear();
    }
    
    @Override public int tickDelay() {
        return TickHelper.ONE_SEC;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            updateBlocks();
        }
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        Iterator<IDraw> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw();
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Rendering blocks in the world selected in NEI/XRaySelect", "Отрисовка блоков в мире выбранных в NEI/XRaySelect");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("TracerLine", lines) {
                @Override public void onLeftClick() {
                    buttonValue(lines = !lines);
                }
                @Override public String elementDesc() {
                    return lang.get("Drawing all tracer lines", "Отрисовка всех трасер линий");
                }
            },
            new Button("BindLines", bindLines) {
                @Override public void onLeftClick() {
                    buttonValue(bindLines = !bindLines);
                }
                @Override public String elementDesc() {
                    return lang.get("Snap line tracers to cursor", "Привязка трасер линий к курсору");
                }
            },
            new ScrollSlider("Radius", radius, 100) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Blocks check radius", "Радиус проверки блоков");
                }
            },
            new ScrollSlider("Height", height, 256) {
                @Override public void onScroll(int dir, boolean withShift) {
                    height = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Blocks check height", "Высота проверки блоков");
                }
            }
        );
    }

}