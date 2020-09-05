package forgefuck.team.xenobyte.api.exceptions;

import forgefuck.team.xenobyte.api.module.CheatModule;

public class DuplicateModuleException extends RuntimeException {
    
    public DuplicateModuleException(CheatModule module) {
        super("Duplicate module found [" + module + "]");
    }

}
