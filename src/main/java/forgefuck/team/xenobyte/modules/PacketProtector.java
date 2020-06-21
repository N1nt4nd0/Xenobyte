package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class PacketProtector extends CheatModule {
    
    private EIOTeleport teleporter;
    private RadioHack radiohack;
    
    public PacketProtector() {
        super("PacketProtector", Category.NONE, PerformMode.ENABLED_ON_START);
    }
    
    @Override public boolean isWidgetable() {
        return false;
    }
    
    @Override public void onPostInit() {
        teleporter = (EIOTeleport) moduleHandler().getModuleByClass(EIOTeleport.class);
        radiohack = (RadioHack) moduleHandler().getModuleByClass(RadioHack.class);
    }
    
    @Override public boolean doSendPacket(Packet packet) {
        if (teleporter.getIntercept()) {
            if (packet instanceof C01PacketChatMessage) {
                String[] args = ((C01PacketChatMessage) packet).func_149439_c().replaceAll(" +", " ").split(" ");
                if ("/tp".equals(args[0]) && args.length == 5) {
                    teleporter.doTeleport(-1, Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]));
                    return false;
                } 
            }
        } 
        return true;
    }
    
    @Override public boolean doReceivePacket(Packet packet) {
        if (radiohack.getKick()) {
            if (packet instanceof FMLProxyPacket) {
                if ("DragonsRadioMod".equals(((FMLProxyPacket) packet).channel())) {
                    return false;
                }
            }
        } 
        return true;
    }

}
