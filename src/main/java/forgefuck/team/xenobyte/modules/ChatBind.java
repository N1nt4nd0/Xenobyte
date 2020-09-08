package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;

public class ChatBind extends CheatModule {
    
    @Cfg("commands") private List<String> commands;
    
    public ChatBind() {
        super("ChatBind", Category.MISC, PerformMode.SINGLE);
        commands = new ArrayList<String>();
        commands.add("/home");
    }
    
    @Override public void onPerform(PerformSource src) {
        commands.forEach(utils::serverChatMessage);
    }
    
    @Override public String moduleDesc() {
        return lang.get("Execution of the set commands by keybind", "Выполнение заданных команд по кейбинду");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Commands") {
                @Override public void onLeftClick() {
                    new UserInput(lang.get("Commands", "Команды"), commands, InputType.CUSTOM).showFrame();
                }
                @Override public String elementDesc() {
                    return lang.get("Command list", "Список команд");
                }
            }
        );
    }

}
