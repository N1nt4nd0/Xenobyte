package forgefuck.team.xenobyte.api.gui;

import forgefuck.team.xenobyte.api.module.CheatModule;

public class WidgetMessage {
    
    private CheatModule module;
    private WidgetMode mode;
    private Object mess;
    
    public WidgetMessage(CheatModule module, Object mess) {
        this(module, mess, WidgetMode.INFO);
    }
    
    public WidgetMessage(Object mess, WidgetMode mode) {
        this(null, mess, mode);
    }
    
    public WidgetMessage(CheatModule module, Object mess, WidgetMode mode) {
        this.mess = module != null ? module + " " + mess : mess;
        this.module = module;
        this.mode = mode;
    }
    
    public CheatModule getModule() {
        return module;
    }
    
    public boolean hasModule() {
        return module != null;
    }

    public String getMessage() {
        return new String() + mess;
    }
    
    public WidgetMode getMode() {
        return mode;
    }
    
    @Override public String toString() {
        return getMessage();
    } 

}
