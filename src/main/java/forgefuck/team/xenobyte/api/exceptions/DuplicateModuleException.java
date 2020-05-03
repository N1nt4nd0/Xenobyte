package forgefuck.team.xenobyte.api.exceptions;

import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.utils.XenoLogger;

public class DuplicateModuleException extends RuntimeException {
    
    public DuplicateModuleException(CheatModule module) {
        super("Найден повторяющийся модуль [" + module + "]");
    }

}
