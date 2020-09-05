package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.swing.CreditsGui;

public class Credits extends CheatModule {
    
    public Credits() {
        super("Credits", Category.MISC, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        new CreditsGui().showFrame();
    }
    
    @Override public String moduleDesc() {
        return lang.get("Product information + links", "Информация о продукте + ссылки");
    }

}
