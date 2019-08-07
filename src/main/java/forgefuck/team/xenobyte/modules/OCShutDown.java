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

public class OCShutDown extends CheatModule {
    
    @Cfg("inRadius") private boolean inRadius;
    private boolean state;
    
    public OCShutDown() {
        super("OCShutDown", Category.MODS, PerformMode.TOGGLE);
    }
    
    private boolean shutDownPacket(TileEntity comp) {
        try {
            if (Class.forName("li.cil.oc.common.tileentity.Case").isInstance(comp)) {
                Class.forName("li.cil.oc.client.PacketSender$").getMethod("sendComputerPower", Class.forName("li.cil.oc.common.tileentity.traits.Computer"), boolean.class).invoke(Class.forName("li.cil.oc.client.PacketSender$").getField("MODULE$").get(null), comp, state);
                return true;
            } else if (Class.forName("li.cil.oc.common.tileentity.Rack").isInstance(comp)) {
                for (int rack = 0; rack <= 3; rack ++) {
                    Class.forName("li.cil.oc.client.PacketSender$").getMethod("sendServerPower", Class.forName("li.cil.oc.common.tileentity.Rack"), int.class, boolean.class).invoke(Class.forName("li.cil.oc.client.PacketSender$").getField("MODULE$").get(null), comp, rack, state);
                }
                return true;
            }
        } catch(Exception e) {}
        return false;
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            state = !state;
            if (inRadius) {
                utils.nearTiles().forEach(this::shutDownPacket);
            } else {
                e.setCanceled(shutDownPacket(utils.tile()));
            }
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("OpenComputers");
    }
    
    @Override public String moduleDesc() {
        return "Перезагрузка компов из OpenComputers по ПКМ";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("InRadius", inRadius) {
                @Override public void onLeftClick() {
                    buttonValue(inRadius = !inRadius);
                }
                @Override public String elementDesc() {
                    return "По радиусу или взгляду";
                }
            }
        );
    }

}