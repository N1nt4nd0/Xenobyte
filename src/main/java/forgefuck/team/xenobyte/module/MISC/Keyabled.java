package forgefuck.team.xenobyte.module.MISC;

import forgefuck.team.xenobyte.api.module.CheatModule;

public class Keyabled extends CheatModule {

    @Override public void onPreInit() {
        moduleHandler().enable(this);
    }
    
    @Override public boolean provideStateEvents() {
        return false;
    }
    
    @Override public String moduleDesc() {
        return "Отображение списка активных и забинженых модулей";
    }

}