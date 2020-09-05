package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;

public class EIOTeleport extends CheatModule {
    
    @Cfg("intercept") private boolean intercept;
    @Cfg("coords") private List<String> coords;
    @Cfg("onView") private boolean onView;
    @Cfg("items") private boolean items;
    
    public EIOTeleport() {
        super("EIOTeleport", Category.MODS, PerformMode.SINGLE);
        coords = new ArrayList<String>();
        coords.add("0");
        coords.add("0");
        coords.add("0");
        onView = true;
    }
    
    public void doTeleport(int entID, int x, int y, int z) {
        utils.sendPacket("enderio", (byte) 56, x, y, z, 0, false, entID, 3);
    }
    
    public boolean getIntercept() {
        return intercept;
    }
    
    @Override public void onPerform(PerformSource src) {
        int[] mop = utils.mop();
        if (!onView) {
            mop[0] = Integer.parseInt(coords.get(0));
            mop[1] = Integer.parseInt(coords.get(1));
            mop[2] = Integer.parseInt(coords.get(2));
        }
        if (items) {
            utils.nearEntityes().filter(ent -> ent instanceof EntityItem).forEach(ent -> doTeleport(ent.getEntityId(), mop[0], mop[1], mop[2]));
        } else {
            Entity ent = utils.entity();
            doTeleport(ent == null ? -1 : ent.getEntityId(), mop[0], mop[1], mop[2]);
        }
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("EnderIO");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Teleports an object with the specified settings", "Телепортирует объект с заданными настройками");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Coords") {
                @Override public void onLeftClick() {
                    new UserInput(lang.get("Coords", "Координаты"), coords, InputType.COORDS).showFrame();
                }
                @Override public String elementDesc() {
                    return lang.get("Specified x y z", "Заданные x y z");
                }
            },
            new Button("ItemTeleport", items) {
                @Override public void onLeftClick() {
                    buttonValue(items = !items);
                }
                @Override public String elementDesc() {
                    return lang.get("Teleporting items instead of the player", "Телепорт предметов вместо игрока");
                }
            },
            new Button("OnView", onView) {
                @Override public void onLeftClick() {
                    buttonValue(onView = !onView);
                }
                @Override public String elementDesc() {
                    return lang.get("Teleport by sight or coordinates", "Телепорт по взгляду или координатам");
                }
            },
            new Button("Intercept", intercept) {
                @Override public void onLeftClick() {
                    buttonValue(intercept = !intercept);
                }
                @Override public String elementDesc() {
                    return lang.get("Interception of the chat command", "Перехват чат-команды") + " /tp nick x y z";
                }
            }
        );
    }

}