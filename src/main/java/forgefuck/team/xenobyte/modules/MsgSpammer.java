package forgefuck.team.xenobyte.modules;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;

public class MsgSpammer extends CheatModule {
    
    @Cfg("message") private List<String> message;
    @Cfg("players") private List<String> players;
    private SpamListInput input;
    private Thread sendThread;

    public MsgSpammer() {
        super("MsgSpammer", Category.MISC, PerformMode.SINGLE);
        players = new ArrayList<String>();
        message = new ArrayList<String>();
        message.add("йбобаный ты казёл");
    }
    
    @Override public void onPostInit() {
        input = new SpamListInput(players);
    }
    
    @Override public void onPerform(PerformSource src) {
        if (sendThread != null && sendThread.isAlive()) {
            widgetMessage("Рассылка ещё ведётся...", WidgetMode.FAIL);
        } else {
            String mess = message.get(0);
            if (!mess.isEmpty()) {
                sendThread = new Thread(() -> {
                    List<String> out = new ArrayList<String>();
                    List<String> tab = utils.tabList();
                    if (input.white.isSelected()) {
                        out = tab.stream().filter(players::contains).collect(Collectors.toList());
                    } else if (input.black.isSelected()) {
                        out = tab.stream().filter(p -> !players.contains(p)).collect(Collectors.toList());
                    } else {
                        out = tab;
                    }
                    for (String player : out) {
                        if (!utils.isInGame()) {
                            break;
                        }
                        sendMessage(player, mess);
                        try {
                            Thread.sleep(1000);
                        } catch(Exception e) {}
                    }
                });
                sendThread.start();
            } else {
                widgetMessage("Сообщение не задано", WidgetMode.FAIL);
            }
        }
    }
    
    private void sendMessage(String player, String mess) {
        if (!utils.myName().equals(player)) {
            utils.serverChatMessage(String.format("/m %s %s", player, mess));
        }
    }
    
    @Override public String moduleDesc() {
        return "Рассылка сообщения заданным игрокам с задержкой в 1 секунду";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Message") {
                @Override public void onLeftClick() {
                    new UserInput("Сообщение", message, InputType.SINGLE_STRING).showFrame();
                }
                @Override public String elementDesc() {
                    return "Отправляемое сообщение";
                }
            },
            new Button("Players") {
                @Override public void onLeftClick() {
                    input.showFrame();
                }
                @Override public String elementDesc() {
                    return "Настройка вайтлиста игроков";
                }
            }
        );
    }
    
    class SpamListInput extends UserInput {

        JRadioButton white, black, all;
        ButtonGroup group;
        JPanel panel;
        
        public SpamListInput(List<String> list) {
            super("Спам лист", list, InputType.CUSTOM);
            all = new JRadioButton("Игнорируя список", true);
            white = new JRadioButton("Игрокам из списка");
            black = new JRadioButton("Всем кроме списка");
            group = new ButtonGroup();
            panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            group.add(white);
            group.add(black);
            group.add(all);
            panel.add(white, GBC);
            panel.add(black, GBC);
            panel.add(all, GBC);
            add(panel);
            pack();
        }
    }

}
