package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import forgefuck.team.xenobyte.handlers.ModuleHandler;
import net.minecraft.network.Packet;

public class ScreenProtect extends CheatModule {
    
    @Cfg("channels") private List<String> channels;
    @Cfg("delay") private int delay;
    private boolean isReady;
    
    public ScreenProtect() {
        super("ScreenProtect", Category.MISC, PerformMode.TOGGLE);
        channels = new ArrayList<String>();
        channels.add("ScreenMod");
        channels.add("screener");
        channels.add("scr_mcs");
        channels.add("nGuard");
        channels.add("scrs");
        isReady = true;
        delay = 2;
    }
    
    @Override public boolean doReceivePacket(Packet packet) {
        if (packet instanceof FMLProxyPacket) {
            String channel = ((FMLProxyPacket) packet).channel();
            if (isReady && utils.isInGame() && channels.contains(channel)) {
                utils.closeGuis();
                new Thread(() -> {
                    isReady = false;
                    ModuleHandler hand = moduleHandler();
                    List<CheatModule> moduleHash = new ArrayList<CheatModule>();
                    moduleHash.addAll(hand.enabledModules().collect(Collectors.toList()));
                    hand.enabledModules().forEach(hand::disable);
                    try {
                        Thread.sleep(delay * 1000);
                    } catch(Exception e) {}
                    moduleHash.forEach(hand::enable);
                    widgetMessage(lang.get("Screenshot was triggered: ", "Сработал скриншотер: ") + channel, WidgetMode.SUCCESS);
                    isReady = true;
                }).start();
            }
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return lang.get("Disable active modules for a specified time when taking a screenshot by admin", "Отключит на заданное время активные модули при скриншоте администратора");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Channels") {
                @Override public void onLeftClick() {
                    new UserInput("Channels", channels, InputType.CUSTOM).showFrame();
                }
                @Override public String elementDesc() {
                    return lang.get("Blacklist FMLProxy channels (modify only by appointment)", "Блэклист FMLProxy каналов (изменять только по назначению)");
                }
            },
            new ScrollSlider("Delay", delay, 6) {
                @Override public void onScroll(int dir, boolean withShift) {
                     delay = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Time in seconds for which active modules will be disabled when a screenshot is taken", "Время в секундах на которое отключатся активные модули при скриншоте");
                }
            }
        );
    }

}
