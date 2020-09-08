package forgefuck.team.xenobyte.modules;

import org.lwjgl.input.Keyboard;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.XenoGuiScreen;

public class XenoGui extends CheatModule {
    
    public XenoGui() {
        super("XenoGui", Category.NONE, PerformMode.SINGLE);
        setKeyBind(Keyboard.KEY_B);
    }
    
    @Override public void onPerform(PerformSource type) {
        utils.openGui(new XenoGuiScreen(moduleHandler()), true);
    }
    
    @Override public boolean allowStateMessages() {
        return false;
    }

}