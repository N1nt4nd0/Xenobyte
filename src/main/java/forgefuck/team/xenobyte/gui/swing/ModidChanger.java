package forgefuck.team.xenobyte.gui.swing;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.utils.Config;
import forgefuck.team.xenobyte.utils.Reflections;

public class ModidChanger extends XenoJFrame {
    
    private JTextField modIDField, nameField, modVerField;
    
    public ModidChanger() {
        super(Xeno.mod_name + " modid changer", DO_NOTHING_ON_CLOSE);
    }

    @Override public void createObjects() {
        modVerField = new JTextField(30);
        modIDField = new JTextField(30);
        nameField = new JTextField(30);
    }

    @Override public void configurate() {
        modVerField.setBorder(customTitledBorder("Version", TitledBorder.CENTER));
        modIDField.setBorder(customTitledBorder("ModID", TitledBorder.CENTER));
        nameField.setBorder(customTitledBorder("Name", TitledBorder.CENTER));
        accept.setAlignmentY(CENTER_ALIGNMENT);
        buttonsBar.setFloatable(false);
        setLocationRelativeTo(null);
        modVerField.setFont(FONT);
        modIDField.setFont(FONT);
        nameField.setFont(FONT);
        setAlwaysOnTop(true);
    }

    @Override public void addElements() {
        buttonsBar.add(accept);
        buttonsBar.add(clear);
        add(modIDField);
        add(nameField);
        add(modVerField);
        add(buttonsBar);
    }
    
    @Override public void fillData() {
        modIDField.setText(Config.getData().fakeMODID);
        modVerField.setText(Config.getData().fakeVER);
        nameField.setText(Config.getData().fakeNAME);
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();
        if (button == accept) {
            String name = nameField.getText();
            String modid = modIDField.getText();
            String version = modVerField.getText();
            FMLModContainer modContainer = null;
            for (ModContainer mod : Loader.instance().getActiveModList()) {
                if (mod.getModId().equals(Xeno.mod_id)) {
                    modContainer = (FMLModContainer) mod;
                    break;
                }
            }
            ((ModMetadata) Reflections.getPrivateValue(FMLModContainer.class, modContainer, 2)).modId = modid;
            ((ModMetadata) Reflections.getPrivateValue(FMLModContainer.class, modContainer, 2)).name = name;
            ((ModMetadata) Reflections.getPrivateValue(FMLModContainer.class, modContainer, 2)).version = version;
            ((Map<String, Object>) Reflections.getPrivateValue(FMLModContainer.class, modContainer, 4)).replace("modid", modid);
            ((Map<String, Object>) Reflections.getPrivateValue(FMLModContainer.class, modContainer, 4)).replace("name", name);
            ((Map<String, Object>) Reflections.getPrivateValue(FMLModContainer.class, modContainer, 4)).replace("version", version);
            Reflections.setPrivateValue(FMLModContainer.class, modContainer, version, 6);
            LoadController loadController = ((LoadController) Reflections.getPrivateValue(Loader.class, Loader.instance(), 16));
            HashMap<String, EventBus> temp = Maps.newHashMap((ImmutableMap<String, EventBus>)Reflections.getPrivateValue(LoadController.class, loadController, 2));
            temp.put(modid, temp.remove(Xeno.mod_id));
            Reflections.setPrivateValue(LoadController.class, loadController, ImmutableMap.copyOf(temp), 2);
            Config.getData().fakeMODID = modid;
            Config.getData().fakeVER = version;
            Config.getData().fakeNAME = name;
            dispose();
            resume();
        } else if (button == clear) {
            Config.getData().fakeVER = Xeno.mod_version;
            Config.getData().fakeNAME = Xeno.mod_name;
            Config.getData().fakeMODID = Xeno.mod_id;
            modVerField.setText(Xeno.mod_version);
            nameField.setText(Xeno.mod_name);
            modIDField.setText(Xeno.mod_id);
        }
        Config.save();
    }
    
}
