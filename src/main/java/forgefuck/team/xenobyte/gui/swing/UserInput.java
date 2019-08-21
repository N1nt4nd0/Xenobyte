package forgefuck.team.xenobyte.gui.swing;

import java.awt.Color;
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
        this.type = type;
        this.list = list;
        if (this.type == InputType.COORDS) {
            if (this.list.size() < 3) {
                for (int i = 0; i < 3; i++) {
                    this.list.add("0");
                } 
            }
        } else if (this.type == InputType.SINGLE_STRING) {
            if (this.list.size() < 1) {
                this.list.add("");
            }
        }
        if (this.type == InputType.CUSTOM) {
            buttonsBar.add(add);
        }
        this.list.forEach(this::addTextBar);
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