package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.world.WorldSettings;

public class FakeCreative extends CheatModule {
    
    public FakeCreative() {
        super("FakeCreative", Category.PLAYER, PerformMode.SINGLE);
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
        return lang.get("Visual /gamemode 1", "Визуальный /gamemode 1");
    }

}
