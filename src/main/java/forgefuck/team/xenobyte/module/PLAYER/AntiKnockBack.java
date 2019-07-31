package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class AntiKnockBack extends CheatModule {
    
    @Override public boolean doReceivePacket(Packet packet) {
        if (packet instanceof S12PacketEntityVelocity) {
            if (utils.isInGame() && utils.myId() == ((S12PacketEntityVelocity)packet).func_149412_c()) {
                return false;
            }
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return "Выключает эфект отбрасывания у игрока";
    }
    
}