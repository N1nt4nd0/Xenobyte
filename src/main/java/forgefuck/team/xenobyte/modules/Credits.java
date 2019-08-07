package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.swing.InfoGui;

public class Credits extends CheatModule {
    
    public Credits() {
        super("Credits", Category.MISC, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        new InfoGui().showFrame();
    }
    
    @Override public String moduleDesc() {
        return "Информация о продукте + ссылки";
    }

}
