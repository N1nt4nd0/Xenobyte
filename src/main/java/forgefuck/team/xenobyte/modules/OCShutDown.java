package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.tileentity.TileEntity;

public class OCShutDown extends CheatModule {
    
    @Cfg("inRadius") private boolean inRadius;
    private boolean state;
    
    public OCShutDown() {
        super("OCShutDown", Category.MODS, PerformMode.SINGLE);
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
    
    @Override public void onPerform(PerformSource src) {
        state = !state;
        if (inRadius) {
            utils.nearTiles().forEach(this::shutDownPacket);
        } else {
            shutDownPacket(utils.tile());
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("OpenComputers");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Restarting PC's from OpenComputers by keybind", "Перезагрузка компов из OpenComputers по кейбинду");
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