package forgefuck.team.xenobyte.api.module;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.WidgetMessage;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.handlers.ModuleHandler;
import forgefuck.team.xenobyte.module.NEI.GiveSelect;

public abstract class CheatModule extends ModuleAbility {
    
    private boolean forgeEvents, ticksStarted;
    private final String name, id, category;
    private ModuleHandler handler;
    @Cfg public boolean cfgState;
    @Cfg private int key;
    private int counter;
    
    public CheatModule() {
        String catPack = StringUtils.substringBeforeLast(getClass().getName(), ".");
        category = catPack.endsWith("NONE") ? null : catPack.contains(modules_package) && !modules_package.startsWith(catPack) ? StringUtils.substringAfterLast(catPack, ".") : null;
        forgeEvents = Stream.of(getClass().getDeclaredMethods()).filter(f -> f.isAnnotationPresent(SubscribeEvent.class)).findFirst().isPresent();
        id = String.valueOf(getClass().getName().hashCode());
        name = getClass().getSimpleName();
        setLastCounter();
    }
    
    public String getName() {
        return name;
    }
    
    public String getID() {
        return id;
    }
    
    public boolean hasCategory() {
        return category != null;
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
    
    public void handleInit(ModuleHandler handler) {
        this.handler = handler;
        onPreInit();
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

    public String getCategory() {
        return category;
    }
    
    protected ModuleHandler moduleHandler() {
        return handler;
    }
    
    protected void widgetMessage(String mess, WidgetMode mode) {
        handler.widgets().widgetMessage(new WidgetMessage(this, mess, mode));
    }
    
    protected void infoMessage(String mess, WidgetMode mode) {
        handler.widgets().infoMessage(new WidgetMessage(this, mess, mode));
    }
    
    protected void hideInfoMessage() {
        handler.widgets().hideInfoMessage(this);
    }
    
    protected GiveSelect giveSelector() {
        return (GiveSelect)handler.getModuleByClass(GiveSelect.class);
    }
    
    @Override public String toString() {
        return name;
    }

}