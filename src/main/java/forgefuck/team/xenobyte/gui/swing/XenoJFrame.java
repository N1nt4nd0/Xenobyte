package forgefuck.team.xenobyte.gui.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import forgefuck.team.xenobyte.render.Images;

public abstract class XenoJFrame extends JFrame implements ActionListener {
    
    public static final Border BORDER = BorderFactory.createLineBorder(Color.BLACK);
    public static final GridBagConstraints GBC = new GridBagConstraints();
    public static final Font FONT = new Font("Terminal", Font.BOLD, 12);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color FAIL = new Color(255, 200, 200);
    protected final JButton accept, clear;
    protected final JToolBar buttonsBar;
    private static XenoJFrame lastFrame;
    private boolean resume;
    
    public XenoJFrame(String title, int closeOperation) {
        super(title);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        GBC.gridwidth = GridBagConstraints.REMAINDER;
        setDefaultCloseOperation(closeOperation);
        accept = new JButton("Применить");
        clear = new JButton("Сбросить");
        accept.addActionListener(this);
        clear.addActionListener(this);
        buttonsBar = new JToolBar();
        buttonsBar.setFloatable(false);
        setIconImage(Images.ICON);
        accept.setFont(FONT);
        clear.setFont(FONT);
        setResizable(false);
        createObjects();
        configurate();
        addElements();
        fillData();
        packFrame();
    }
    
    public static Border customTitledBorder(String title, int align) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleJustification(align);
        return border;
    }
    
    public static Border customTitledBorder(String title) {
        return customTitledBorder(title, TitledBorder.LEFT);
    }
    
    protected void packFrame() {
        pack();
        setLocationRelativeTo(null);
    }
    
    public void showFrame() {
        showFrame(false);
    }
    
    public void resume() {
        resume = true;
    }
    
    public void showFrame(boolean waiting) {
        if (lastFrame != null) {
            lastFrame.dispose();
        }
        lastFrame = this;
        setState(NORMAL);
        setVisible(true);
        toFront();
        if (waiting) {
            for (;;) {
                if (resume) break;
                try {
                    Thread.sleep(100);
                } catch(Exception e) {}
            }
        }
    }
    
    protected abstract void createObjects();
    protected abstract void configurate();
    protected abstract void addElements();
    protected void fillData() {};

}