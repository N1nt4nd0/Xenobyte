package forgefuck.team.xenobyte.module.MISC;

import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.utils.NetUtils;
import io.netty.buffer.Unpooled;

public class CmdTest extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public String moduleDesc() {
        return "Проверка командного блока на работоспособность";
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.sendPacket("MC|AdvCdm", Unpooled.buffer());
    }

}
