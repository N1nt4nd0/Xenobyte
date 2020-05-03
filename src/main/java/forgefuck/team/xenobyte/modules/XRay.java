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
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class XRay extends CheatModule {
    
    @Cfg("bindLines") private boolean bindLines;
    @Cfg("lines") private boolean lines;
    @Cfg("radius") private int radius;
    @Cfg("height") private int height;
    private List<IDraw> blocks;
    private double startLines[];
    
    public XRay() {
        super("XRay", Category.WORLD, PerformMode.TOGGLE);
        blocks = new ArrayList<IDraw>();
        startLines = new double[3];
        bindLines = true;
        height = 100;
        radius = 25;
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
                        int meta = world.getBlockMetadata(x, y, z);
                        SelectedBlock sel = xraySelector().getBlock(block, xraySelector().resetMetaFor(block, meta));
                        if (sel != null) {
                            double dX = (double) x;
                            double dY = (double) y;
                            double dZ = (double) z;
                            out.add(() -> {
                                if ((bindLines) || (startLines[0] == 0D && startLines[1] == 0D && startLines[2] == 0D)) {
                                    startLines[0] = RenderManager.instance.viewerPosX;
                                    startLines[1] = RenderManager.instance.viewerPosY;
                                    startLines[2] = RenderManager.instance.viewerPosZ;
                                }
                                if (!sel.hidden) {
                                    if (lines && sel.tracer) {
                                        render.WORLD.drawEspLine(startLines[0], startLines[1], startLines[2], dX + 0.5, dY + 0.5, dZ + 0.5, sel.rf, sel.gf, sel.bf, sel.af, 3);
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
        utils.mc().gameSettings.viewBobbing = true;
        startLines = new double[3];
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
        utils.mc().gameSettings.viewBobbing = !lines || !bindLines || blocks.isEmpty();
        Iterator<IDraw> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw();
        }
    }
    
    @Override public String moduleDesc() {
        return "Отрисовка блоков в мире выбранных в NEI/XRaySelect";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("TracerLine", lines) {
                @Override public void onLeftClick() {
                    buttonValue(lines = !lines);
                }
                @Override public String elementDesc() {
                    return "Отрисовка всех трасер линий";
                }
            },
            new Button("BindLines", bindLines) {
                @Override public void onLeftClick() {
                    buttonValue(bindLines = !bindLines);
                }
                @Override public String elementDesc() {
                    return "Привязка трасер линий к курсору";
                }
            },
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

}