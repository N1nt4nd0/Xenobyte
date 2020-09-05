package forgefuck.team.xenobyte.api.module;

import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.network.Packet;

public abstract class ModuleAbility {
    
    /**
     * Called when the module is activated in the PerformMode.TOGGLE mode
     */
    public void onEnabled() {}
    
    /**
     * Called when the module is loaded (after config and before loading EventHandler)
     */
    public void onPostInit() {}
    
    /**
     * Called by enabling a module in PerformMode.TOGGLE mode 
     */
    public void onDisabled() {}
    
    /**
     * Called at the first start of client ticks
     */
    public void onTicksStart() {}
    
    /**
     * Called when the module is loaded (before the config)
     */
    public void onHandlerInit() {}
    
    /**
     * Last tick of drawing GUI elements
     */
    public void onDrawGuiLast() {}
    
    /**
     * Rendering InGame GUI elements
     */
    public void onDrawGuiOverlay() {}
    
    /**
     * Every tick is called with a delay specified in {@link #tickDelay()}
     * @param inGame is the world loaded with the player
     */
    public void onTick(boolean inGame) {}
    
    /**
     * Called when the module is activated/deactivated and in the PerformMode.SINGLE mode
     * @param src activation by keybind or click in GUI
     */
    public void onPerform(PerformSource src) {}
    
    /**
     * Called in other modules when the current module is bound
     * @param module bound module
     */
    public void onModuleBinded(CheatModule module) {}
    
    /**
     * Called in other modules when the current module is activated
     * @param module activated module
     */
    public void onModuleEnabled(CheatModule module) {}
    
    /**
     * Called in other modules when deleting the keybind of the current module
     * @param module unbound module
     */
    public void onModuleUnBinded(CheatModule module) {}
    
    /**
     * Called in other modules when the current module is deactivated
     * @param module deactivated module
     */
    public void onModuleDisabled(CheatModule module) {}
    
    /**
     * Inbound packet processing
     * @param packet incoming packet
     * @return boolean skip or ignore a packet
     */
    public boolean doReceivePacket(Packet packet) {
        return true;
    }
    
    /**
     * Outbound packet processing
     * @param packet outgoing packet
     * @return boolean skip or ignore a packet
     */
    public boolean doSendPacket(Packet packet) {
        return true;
    }
    
    /**
     * Determines whether the module will be displayed in information widgets
     * @return boolean
     */
    public boolean isWidgetable() {
        return true;
    }
    
    /**
     * Determines whether the module will raise events about its state {@link #onEnabled()}, {@link #onDisabled()} and such as {@link #onModuleEnabled(CheatModule module)}}
     * @return boolean
     */
    public boolean provideStateEvents() {
        return true;
    }
    
    /**
     * Determines whether the module will trigger events about the keybind {@link #onModuleUnBinded(CheatModule module)}
     * @return boolean
     */
    public boolean provideBindEvents() {
        return true;
    }
    
    /**
     * Determines whether messages about the module's status will be displayed
     * @return boolean
     */
    public boolean allowStateMessages() {
        return true;
    }
    
    /**
     * Determines if the module will be triggered by keybind in GuiScreen
     * @return boolean
     */
    public boolean inGuiPerform() {
        return false;
    }
    
    /**
     * Module settings panel
     * @return Panel
     */
    public Panel settingPanel() {
        return null;
    }
    
    /**
     * Description of the module for the tooltip
     * @return String
     */
    public String moduleDesc() {
        return null;
    }
    
    /**
     * Determines if the module will be loaded into the ModuleHandler
     * @return boolean
     */
    public boolean isWorking() {
        return true;
    }
    
    /**
     * Delay of ticks when calling {@link #onTick(boolean inGame)}
     * @return boolean
     */
    public int tickDelay() {
        return 0;
    }

}