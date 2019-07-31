package forgefuck.team.xenobyte.module.MISC;

import java.util.ArrayList;
import java.util.List;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;

public class ChatBind extends CheatModule {
    
    @Cfg private List<String> commands;
    
    public ChatBind() {
        commands = new ArrayList<String>();
    }
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        commands.forEach(utils::serverChatMessage);
    }
    
    @Override public String moduleDesc() {
        return "Выполнение заданных команд по кейбинду";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Commands") {
                @Override public void onLeftClick() {
                    new UserInput("Команды", commands, InputType.CUSTOM).showFrame();
                }
            }
        );
    }

}
