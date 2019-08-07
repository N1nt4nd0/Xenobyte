package forgefuck.team.xenobyte.api.module;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import net.minecraft.network.Packet;

public abstract class ModuleAbility implements Xeno {
    
    /**
     * Вызывается при активации модуля в режиме PerformMode.TOGGLE
     */
    public void onEnabled() {}
    
    /**
     * Вызывается при загрузке модуля (перед конфигом)
     */
    public void onPreInit() {}
    
    /**
     * Вызывается при загрузке модуля (после конфига и до загрузки EventHandler)
     */
    public void onPostInit() {}
    
    /**
     * Вызывается включении модуля в режиме PerformMode.TOGGLE
     */
    public void onDisabled() {}
    
    /**
     * Вызывается при первом старте тиков в модуле
     */
    public void onTicksStart() {}
    
    /**
     * Последний тик отрисовки гуи элементов
     */
    public void onDrawGuiLast() {}
    
    /**
     * Отрисовка InGame гуи элементов
     */
    public void onDrawGuiOverlay() {}
    
    /**
     * Вызывается каждый тик с задержкой заданной в {@link #tickDelay()}
     * @param inGame загружен ли мир с игроком
     */
    public void onTick(boolean inGame) {}
    
    /**
     * Вызывается при активации/деактивации модуля и в режиме PerformMode.SINGLE
     * @param src активация кейбиндом или кликом в гуи
     */
    public void onPerform(PerformSource src) {}
    
    /**
     * Вызывается в других модулях при бинде текущего модуля
     * @param module текущий модуль
     */
    public void onModuleBinded(CheatModule module) {}
    
    /**
     * Вызывается в других модулях при активации текущего модуля
     * @param module текущий модуль
     */
    public void onModuleEnabled(CheatModule module) {}
    
    /**
     * Вызывается в других модулях при удалении кейбинда текущего модуля
     * @param module текущий модуль
     */
    public void onModuleUnBinded(CheatModule module) {}
    
    /**
     * Вызывается в других модулях при деактивации текущего модуля
     * @param module текущий модуль
     */
    public void onModuleDisabled(CheatModule module) {}
    
    /**
     * Обработка входящих пакетов
     * @param packet входящий пакет
     * @return boolean пропускать или игнорировать пакет
     */
    public boolean doReceivePacket(Packet packet) {
        return true;
    }
    
    /**
     * Обработка исходящих пакетов
     * @param packet исходящий пакет
     * @return boolean пропускать или игнорировать пакет
     */
    public boolean doSendPacket(Packet packet) {
        return true;
    }
    
    /**
     * Определяет будет ли модуль вызывать события о своём состоянии {@link #onEnabled()}, {@link #onDisabled()} и такие как {@link #onModuleEnabled(CheatModule module)}}
     * @return boolean
     */
    public boolean provideStateEvents() {
        return true;
    }
    
    /**
     * Определяет будут ли выводится сообщения о состоянии модуля
     * @return boolean
     */
    public boolean allowStateMessages() {
        return true;
    }
    
    /**
     * Определяет будет ли модуль срабатывать по кейбинду в GuiScreen
     * @return boolean
     */
    public boolean inGuiPerform() {
        return false;
    }
    
    /**
     * Панель настроек модуля
     * @return Panel
     */
    public Panel settingPanel() {
        return null;
    }
    
    /**
     * Описание модуля для тултипа
     * @return String
     */
    public String moduleDesc() {
        return null;
    }
    
    /**
     * Определяет будет ли модуль загружен в ModuleHandler
     * @return boolean
     */
    public boolean isWorking() {
        return true;
    }
    
    /**
     * Задержка тиков при вызове {@link #onTick(boolean inGame)}
     * @return boolean
     */
    public int tickDelay() {
        return 0;
    }

}