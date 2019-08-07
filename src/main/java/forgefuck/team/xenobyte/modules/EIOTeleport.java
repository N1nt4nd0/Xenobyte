package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.client.event.MouseEvent;

public class EIOTeleport extends CheatModule {
    
    @Cfg private boolean intercept, onView, items;
    @Cfg private List<String> coords;
    
    public EIOTeleport() {
        super("EIOTeleport", Category.MODS, PerformMode.TOGGLE);
        coords = new ArrayList<String>();
    }
    
    private void doTeleport(int entID, int x, int y, int z) {
        utils.sendPacket("enderio", (byte) 56, x, y, z, 0, false, entID, 3);
    }
    
    @Override public void onPreInit() {
        coords.add("0");
        coords.add("0");
        coords.add("0");
        onView = true;
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent ev) {
        if (ev.button == 1 && ev.buttonstate) {
            int[] mop = utils.mop();
            if (!onView) {
                mop[0] = Integer.parseInt(coords.get(0));
                mop[1] = Integer.parseInt(coords.get(1));
                mop[2] = Integer.parseInt(coords.get(2));
            }
            if (items) {
                utils.nearEntityes().filter(e -> e instanceof EntityItem).forEach(e -> doTeleport(e.getEntityId(), mop[0], mop[1], mop[2]));
            } else {
                Entity ent = utils.entity();
                doTeleport(ent == null ? -1 : ent.getEntityId(), mop[0], mop[1], mop[2]);
            }
        }
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("EnderIO");
    }
    
    @Override public boolean doSendPacket(Packet packet) {
        if (intercept) {
            if (packet instanceof C01PacketChatMessage) {
                String[] args = ((C01PacketChatMessage) packet).func_149439_c().replaceAll(" +", " ").split(" ");
                if ("/tp".equals(args[0]) && args.length == 5) {
                    doTeleport(-1, Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]));
                    return false;
                } 
            }
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return "Телепортирует объект с заданными настройками по ПКМ";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Coords") {
                @Override public void onLeftClick() {
                    new UserInput("Координаты", coords, InputType.COORDS).showFrame();
                }
                @Override public String elementDesc() {
                    return "Заданные x y z";
                }
            },
            new Button("ItemTeleport", items) {
                @Override public void onLeftClick() {
                    buttonValue(items = !items);
                }
                @Override public String elementDesc() {
                    return "Телепорт предметов вместо игрока";
                }
            },
            new Button("OnView", onView) {
                @Override public void onLeftClick() {
                    buttonValue(onView = !onView);
                }
                @Override public String elementDesc() {
                    return "Телепорт по взгляду или координатам";
                }
            },
            new Button("Intercept", intercept) {
                @Override public void onLeftClick() {
                    buttonValue(intercept = !intercept);
                }
                @Override public String elementDesc() {
                    return "Перехват чат-команды /tp nick x y z";
                }
            }
        );
    }

}