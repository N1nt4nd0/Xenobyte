package forgefuck.team.xenobyte.api.module;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.WidgetMessage;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.handlers.ModuleHandler;
import forgefuck.team.xenobyte.modules.GiveSelect;
import forgefuck.team.xenobyte.modules.XRaySelect;

public abstract class CheatModule extends ModuleAbility implements Xeno {
    
    private boolean forgeEvents, ticksStarted;
    @Cfg("cfgState") public boolean cfgState;
    private GiveSelect giveSelector;
    private XRaySelect xraySelector;
    private final Category category;
    private final PerformMode mode;
    private ModuleHandler handler;
    @Cfg("key") private int key;
    protected final Logger log;
    private final String name;
    private int counter;
    
    public CheatModule(String name, Category category, PerformMode mode) {
        this.forgeEvents = Stream.of(getClass().getDeclaredMethods()).filter(f -> f.isAnnotationPresent(SubscribeEvent.class)).findFirst().isPresent();
        this.log = LogManager.getLogger(this);
        this.category = category;
        this.mode = mode;
        this.name = name;
        setLastCounter();
    }
    
    public String getName() {
        return name;
    }
    
    public PerformMode getMode() {
        return mode;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public boolean hasCategory() {
        return category != Category.NONE;
    }
    
    public void setKeyBind(int key) {
        this.key = key;
    }
    
    public void resetKeyBind() {
        setKeyBind(Keyboard.KEY_NONE);
    }
    
    public int getKeyBind() {
        return key;
    }
    
    public boolean provideForgeEvents() {
        return forgeEvents;
    }
    
    public boolean hasKeyBind() {
        return getKeyBind() > Keyboard.KEY_NONE;
    }
    
    public String getKeyName() {
        return hasKeyBind() ? Keyboard.getKeyName(getKeyBind()) : null;
    }
    
    public void setLastCounter() {
        counter = tickDelay();
    }
    
    public void resetCounter() {
        counter = 0;
    }
    
    public void handlerInit(ModuleHandler handler) {
        this.handler = handler;
    	xraySelector = (XRaySelect) moduleHandler().getModuleByClass(XRaySelect.class);
    	giveSelector = (GiveSelect) moduleHandler().getModuleByClass(GiveSelect.class);
        onHandlerInit();
    }
    
    public void handleTick() {
        if (!ticksStarted) {
            ticksStarted = true;
            onTicksStart();
        }
        if (counter >= tickDelay()) {
            resetCounter();
            onTick(utils.isInGame());
        }
        counter ++;
    }
    
    protected ModuleHandler moduleHandler() {
        return handler;
    }
    
    protected void widgetMessage(Object mess, WidgetMode mode) {
        handler.widgets().widgetMessage(new WidgetMessage(this, mess, mode));
    }
    
    protected void infoMessage(Object mess, WidgetMode mode) {
        handler.widgets().infoMessage(new WidgetMessage(this, mess, mode));
    }
    
    protected void hideInfoMessage() {
        handler.widgets().hideInfoMessage(this);
    }
    
    protected GiveSelect giveSelector() {
        return giveSelector;
    }
    
    protected XRaySelect xraySelector() {
    	return xraySelector;
    }
    
    @Override public boolean equals(Object o) {
    	if (o instanceof CheatModule) {
    		return ((CheatModule) o).getName().equals(getName());
    	}
    	return super.equals(o);
    }
    
    @Override public String toString() {
        return name;
    }

}