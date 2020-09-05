package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.utils.TickHelper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends CheatModule {
    
    @Cfg("withPotion") private boolean withPotion;
    
    public FullBright() {
        super("FullBright", Category.WORLD, PerformMode.TOGGLE);
    }
    
    @Override public void onDisabled() {
        utils.reloadWorld();
        utils.player().removePotionEffectClient(Potion.nightVision.getId());
    }
    
    @Override public int tickDelay() {
        return TickHelper.ONE_SEC;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            float[] bright = utils.world().provider.lightBrightnessTable;
            for (int i = 0; i < bright.length; ++i) {
                bright[i] = 1.0f;
            }
            if (withPotion) {
                utils.player().addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Integer.MAX_VALUE));
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Let there be light!", "Да будет свет!");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("WithPotion", withPotion) {
                @Override public void onLeftClick() {
                    buttonValue(withPotion = !withPotion);
                }
                @Override public String elementDesc() {
                    return lang.get("Fallback - using a potion", "Запасной вариант - с применением зелья");
                }
            }
        );
    }

}
