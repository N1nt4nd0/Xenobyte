package forgefuck.team.xenobyte.gui.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import forgefuck.team.xenobyte.api.gui.XenoJFrame;

public class CreditsGui extends XenoJFrame {
    
    private JPanel fieldsPanel;
    
    public CreditsGui() {
        super("Credits", DISPOSE_ON_CLOSE);
    }
    
    @Override public void localizeSet() {}

    @Override public void createObjects() {
        fieldsPanel = new JPanel();
    }

    @Override public void configurate() {
        fieldsPanel.setLayout(new GridBagLayout());
        accept.setText(" ОК ");
    }

    @Override public void addElements() {
        fieldsPanel.add(new LinkLabel("  " + mod_name + " version " + mod_version + " (C) " + mod_author + "  "), GBC);
        fieldsPanel.add(new JLabel("  "), GBC);
        fieldsPanel.add(new LinkLabel("Telegram", tg_link), GBC);
        fieldsPanel.add(new LinkLabel("YouTube", yt_link), GBC);
        fieldsPanel.add(new LinkLabel("Discord", ds_link), GBC);
        fieldsPanel.add(new LinkLabel("GitHub", gh_link), GBC);
        fieldsPanel.add(new JLabel("  "), GBC);
        buttonsBar.add(accept);
        add(fieldsPanel);
        add(buttonsBar);
    }
    
    @Override public void actionPerformed(ActionEvent e) {
        dispose();
    }
    
    class LinkLabel extends JLabel {
        
        LinkLabel(String title) {
            this(title, null);
        }
        
        LinkLabel(String title, String link) {
            super(title);
            setHorizontalAlignment(JTextField.CENTER);
            setFont(FONT);
            if (link != null) {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() > 0) {
                            utils.clipboardMessage(link);
                        }
                    }
                    @Override public void mouseEntered(MouseEvent e) {
                        ((JComponent)e.getSource()).setForeground(Color.RED);
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        ((JComponent)e.getSource()).setForeground(Color.BLUE);
                    }
                });
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setToolTipText("Copy " + link);
                setForeground(Color.BLUE);
            }
        }
        
    }

}