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
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Esp extends CheatModule {
    
    @Cfg("villagers") private boolean villagers;
    @Cfg("minecarts") private boolean minecarts;
    @Cfg("customnpc") private boolean customnpc;
    @Cfg("bindLines") public boolean bindLines;
    @Cfg("monsters") private boolean monsters;
    @Cfg("players") private boolean players;
    @Cfg("animals") private boolean animals;
    @Cfg("blocks") private boolean blocks;
    @Cfg("lines") private boolean lines;
    @Cfg("radius") private int radius;
    @Cfg("drop") private boolean drop;
    private List<IDraw> objects;
    private double lx, ly, lz;
    public boolean linesCheck;
    
    public Esp() {
        super("Esp", Category.WORLD, PerformMode.TOGGLE);
        objects = new ArrayList<IDraw>();
        bindLines = true;
        players = true;
        blocks = true;
        lines = true;
        radius = 100;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            List<IDraw> out = new ArrayList<IDraw>();
            utils.nearEntityes(radius)
            .forEach(e -> {
                final float[] col = new float[3];
                if (players && utils.isPlayer(e)) {
                    col[0] = 1; col[1] = 0; col[2] = 1;
                } else if (monsters && utils.isMonster(e)) {
                    col[0] = 1; col[1] = 0; col[2] = 0;
                } else if (animals && utils.isAnimal(e)) {
                    col[0] = 0; col[1] = 1; col[2] = 0;
                } else if (drop && utils.isDrop(e)) {
                    col[0] = 1; col[1] = 1; col[2] = 0;
                } else if (villagers && utils.isVillager(e)) {
                    col[0] = 0; col[1] = 1; col[2] = 1;
                } else if (customnpc && utils.isCustom(e)) {
                    col[0] = 0; col[1] = 0; col[2] = 1;
                } else if (minecarts && e instanceof EntityMinecart) {
                    col[0] = 1; col[1] = 1; col[2] = 1;
                } else {
                    return;
                }
                out.add(() -> {
                    lx = bindLines ? RenderManager.instance.viewerPosX : lx;
                    ly = bindLines ? RenderManager.instance.viewerPosY : ly;
                    lz = bindLines ? RenderManager.instance.viewerPosZ : lz;
                    if (lines) {
                        render.WORLD.drawEspLine(lx, ly, lz, e.posX, e.posY, e.posZ, col[0], col[1], col[2], 0.6F, 1.5F);
                        linesCheck = true;
                    }
                    if (blocks) {
                        render.WORLD.drawEspBlock(e.posX - 0.5, e.posY - 0.3, e.posZ - 0.5, col[0], col[1], col[2], 0.4F, 0.5F);
                    }
                });
            });
            objects = out;
        }
    }
    
    @Override public void onDisabled() {
        objects.clear();
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        Iterator<IDraw> iterator = objects.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw();
        }
    }
    
    @Override public String moduleDesc() {
        return "Подсветка заданных объектов в мире";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("EspBlock", blocks) {
                @Override public void onLeftClick() {
                    buttonValue(blocks = !blocks);
                }
                @Override public String elementDesc() {
                    return "Отрисовка блока";
                }
            },
            new Button("TracerLine", lines) {
                @Override public void onLeftClick() {
                    buttonValue(lines = !lines);
                }
                @Override public String elementDesc() {
                    return "Отрисовка трасер линий";
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
            new Button("Monsters", monsters) {
                @Override public void onLeftClick() {
                    buttonValue(monsters = !monsters);
                }
                @Override public String elementDesc() {
                    return "Отображать монстров";
                }
            },
            new Button("Animals", animals) {
                @Override public void onLeftClick() {
                    buttonValue(animals = !animals);
                }
                @Override public String elementDesc() {
                    return "Отображать животных";
                }
            },
            new Button("Villagers", villagers) {
                @Override public void onLeftClick() {
                    buttonValue(villagers = !villagers);
                }
                @Override public String elementDesc() {
                    return "Отображать жителей";
                }
            },
            new Button("CustomNPC", customnpc) {
                @Override public void onLeftClick() {
                    buttonValue(customnpc = !customnpc);
                }
                @Override public String elementDesc() {
                    return "Отображать неписей";
                }
            },
            new Button("Players", players) {
                @Override public void onLeftClick() {
                    buttonValue(players = !players);
                }
                @Override public String elementDesc() {
                    return "Отображать игроков";
                }
            },
            new Button("Drop", drop) {
                @Override public void onLeftClick() {
                    buttonValue(drop = !drop);
                }
                @Override public String elementDesc() {
                    return "Отображать выброшенные предметов";
                }
            },
            new Button("Minecarts", minecarts) {
                @Override public void onLeftClick() {
                    buttonValue(minecarts = !minecarts);
                }
                @Override public String elementDesc() {
                    return "Отображать вагонетки";
                }
            },
            new ScrollSlider("Radius", radius, 200) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Радиус поиска объектов";
                }
            }
        );
    }

}
