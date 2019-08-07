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
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Esp extends CheatModule {
    
    @Cfg private boolean monsters, players, animals, drop, blocks, lines, bindLines;
    private List<EspObject> objects;
    private double startLines[];
    private boolean bobbing;
    @Cfg private int radius;
    
    public Esp() {
        super("Esp", Category.WORLD, PerformMode.TOGGLE);
        objects = new ArrayList<EspObject>();
        startLines = new double[3];
    }
    
    @Override public void onPreInit() {
        bindLines = true;
        players = true;
        blocks = true;
        lines = true;
        radius = 100;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            List<EspObject> out = new ArrayList<EspObject>();
            utils.nearEntityes(radius)
            .forEach(e -> {
                if (players && utils.isPlayer(e)) {
                    out.add(new EspObject(e, 1, 0, 1));
                } else if (monsters && utils.isMonster(e)) {
                    out.add(new EspObject(e, 1, 0, 0));
                } else if (animals && utils.isAnimal(e)) {
                    out.add(new EspObject(e, 0, 1, 0));
                } else if (drop && e instanceof EntityItem) {
                    out.add(new EspObject(e, 1, 1, 0));
                }
            });
            objects.clear();
            objects.addAll(out);
            utils.mc().gameSettings.viewBobbing =  !lines || !bindLines || objects.isEmpty();
        }
    }
    
    @Override public void onDisabled() {
        utils.mc().gameSettings.viewBobbing = true;
        startLines = new double[3];
    }
    
    @SubscribeEvent public void worldRender(RenderWorldLastEvent e) {
        objects.forEach(EspObject::draw);
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
                    return "Отрисовка линий";
                }
            },
            new Button("BindLines", bindLines) {
                @Override public void onLeftClick() {
                    buttonValue(bindLines = !bindLines);
                }
                @Override public String elementDesc() {
                    return "Привязка линий к курсору";
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
    
    class EspObject implements IDraw {
        
        final double x, y, z;
        final float r, g, b;
        
        EspObject(Entity ent, float r, float g, float b) {
            this.x = ent.posX;
            this.y = ent.posY;
            this.z = ent.posZ;
            this.r = r;
            this.g = g;
            this.b = b;
        }
        
        @Override public void draw() {
            if ((bindLines) || (startLines[0] == 0D && startLines[1] == 0D && startLines[2] == 0D)) {
                startLines[0] = RenderManager.instance.viewerPosX;
                startLines[1] = RenderManager.instance.viewerPosY;
                startLines[2] = RenderManager.instance.viewerPosZ;
            }
            if (blocks) {
                render.WORLD.drawEspBlock(x - 0.5, y, z - 0.5, r, g, b, 0.4F, 1);
            }
            if (lines) {
                render.WORLD.drawEspLine(startLines[0], startLines[1], startLines[2], x, y, z, r, g, b, 0.6F, 1.5F);
            }
        }
        
    }

}
