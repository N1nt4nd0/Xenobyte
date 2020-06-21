package forgefuck.team.xenobyte.gui.swing;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import forgefuck.team.xenobyte.api.gui.InputType;

public class UserInput extends XenoJFrame {
    
    private List<String> list;
    private JPanel inputPanel;
    private InputType type;
    private JButton add;

    public UserInput(String title, List<String> list, InputType type) {
        super(title, DISPOSE_ON_CLOSE);
        if (type == InputType.COORDS) {
            if (list.size() < 3) {
                for (int i = list.size(); i < 3; i++) {
                    list.add("0");
                }
            } else if (list.size() > 3) {
                for (int i = 2; i < list.size(); i++) {
                    list.remove(i);
                }
            }
        } else if (type == InputType.SINGLE_STRING) {
            if (list.size() == 0) {
                list.add("");
            } else if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    list.remove(i);
                }
            }
        }
        if (type == InputType.CUSTOM) {
            buttonsBar.add(add);
        }
        list.forEach(this::addTextBar);
        this.type = type;
        this.list = list;
        packFrame();
    }

    @Override public void createObjects() {
        add = new JButton("Добавить");
        inputPanel = new JPanel();
    }

    @Override public void configurate() {
        inputPanel.setLayout(new GridBagLayout());
        add.addActionListener(this);
        add.setFont(FONT);
    }

    @Override public void addElements() {
        buttonsBar.add(accept);
        buttonsBar.add(clear);
        add(inputPanel);
        add(buttonsBar);
    }
    
    private Stream<TextBar> textBars() {
        return Stream.of(inputPanel.getComponents()).map(c -> (TextBar) c);
    }
    
    private void addTextBar(String text) {
        inputPanel.add(new TextBar(text), GBC);
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();
        if (button == accept) {
            if (type == InputType.COORDS) {
                if (!textBars().allMatch(TextBar::parseIntValue)) {
                    return;
                }
            }
            list.clear();
            list.addAll(textBars().map(Objects::toString).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
            dispose();
        } else if (button == clear) {
            if (type == InputType.CUSTOM) {
                inputPanel.removeAll();
            } else {
                textBars().forEach(TextBar::clearText);
            }
            packFrame();
        } else if (button == add) {
            addTextBar("");
            packFrame();
        }
    }
    
    class TextBar extends JToolBar {
        
        JButton removeButton;
        JTextField inputField;
        
        TextBar(String text) {
            setFloatable(false);
            inputField = new JTextField(text, 20);
            removeButton = new JButton("X");
            removeButton.setFont(FONT);
            inputField.setFont(FONT);
            add(inputField);
            if (type == InputType.CUSTOM) {
                removeButton.addActionListener(e -> {
                    inputPanel.remove(this);
                    packFrame();
                });
                add(removeButton);
            }
        }
        
        boolean parseIntValue() {
            try {
                Integer.parseInt(inputField.getText());
                inputField.setBackground(WHITE);
                return true;
            } catch(NumberFormatException e) {
                inputField.setBackground(FAIL);
                return false;
            }
        }
        
        void clearText() {
            if(type == InputType.COORDS) {
                inputField.setText("0");
            } else {
                inputField.setText("");
            }
        }
        
        @Override public String toString() {
            return inputField.getText();
        }
        
    }

}