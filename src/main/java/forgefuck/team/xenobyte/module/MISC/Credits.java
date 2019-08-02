package forgefuck.team.xenobyte.module.MISC;

import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.swing.InfoGui;

public class Credits extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        new InfoGui().showFrame();
    }
    
    @Override public String moduleDesc() {
        return "Информация о продукте + ссылки";
    }

}
