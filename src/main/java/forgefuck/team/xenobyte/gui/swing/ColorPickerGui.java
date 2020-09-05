package forgefuck.team.xenobyte.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import forgefuck.team.xenobyte.api.gui.ColorPicker;
import forgefuck.team.xenobyte.api.gui.XenoJFrame;

public class ColorPickerGui extends XenoJFrame implements ChangeListener {

    private ColorPicker picker;
    private JSlider r, g, b, a;
    protected JPanel sliders;
    private JTextField info;
    private JPanel viewer;
    
    public ColorPickerGui(String title, ColorPicker picker) {
        super(title, DISPOSE_ON_CLOSE);
        this.picker = picker;
        viewer.setBackground(new Color(picker.rgb));
        r.setValue(picker.r);
        g.setValue(picker.g);
        b.setValue(picker.b);
        a.setValue(picker.a);
        r.addChangeListener(this);
        g.addChangeListener(this);
        b.addChangeListener(this);
        a.addChangeListener(this);
    }

    @Override public void createObjects() {
        r = new JSlider(0, 255);
        g = new JSlider(0, 255);
        b = new JSlider(0, 255);
        a = new JSlider(0, 255);
        sliders = new JPanel();
        viewer = new JPanel();
    }
    
    @Override public void configurate() {
        viewer.setPreferredSize(new Dimension(25, 25));
        r.setPreferredSize(new Dimension(350, 50));
        g.setPreferredSize(new Dimension(350, 50));
        b.setPreferredSize(new Dimension(350, 50));
        a.setPreferredSize(new Dimension(350, 50));
        sliders.setLayout(new GridBagLayout());
    }
    
    @Override public void localizeSet() {
        a.setBorder(customTitledBorder(lang.get("Transparency", "Прозрачность")));
        r.setBorder(customTitledBorder(lang.get("Red", "Красный")));
        g.setBorder(customTitledBorder(lang.get("Green", "Зеленый")));
        b.setBorder(customTitledBorder(lang.get("Blue", "Синий")));
    }

    @Override public void addElements() {
        buttonsBar.add(accept);
        add(viewer);
        sliders.add(r, GBC);
        sliders.add(g, GBC);
        sliders.add(b, GBC);
        sliders.add(a, GBC);
        add(sliders);
        add(buttonsBar);
    }

    @Override public void stateChanged(ChangeEvent e) {
        viewer.setBackground(new Color(r.getValue(), g.getValue(), b.getValue()));
        picker.setColor(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accept) {
            dispose();
        }
    }
    
}
