package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;

public class Keyabled extends CheatModule {
    
    public Keyabled() {
        super("Keyabled", Category.MISC, PerformMode.TOGGLE);
        cfgState = true;
    }
    
    @Override public boolean provideStateEvents() {
        return false;
    }
    
    @Override public String moduleDesc() {
        return "Отображение списка активных и забинженых модулей";
    }

}