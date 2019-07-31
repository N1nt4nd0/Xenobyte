package forgefuck.team.xenobyte.module.PLAYER;

import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.world.WorldSettings;

public class FakeCreative extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        if (utils.isInCreative()) {
            utils.mc().playerController.setGameType(WorldSettings.GameType.SURVIVAL);
            WorldSettings.GameType.SURVIVAL.configurePlayerCapabilities(utils.player().capabilities);
            utils.player().sendPlayerAbilities();
        } else {
            utils.mc().playerController.setGameType(WorldSettings.GameType.CREATIVE);
            WorldSettings.GameType.CREATIVE.configurePlayerCapabilities(utils.player().capabilities);
            utils.player().sendPlayerAbilities();
        }
    }
    
    @Override public String moduleDesc() {
        return "Визуальный Gamemode 1";
    }

}
