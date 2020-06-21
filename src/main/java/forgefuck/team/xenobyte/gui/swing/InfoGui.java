package forgefuck.team.xenobyte.gui.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import forgefuck.team.xenobyte.api.Xeno;

public class InfoGui extends XenoJFrame {
    
    private JLabel author, desc, vkLink, disLink, gitHubLink;
    private MouseAdapter linkClicker;
    private JPanel fieldsPanel;
    
    public InfoGui() {
        super("Инфо", DISPOSE_ON_CLOSE);
    }

    @Override public void createObjects() {
        linkClicker = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI(((JLabel)e.getSource()).getText()));
                            dispose();
                        } catch(Exception ex) {}
                    }
                }    
            }
            @Override public void mouseEntered(MouseEvent e) {
                ((JComponent)e.getSource()).setForeground(Color.BLUE);
            }
            @Override public void mouseExited(MouseEvent e) {
                ((JComponent)e.getSource()).setForeground(Color.BLACK);
            }
        };
        author = new JLabel("  " + Xeno.mod_name + " version " + Xeno.mod_version + " (C) " + Xeno.author + "  ");
        gitHubLink = new JLabel("https://github.com/N1nt4nd0/Xenobyte");
        disLink = new JLabel("https://discord.gg/HMMKfWp");
        desc = new JLabel("Special for ForgeFuck - Team");
        vkLink = new JLabel("https://vk.com/forgefuck");
        fieldsPanel = new JPanel();
    }

    @Override public void configurate() {
        gitHubLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        disLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        vkLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        author.setHorizontalAlignment(JTextField.CENTER);
        desc.setHorizontalAlignment(JTextField.CENTER);
        fieldsPanel.setLayout(new GridBagLayout());
        gitHubLink.addMouseListener(linkClicker);
        disLink.addMouseListener(linkClicker);
        vkLink.addMouseListener(linkClicker);
        gitHubLink.setFont(FONT);
        accept.setText(" ОК ");
        disLink.setFont(FONT);
        vkLink.setFont(FONT);
        author.setFont(FONT);
        desc.setFont(FONT);
    }

    @Override public void addElements() {
        fieldsPanel.add(author, GBC);
        fieldsPanel.add(desc, GBC);
        fieldsPanel.add(gitHubLink, GBC);
        fieldsPanel.add(disLink, GBC);
        fieldsPanel.add(vkLink, GBC);
        buttonsBar.add(accept);
        add(fieldsPanel);
        add(buttonsBar);
    }
    
    @Override public void actionPerformed(ActionEvent e) {
        dispose();
    }

}