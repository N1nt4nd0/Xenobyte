package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;

public class Rocket extends CheatModule {
    
    @Cfg("strength") private int strength;
    public Rocket() {
        super("Rocket", Category.MOVE, PerformMode.SINGLE);
        strength = 2;
    }
    
    @Override public void onPerform(PerformSource src) {
        this.utils.player().addVelocity(0.0d, strength * 0.5d, 0.0d);
    }
    
    @Override public String moduleDesc() {
        return lang.get("Acceleration to the sky", "Ускорение в небо");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
                new ScrollSlider("Strength", strength, 30) {
                    @Override public void onScroll(int dir, boolean withShift) {
                        strength = processSlider(dir, withShift);
                    }
                    @Override public String elementDesc() {
                        return lang.get("Acceleration amount", "Величина ускорения");
                    }
                }
        );
    }

}