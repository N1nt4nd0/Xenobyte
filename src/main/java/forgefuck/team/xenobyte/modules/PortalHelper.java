package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class PortalHelper extends CheatModule {

    private List<Portal> portals;
    public boolean linesCheck;
    private int[] enterPoint;
    
    public PortalHelper() {
        super("PortalHelper", Category.WORLD, PerformMode.DISABLED_ON_START);
        portals = new ArrayList<Portal>();
        enterPoint = new int[3];
    }

    public List<Portal> makePortals(double posX, double posY, double posZ) {
        List<Portal> portals = new ArrayList<Portal>();
        World world = utils.world();
        for (int l1 = 0; l1 < 4; l1++) {
            Portal portal = new Portal();
            int i2;
            double d1;
            int k2;
            double d2;
            int i3;
            int j3;
            int k3;
            int l3;
            int i4;
            int j4;
            int k4;
            int l4;
            int i5;
            double d3;
            double d4;
            byte b0 = 16;
            double d0 = -1.0D;
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(posY);
            int k = MathHelper.floor_double(posZ);
            int l = i;
            int i1 = j;
            int j1 = k;
            int k1 = 0;
            for (i2 = i - b0; i2 <= i + b0; ++i2) {
                d1 = (double) i2 + 0.5D - posX;
                for (k2 = k - b0; k2 <= k + b0; ++k2) {
                    d2 = (double) k2 + 0.5D - posZ;
                    label274:
                    for (i3 = world.getActualHeight() - 1; i3 >= 0; --i3) {
                        if (world.isAirBlock(i2, i3, k2)) {
                            while (i3 > 0 && world.isAirBlock(i2, i3 - 1, k2)) {
                                --i3;
                            }
                            for (j3 = l1; j3 < l1 + 4; ++j3) {
                                k3 = j3 % 2;
                                l3 = 1 - k3;
                                if (j3 % 4 >= 2) {
                                    k3 = -k3;
                                    l3 = -l3;
                                }
                                for (i4 = 0; i4 < 3; ++i4) {
                                    for (j4 = 0; j4 < 4; ++j4) {
                                        for (k4 = -1; k4 < 4; ++k4) {
                                            l4 = i2 + (j4 - 1) * k3 + i4 * l3;
                                            i5 = i3 + k4;
                                            int j5 = k2 + (j4 - 1) * l3 - i4 * k3;

                                            if (k4 < 0 && !world.getBlock(l4, i5, j5).getMaterial().isSolid() || k4 >= 0 && !world.isAirBlock(l4, i5, j5)) {
                                                continue label274;
                                            }
                                        }
                                    }
                                }
                                d3 = (double) i3 + 0.5D - posY;
                                d4 = d1 * d1 + d3 * d3 + d2 * d2;
                                if (d0 < 0.0D || d4 < d0) {
                                    d0 = d4;
                                    l = i2;
                                    i1 = i3;
                                    j1 = k2;
                                    k1 = j3 % 4;
                                }
                            }
                        }
                    }
                }
            }
            if (d0 < 0.0D) {
                for (i2 = i - b0; i2 <= i + b0; ++i2) {
                    d1 = (double) i2 + 0.5D - posX;

                    for (k2 = k - b0; k2 <= k + b0; ++k2) {
                        d2 = (double) k2 + 0.5D - posZ;
                        label222:
                        for (i3 = world.getActualHeight() - 1; i3 >= 0; --i3) {
                            if (world.isAirBlock(i2, i3, k2)) {
                                while (i3 > 0 && world.isAirBlock(i2, i3 - 1, k2)) {
                                    --i3;
                                }
                                for (j3 = l1; j3 < l1 + 2; ++j3) {
                                    k3 = j3 % 2;
                                    l3 = 1 - k3;
                                    for (i4 = 0; i4 < 4; ++i4) {
                                        for (j4 = -1; j4 < 4; ++j4) {
                                            k4 = i2 + (i4 - 1) * k3;
                                            l4 = i3 + j4;
                                            i5 = k2 + (i4 - 1) * l3;

                                            if (j4 < 0 && !world.getBlock(k4, l4, i5).getMaterial().isSolid() || j4 >= 0 && !world.isAirBlock(k4, l4, i5)) {
                                                continue label222;
                                            }
                                        }
                                    }
                                    d3 = (double) i3 + 0.5D - posY;
                                    d4 = d1 * d1 + d3 * d3 + d2 * d2;
                                    if (d0 < 0.0D || d4 < d0) {
                                        d0 = d4;
                                        l = i2;
                                        i1 = i3;
                                        j1 = k2;
                                        k1 = j3 % 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int k5 = l;
            int j2 = i1;
            k2 = j1;
            int l5 = k1 % 2;
            int l2 = 1 - l5;
            if (k1 % 4 >= 2) {
                l5 = -l5;
                l2 = -l2;
            }
            boolean flag;
            if (d0 < 0.0D) {
                if (i1 < 70) {
                    i1 = 70;
                }
                if (i1 > world.getActualHeight() - 10) {
                    i1 = world.getActualHeight() - 10;
                }
                j2 = i1;
                for (i3 = -1; i3 <= 1; ++i3) {
                    for (j3 = 1; j3 < 3; ++j3) {
                        for (k3 = -1; k3 < 3; ++k3) {
                            l3 = k5 + (j3 - 1) * l5 + i3 * l2;
                            i4 = j2 + k3;
                            j4 = k2 + (j3 - 1) * l2 - i3 * l5;
                            flag = k3 < 0;
                            portal.addBlock(l3, i4, j4);
                        }
                    }
                }
            }
            for (i3 = 0; i3 < 4; ++i3) {
                for (j3 = 0; j3 < 4; ++j3) {
                    for (k3 = -1; k3 < 4; ++k3) {
                        l3 = k5 + (j3 - 1) * l5;
                        i4 = j2 + k3;
                        j4 = k2 + (j3 - 1) * l2;
                        flag = j3 == 0 || j3 == 3 || k3 == -1 || k3 == 3;
                        if (flag)
                        portal.addBlock(l3, i4, j4);
                    }
                }
            }
            portals.add(portal);
        }
        return portals;
    }
    
    
    @Override public void onEnabled() {
        EntityPlayer player = utils.player();
        if (player.dimension == 0) {
            enterPoint[0] = (int) Math.round(player.posX / 8);
            enterPoint[1] = (int) Math.round(player.posY);
            enterPoint[2] = (int) Math.round(player.posZ / 8);
            portals = makePortals(enterPoint[0] * 8, enterPoint[1], enterPoint[2] * 8);
        }
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        if (!portals.isEmpty()) {
            int dim = utils.player().dimension;
            if (dim == 0) {
                for (Portal portal : portals) {
                    for (int[] block : portal.blocks) {
                        render.WORLD.drawOutlinedEspBlock(block[0], block[1], block[2], 1, 0, 1, 0.5F, 0.8F);
                    }
                }
            } else if (dim == -1) {
                render.WORLD.drawOutlinedEspBlock(enterPoint[0], enterPoint[1], enterPoint[2], 1, 0, 1, 1, 1);
                render.WORLD.drawOutlinedEspBlock(enterPoint[0], enterPoint[1] - 1, enterPoint[2], 1, 0, 1, 1, 1);
                render.WORLD.drawEspLine(RenderManager.instance.viewerPosX, RenderManager.instance.viewerPosY, RenderManager.instance.viewerPosZ, enterPoint[0] + 0.5F, enterPoint[1] - 1, enterPoint[2] + 0.5F, 1, 0, 1, 1, 2);
                linesCheck = true;
            }
        }
    }

    @Override public String moduleDesc() {
        return lang.get("Rendering a portal to hell and an entry point in the nether", "Отрисовка портала в ад и точки входа в незере");
    }

    class Portal {

        final List<int[]> blocks;
        
        Portal() {
            blocks = new ArrayList<int[]>();
        }

        void addBlock(int x, int y, int z) {
            blocks.add(new int[] { x, y, z });
        }
    }

}
