package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.MouseEvent;

public class MalisisDoors extends CheatModule {
    
    @Cfg("inRadius") private boolean inRadius;
    
    public MalisisDoors() {
        super("MalisisDoors", Category.MODS, PerformMode.TOGGLE);
    }
    
    private boolean toggleDoorPacket(TileEntity door) {
        try {
            if (Class.forName("net.malisis.doors.door.tileentity.DoorTileEntity").isInstance(door)) {
                utils.sendPacket("malisisdoors", (byte) 0, utils.coords(door));
                return true;
            }
        } catch(Exception e) {}
        return false;
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            if (inRadius) {
                utils.nearTiles().forEach(this::toggleDoorPacket);
            } else {
                e.setCanceled(toggleDoorPacket(utils.tile()));
            }
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("malisisdoors");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Door opener by right click", "Открывашка дверей по ПКМ");
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
            }
        );
    }

}
