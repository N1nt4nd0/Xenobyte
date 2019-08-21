package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import forgefuck.team.xenobyte.utils.TickHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class XenAura extends CheatModule {
    
    @Cfg("friendsList") private List<String> friendsList;
    @Cfg("ignoreWalls") private boolean ignoreWalls;
    @Cfg("criticals") private boolean criticals;
    @Cfg("handshake") private boolean handshake;
    @Cfg("monsters") private boolean monsters;
    @Cfg("animals") private boolean animals;
    @Cfg("players") private boolean players;
    @Cfg("pointed") private boolean pointed;
    @Cfg("radius") private int radius;
    @Cfg("radius") private int delay;
    
    public XenAura() {
        super("XenAura", Category.PLAYER, PerformMode.TOGGLE);
        friendsList = new ArrayList<String>();
        ignoreWalls = true;
        criticals = true;
        players = true;
        radius = 6;
    }
    
    private void attack(Entity e) {
        EntityPlayer pl = utils.player();
        if (criticals && pl.isCollidedVertically) {
            utils.sendPacket(new C04PacketPlayerPosition(pl.posX, pl.posY + 0.0624D, pl.posY + 1.0D, pl.posZ, true));
            utils.sendPacket(new C04PacketPlayerPosition(pl.posX, pl.posY, pl.posY + 1.0D, pl.posZ, false));
            utils.sendPacket(new C04PacketPlayerPosition(pl.posX, pl.posY + 0.000111D, pl.posY + 1.0D, pl.posZ, false));
            utils.sendPacket(new C04PacketPlayerPosition(pl.posX, pl.posY, pl.posY + 1.0D, pl.posZ, false));
        }
        utils.sendPacket(new C02PacketUseEntity(e, Action.ATTACK));
        if (handshake) {
            pl.swingItem();
        }
    }
    
    @Override public int tickDelay() {
        return delay;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.nearEntityes(radius)
            .filter(e -> e instanceof EntityLivingBase && !e.isDead)
            .filter(e -> (players && utils.isPlayer(e) && !friendsList.contains(e.getCommandSenderName())) || (monsters && utils.isMonster(e)) || (animals && utils.isAnimal(e)))
            .filter(e -> ignoreWalls ? true : utils.player().canEntityBeSeen(e))
            .filter(e -> pointed ? e == utils.pointedEntity() : true)
            .forEach(this::attack);
        }
    }
    
    @Override public String moduleDesc() {
        return "Нанесение ударов по сущностям в радиусе";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Radius", radius, 6) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Радиус киллауры";
                }
            },
            new ScrollSlider("Delay", delay, 0, TickHelper.ONE_SEC) {
                @Override public void onScroll(int dir, boolean withShift) {
                    delay = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Задержка нанесения ударов";
                }
            },
            new Button("Friends") {
                @Override public void onLeftClick() {
                    new UserInput("Друзья", friendsList, InputType.CUSTOM).showFrame();
                }
                @Override public String elementDesc() {
                    return "Вайтлист друзей по никнеймам";
                }
            },
            new Button("HandShake", handshake) {
                @Override public void onLeftClick() {
                    buttonValue(handshake = !handshake);
                }
                @Override public String elementDesc() {
                    return "Взмах рукой при ударе";
                }
            },
            new Button("IgnoreWalls", ignoreWalls) {
                @Override public void onLeftClick() {
                    buttonValue(ignoreWalls = !ignoreWalls);
                }
                @Override public String elementDesc() {
                    return "Игнорирование преград";
                }
            },
            new Button("Pointed", pointed) {
                @Override public void onLeftClick() {
                    buttonValue(pointed = !pointed);
                }
                @Override public String elementDesc() {
                    return "Нанесение ударов только по взгляду на объект";
                }
            },
            new Button("Criticals", criticals) {
                @Override public void onLeftClick() {
                    buttonValue(criticals = !criticals);
                }
                @Override public String elementDesc() {
                    return "Наносить удары с критами";
                }
            },
            new Button("Players", players) {
                @Override public void onLeftClick() {
                    buttonValue(players = !players);
                }
                @Override public String elementDesc() {
                    return "Урон по игрокам";
                }
            },
            new Button("Animals", animals) {
                @Override public void onLeftClick() {
                    buttonValue(animals = !animals);
                }
                @Override public String elementDesc() {
                    return "Урон по животным";
                }
            },
            new Button("Monsters", monsters) {
                @Override public void onLeftClick() {
                    buttonValue(monsters = !monsters);
                }
                @Override public String elementDesc() {
                    return "Урон по монстрам";
                }
            }
        );
    }
    
}