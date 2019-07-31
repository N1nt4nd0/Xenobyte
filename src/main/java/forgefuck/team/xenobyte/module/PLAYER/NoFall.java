package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class NoFall extends CheatModule {
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.player().fallDistance > 2) {
            utils.sendPacket(new C04PacketPlayerPosition(utils.player().motionX, -999, -999, utils.player().motionZ, !utils.player().onGround));
        }
    }
    
    @Override public String moduleDesc() {
        return "Отключает урон от падения";
    }

}
