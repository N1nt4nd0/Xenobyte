package forgefuck.team.xenobyte.module.MISC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import forgefuck.team.xenobyte.handlers.ModuleHandler;
import net.minecraft.network.Packet;

public class ScreenProtect extends CheatModule {
    
    @Cfg private List<String> channels;
    private boolean isReady;
    
    public ScreenProtect() {
        channels = new ArrayList<String>();
    }
    
    public void doProtect(String channel) {
        utils.closeGuis();
        new Thread(() -> {
            isReady = false;
            ModuleHandler hand = moduleHandler();
            List<CheatModule> moduleHash = new ArrayList<CheatModule>();
            moduleHash.addAll(hand.enabledModules().collect(Collectors.toList()));
            hand.enabledModules().forEach(hand::disable);
            try {
                Thread.sleep(2000);
            } catch(Exception e) {}
            moduleHash.forEach(hand::enable);
            widgetMessage("Сработал скриншотер: " + channel, WidgetMode.SUCCESS);
            isReady = true;
        }).start();
    }
    
    @Override public void onPreInit() {
        channels.add("screener");
        channels.add("nGuard");
    }
    
    @Override public boolean doReceivePacket(Packet packet) {
        if (packet instanceof FMLProxyPacket) {
            String channel = ((FMLProxyPacket) packet).channel();
            if (channels.contains(channel) && isReady) {
                doProtect(channel);
            }
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return "Отключит на пару секунд активные модули при скриншоте";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Channels") {
                   @Override public void onLeftClick() {
                       new UserInput("Screen channels", channels, InputType.CUSTOM).showFrame();
                   }
                   @Override public String elementDesc() {
                    return "Блэклист FMLProxy каналов (изменять только по назначению)";
                }
            }
        );
    }

}
