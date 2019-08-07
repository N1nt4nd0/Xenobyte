package forgefuck.team.xenobyte.api.gui;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.module.CheatModule;

public class WidgetMessage {
    
    private CheatModule module;
    private WidgetMode mode;
    private String text;
    
    public WidgetMessage(CheatModule module, String text) {
        this(module, text, WidgetMode.INFO);
    }
    
    public WidgetMessage(String text, WidgetMode mode) {
        this(null, text, mode);
    }
    
    public WidgetMessage(CheatModule module, String text, WidgetMode mode) {
        this.text = module != null ? module + " " + text : text;
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
        return text;
    }
    
    public WidgetMode getMode() {
        return mode;
    }
    
    @Override public String toString() {
        return Xeno.utils.nullHelper(getMessage());
    } 

}
