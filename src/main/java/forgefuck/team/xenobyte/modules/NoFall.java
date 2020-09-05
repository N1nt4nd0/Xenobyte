package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class NoFall extends CheatModule {
    
    public NoFall() {
        super("NoFall", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.player().fallDistance > 2) {
            utils.sendPacket(new C04PacketPlayerPosition(utils.player().motionX, -999, -999, utils.player().motionZ, !utils.player().onGround));
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Disables fall damage", "Отключает урон от падения");
    }

}
