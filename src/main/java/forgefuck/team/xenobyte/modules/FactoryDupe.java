package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;

public class FactoryDupe extends CheatModule {
    
    @Cfg("fillSlots") private boolean fillSlots;
    @Cfg("inRadius") private boolean inRadius;
    
    public FactoryDupe() {
        super("FactoryDupe", Category.MODS, PerformMode.SINGLE);
    }
    
    void sendDupePacket(TileEntity tile) {
        int slots = utils.slots(tile);
        if (slots > 0) {
            for (int slot = 0; slot < (fillSlots ? slots: 1); slot ++) {
                for (int count = 0; count < 64; count ++) {
                    utils.sendPacket("MFReloaded", (byte) 0, utils.worldId(), (short) 20, utils.coords(tile), utils.myId(), slot, (byte) 0);
                }
            }
        }
    }
    
    @Override public boolean inGuiPerform() {
        return utils.currentScreen() instanceof GuiContainer;
    }
    
    @Override public void onPerform(PerformSource src) {
        if (inRadius) {
            utils.nearTiles().forEach(this::sendDupePacket);
        } else {
            sendDupePacket(utils.tile());
        }
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("MineFactoryReloaded");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Item dupe under the cursor in the inventories (if there is no item, then clears the inventory)", "Дюп предмета под курсором в инвентарях (если предмета нет, то очищает инвентари)");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("InRadius", inRadius) {
                @Override public void onLeftClick() {
                    buttonValue(inRadius = !inRadius);
                }
                @Override public String elementDesc() {
                    return lang.get("By radius or sight", "По радиусу или взгляду");
                }
            },
            new Button("FillSlots", fillSlots) {
                @Override public void onLeftClick() {
                    buttonValue(fillSlots = !fillSlots);
                }
                @Override public String elementDesc() {
                    return lang.get("Across all inventory slots", "Работа по всем слотам инвентаря");
                }
            }
        );
    }

}