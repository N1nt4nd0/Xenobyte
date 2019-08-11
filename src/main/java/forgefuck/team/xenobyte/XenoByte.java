package forgefuck.team.xenobyte;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.gui.swing.ModidChanger;
import forgefuck.team.xenobyte.handlers.ModuleHandler;
import forgefuck.team.xenobyte.handlers.PacketHandler;
import net.minecraft.client.Minecraft;

@Mod(modid = Xeno.mod_id, name = Xeno.mod_name, version = Xeno.mod_version)

public class XenoByte {
    
    @EventHandler public void init(FMLInitializationEvent e) {
        if (e == null) {
            starter(null);
        }
    }
    
    @EventHandler public void preInit(FMLPreInitializationEvent e) {
        if (e != null) {
            new ModidChanger().showFrame(true);
        }
    }
    
    @EventHandler public void starter(FMLLoadCompleteEvent e) {
        new ModuleHandler();
    }
    
}