package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FastBreak extends CheatModule {

	public FastBreak() {
		super("FastBreak", Category.WORLD, PerformMode.TOGGLE);
	}
	
    @SubscribeEvent public void breakSpeed(PlayerEvent.BreakSpeed e) {
        utils.sendPacket(new C07PacketPlayerDigging(2, e.x, e.y, e.z, 0));
    }
    
	@Override public String moduleDesc() {
		return "Быстрое ломание блоков";
	}

}