package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import io.netty.buffer.Unpooled;

public class CmdTest extends CheatModule {
    
    public CmdTest() {
        super("CmdTest", Category.MISC, PerformMode.SINGLE);
    }
    
    @Override public String moduleDesc() {
        return lang.get("Checking the command block for operability", "Проверка командного блока на работоспособность");
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.sendPacket("MC|AdvCdm", Unpooled.buffer());
    }

}