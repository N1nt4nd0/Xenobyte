package forgefuck.team.xenobyte.gui.swing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;

import forgefuck.team.xenobyte.api.gui.XenoJFrame;

public class ColorPickBar extends JToolBar {
    
    private final List<ColorObject> cols;
    private JTextField lastField;
    private final ItemChanter gui;
    
    public ColorPickBar(ItemChanter gui) {
        cols = new ArrayList<ColorObject>();
        cols.add(new ColorObject("0", "", "Black", Color.BLACK));
        cols.add(new ColorObject("1", "", "Dark Blue", new Color(0, 0, 170)));
        cols.add(new ColorObject("2", "", "Dark Green", new Color(0, 170, 0)));
        cols.add(new ColorObject("3", "", "Dark Aqua", new Color(0, 170, 170)));
        cols.add(new ColorObject("4", "", "Dark Red", new Color(170, 0, 0)));
        cols.add(new ColorObject("5", "", "Dark Purple", new Color(170, 0, 170)));
        cols.add(new ColorObject("6", "", "Gold", new Color(255, 170, 0)));
        cols.add(new ColorObject("7", "", "Gray", new Color(170, 170, 170)));
        cols.add(new ColorObject("8", "", "Dark Gray", new Color(85, 85, 85)));
        cols.add(new ColorObject("9", "", "Blue", new Color(85, 85, 255)));
        cols.add(new ColorObject("a", "", "Green", new Color(85, 255, 85)));
        cols.add(new ColorObject("b", "", "Aqua", new Color(85, 255, 255)));
        cols.add(new ColorObject("c", "", "Red", new Color(255, 85, 85)));
        cols.add(new ColorObject("d", "", "Light Purple", new Color(255, 85, 255)));
        cols.add(new ColorObject("e", "", "Yellow", new Color(255, 255, 85)));
        cols.add(new ColorObject("f", "", "White", Color.WHITE));
        cols.add(new ColorObject());
        cols.add(new ColorObject("k", "R", "Random Text", Color.WHITE));
        cols.add(new ColorObject("l", "B", "Bold", Color.WHITE));
        cols.add(new ColorObject("m", "S", "Strikethrough", Color.WHITE));
        cols.add(new ColorObject("n", "U", "Underline", Color.WHITE));
        cols.add(new ColorObject("o", "I", "Italic", Color.WHITE));
        cols.add(new ColorObject("r", "N", "Normal Text", Color.WHITE));
        setFloatable(false);
        this.gui = gui;
        build();
    }
    
    public void addTextField(JTextField field) {
        field.addMouseListener(new MouseListener() {
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {
                lastField = field;
            }
        });
    }
    
    private void build() {
        cols.forEach(col -> {
            if (col.isSep) {
                addSeparator();
            } else {
                JButton button = new JButton(col.name.isEmpty() ? "     " : " " + col.name + " ");
                if (!col.tooltip.isEmpty()) {
                    button.setToolTipText(col.tooltip);
                }
                button.setBorder(XenoJFrame.BORDER);
                button.setBackground(col.color);
                button.addActionListener(e -> {
                    if (lastField != null) {
                        try {
                            lastField.getDocument().insertString(lastField.getCaretPosition(), "§" + col.code, null);
                            gui.processDisplayNBT();
                            gui.save();
                        } catch (BadLocationException ex) {}
                    }
                });
                add(button);
            }
        });
    }
    
    class ColorObject {
        
        String code, name, tooltip;
        final boolean isSep;
        Color color;
        
        ColorObject() {
            isSep = true;
        }
        
        ColorObject(String code, String name, String desc, Color color) {
            this.isSep = false;
            this.color = color;
            this.code = code;
            this.tooltip = desc;
            this.name = name;
        }
        
    }

}
