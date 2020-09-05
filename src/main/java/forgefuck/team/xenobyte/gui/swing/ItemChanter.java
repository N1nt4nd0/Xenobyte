package forgefuck.team.xenobyte.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;

import forgefuck.team.xenobyte.api.gui.XenoJFrame;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;

public class ItemChanter extends XenoJFrame implements KeyListener {
    
    private JTextField nameField, loreField, effDurationField, effAmpliferField, enchLevelField, logField;
    private JToolBar enchBar, effBar, inputBar, jsonBar;
    private JCheckBox keepName, onTop, checkMode;
    private NBTTagCompound editableNBT, outNBT;
    private JButton addEnch, addEff, ffGift;
    private JComboBox effectBox, enchBox;
    private JRadioButton onItem, onBook;
    private JScrollPane nbtScrollPane;
    private ButtonGroup radioGroup;
    private ColorPickBar colorBar;
    private JTextArea nbtArea;
    private String sep;
    
    public ItemChanter() {
        super("Chanter", DISPOSE_ON_CLOSE);
        loadDefaults();
    }
    
    @Override public void createObjects() {
        nbtScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        keepName = new JCheckBox("Keep names");
        checkMode = new JCheckBox("CheckMode");
        effDurationField = new JTextField();
        effAmpliferField = new JTextField();
        editableNBT = new NBTTagCompound();
        colorBar = new ColorPickBar(this);
        enchLevelField = new JTextField();
        onTop = new JCheckBox("On top");
        ffGift = new JButton("FF-Gift");
        nbtArea = new JTextArea(12, 40);
        radioGroup = new ButtonGroup();
        outNBT = new NBTTagCompound();
        nameField = new JTextField();
        loreField = new JTextField();
        logField = new JTextField();
        effectBox = new JComboBox();
        onBook = new JRadioButton();
        onItem = new JRadioButton();
        enchBox = new JComboBox();
        inputBar = new JToolBar();
        enchBar = new JToolBar();
        jsonBar = new JToolBar();
        effBar = new JToolBar();
        addEnch = new JButton();
        addEff = new JButton();
        sep = " - ";
    }
    
    @Override public void configurate() {
        nbtScrollPane.setViewportView(nbtArea);
        onTop.addActionListener(e -> {
            setAlwaysOnTop(onTop.isSelected());
        });
        effAmpliferField.addKeyListener(this);
        effDurationField.addKeyListener(this);
        enchLevelField.addKeyListener(this);
        colorBar.addTextField(nameField);
        colorBar.addTextField(loreField);
        effectBox.setIgnoreRepaint(true);
        addEnch.addActionListener(this);
        enchBox.setIgnoreRepaint(true);
        addEff.addActionListener(this);
        nbtArea.setWrapStyleWord(true);
        nameField.addKeyListener(this);
        loreField.addKeyListener(this);
        effAmpliferField.setFont(FONT);
        effDurationField.setFont(FONT);
        ffGift.addActionListener(this);
        enchLevelField.setFont(FONT);
        nbtArea.addKeyListener(this);
        inputBar.setFloatable(false);
        effectBox.setEditable(false);
        enchBar.setFloatable(false);
        logField.setEditable(false);
        jsonBar.setFloatable(false);
        enchBox.setEditable(false);
        effBar.setFloatable(false);
        keepName.setSelected(true);
        nbtArea.setLineWrap(true);
        effectBox.setFont(FONT);
        nameField.setFont(FONT);
        loreField.setFont(FONT);
        logField.setFont(FONT);
        nbtArea.setFont(FONT);
        enchBox.setFont(FONT);
        ffGift.setFont(FONT);
    }
    
    @Override public void localizeSet() {
        checkMode.setToolTipText(lang.get("Load NBT of an item in hand or an entity in focus by the GiveSelect keybind", "Загружать NBT предмета в руке или сущности в фокусе по кейбинду GiveSelect"));
        loreField.setBorder(customTitledBorder(lang.get("Lore (comma separated)", "Описания (через запятую)"), TitledBorder.CENTER));
        effDurationField.setBorder(customTitledBorder(lang.get("Potion duration", "Время эффекта"), TitledBorder.CENTER));
        effAmpliferField.setBorder(customTitledBorder(lang.get("Potion power", "Сила эффекта"), TitledBorder.CENTER));
        keepName.setToolTipText(lang.get("Keep names on reset (display tag)", "Не сбрасывать имена (display tag)"));
        enchLevelField.setBorder(customTitledBorder(lang.get("Enchant", "LvL зачарования"), TitledBorder.CENTER));
        jsonBar.setBorder(customTitledBorder(lang.get("NBT Json Editor", "Json редактор NBT")));
        nameField.setBorder(customTitledBorder(lang.get("Name", "Имя"), TitledBorder.CENTER));
        enchBar.setBorder(customTitledBorder(lang.get("Item chant", "Зачарование предмета")));
        effBar.setBorder(customTitledBorder(lang.get("Potion effect", "Эффект зелья")));
        logField.setBorder(customTitledBorder(lang.get("Last info", "Инфо")));
        ffGift.setToolTipText(lang.get("Imbalance potion", "Имбовая зелька"));
        onTop.setToolTipText(lang.get("Gui on top", "Поверх окон"));
        addEnch.setText(lang.get("Add", "Добавить"));
        onItem.setText(lang.get("Item", "Предмет"));
        addEff.setText(lang.get("Add", "Добавить"));
        onBook.setText(lang.get("Book", "Книга"));
        setTitle(lang.get("Chanter", "Чантер"));
    }
    
    @Override public void addElements() {
        inputBar.add(effAmpliferField);
        inputBar.add(effDurationField);
        inputBar.add(enchLevelField);
        jsonBar.add(nbtScrollPane);
        radioGroup.add(onItem);
        radioGroup.add(onBook);
        effBar.add(effectBox);
        effBar.addSeparator();
        effBar.add(addEff);
        enchBar.add(enchBox);
        enchBar.add(onItem);
        enchBar.add(onBook);
        enchBar.add(addEnch);
        buttonsBar.add(ffGift);
        buttonsBar.add(clear);
        buttonsBar.add(keepName);
        buttonsBar.add(onTop);
        buttonsBar.add(checkMode);
        add(nameField);
        add(loreField);
        add(colorBar);
        add(inputBar);
        add(enchBar);
        add(effBar);
        add(jsonBar);
        add(logField);
        add(buttonsBar);
    }
    
    @Override public void fillData() {
        for (Enchantment ench : Enchantment.enchantmentsList) {
            if (ench != null) {
                enchBox.addItem(ench.effectId + sep + I18n.format(ench.getName()) + " (" + ench.type.name() + ")");
            }
        }
        for (Potion potion : Potion.potionTypes) {
            if (potion != null) {
                effectBox.addItem(potion.id + sep + I18n.format(potion.getName()));
            }
        }
    }
    
    public void save() {
        if (checkInputs()) {
            try {
                outNBT = (NBTTagCompound) JsonToNBT.func_150315_a(nbtArea.getText());
            } catch (Exception e) {}
        }
    }
    
    public boolean isCheckMode() {
        return checkMode.isSelected();
    }
    
    public NBTTagCompound getOutNBT() {
        return outNBT;
    }
    
    public void info(Object obj) {
        logField.setText(obj.toString());
        logField.setCaretPosition(0);
    }
    
    public void clearInfo() {
        info("");
    }
    
    private void updateNBTArea() {
        nbtArea.setText(editableNBT.toString());
    }
    
    private String getChantID(JComboBox box) {
        return StringUtils.substringBefore(box.getSelectedItem().toString(), sep);
    }
    
    private void addChantNBT(String key, NBTTagCompound tag) {
        if (!editableNBT.hasKey(key)) {
            editableNBT.setTag(key, new NBTTagList());
        }
        editableNBT.getTagList(key, 10).appendTag(tag);
    }
    
    private String[] getDisplayData(NBTTagCompound itemTag) {
        String[] display = new String[2];
        display[0] = new String();
        display[1] = new String();
        if (itemTag.hasKey("display", 10)) {
            NBTTagCompound displayTag = itemTag.getCompoundTag("display");
            display[0] = displayTag.getString("Name");
            if (displayTag.hasKey("Lore", 9)) {
                String lore = "";
                NBTTagList loreList = displayTag.getTagList("Lore", 8);
                for (int i = 0; i < loreList.tagCount(); i++) {
                    lore += "," + loreList.getStringTagAt(i);
                }
                display[1] = lore.replaceFirst(",", "");
            }
        }
        return display;
    }
    
    public void processDisplayNBT() {
        String name = nameField.getText();
        String lore = loreField.getText();
        if (name.isEmpty() && lore.isEmpty()) {
            editableNBT.removeTag("display");
        } else {
            NBTTagCompound displayTag = new NBTTagCompound();
            if (!name.isEmpty()) {
                displayTag.setString("Name", "§r" + name);
            }
            if (!lore.isEmpty()) {
                NBTTagList loreList = new NBTTagList();
                for (String desc : lore.split(",")) {
                    loreList.appendTag(new NBTTagString(desc));
                }
                displayTag.setTag("Lore", loreList);
            }
            editableNBT.setTag("display", displayTag);
        }
        updateNBTArea();
    }
    
    public boolean checkInputs() {
        Exception ex = null;
        try {
            Short.parseShort(enchLevelField.getText());
            enchLevelField.setBackground(WHITE);
        } catch (NumberFormatException e) {
            enchLevelField.setBackground(FAIL);
            ex = e;
        }
        try {
            Byte.parseByte(effAmpliferField.getText());
            effAmpliferField.setBackground(WHITE);
        } catch (NumberFormatException e) {
            effAmpliferField.setBackground(FAIL);
            ex = e;
        }
        try {
            Integer.parseInt(effDurationField.getText());
            effDurationField.setBackground(WHITE);
        } catch (NumberFormatException e) {
            effDurationField.setBackground(FAIL);
            ex = e;
        }
        try { 
            JsonToNBT.func_150315_a(nbtArea.getText());
            nbtArea.setBackground(WHITE);
        } catch (NBTException e) {
            nbtArea.setBackground(FAIL);
            ex = e;
        }
        if (ex != null) {
            info(ex.getMessage());
            return false;
        }
        clearInfo();
        return true;
    }
    
    public void loadCustomNBT(NBTTagCompound in) {
        String[] display = getDisplayData(editableNBT = (NBTTagCompound) in.copy());
        nameField.setText(display[0]);
        loreField.setText(display[1]);
        updateNBTArea();
        save();
    }
    
    public void loadCustomNBT(String json) {
        try { 
            loadCustomNBT((NBTTagCompound) JsonToNBT.func_150315_a(json));
        } catch (NBTException e) {
            loadCustomNBT(new NBTTagCompound());
            info("Json exception: "+ e.getMessage());
        }
    }
    
    public void loadDefaults() {
        nameField.setText(keepName.isSelected() ? nameField.getText() : "");
        loreField.setText(keepName.isSelected() ? loreField.getText() : "");
        effDurationField.setText(String.valueOf(Integer.MAX_VALUE));
        effAmpliferField.setText(String.valueOf(Byte.MAX_VALUE));
        enchLevelField.setText(String.valueOf(Short.MAX_VALUE));
        editableNBT = new NBTTagCompound();
        onItem.setSelected(true);
        processDisplayNBT();
        save();
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == clear) {
            loadDefaults();
            return;
        } else if (src == addEnch) {
            if (checkInputs()) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setShort("id", Short.parseShort(getChantID(enchBox)));
                tag.setShort("lvl", Short.parseShort(enchLevelField.getText()));
                addChantNBT(onItem.isSelected() ? "ench" : "StoredEnchantments", tag);
                updateNBTArea();
            }
        } else if (src == addEff) {
            if (checkInputs()) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Id", Byte.parseByte(getChantID(effectBox)));
                tag.setByte("Amplifier", Byte.parseByte(effAmpliferField.getText()));
                tag.setInteger("Duration", Integer.parseInt(effDurationField.getText()));
                addChantNBT("CustomPotionEffects", tag);
                updateNBTArea();
            }
        } else if (src == ffGift) {
            loadCustomNBT("{CustomPotionEffects:[0:{Duration:2147483647,Id:5b,Amplifier:127b},1:{Duration:2147483647,Id:6b,Amplifier:127b},2:{Duration:2147483647,Id:11b,Amplifier:127b},3:{Duration:2147483647,Id:12b,Amplifier:127b},4:{Duration:2147483647,Id:13b,Amplifier:127b},5:{Duration:2147483647,Id:23b,Amplifier:127b},6:{Duration:2147483647,Id:10b,Amplifier:127b}],display:{Name:§4By N1nt4nd0 ( §3github.com/N1nt4nd0§4 )}}");
            return;
        }
        save();
    }
    
    @Override public void keyReleased(KeyEvent e) {
        Object field = e.getSource();
        if (field == nameField || field == loreField) {
            processDisplayNBT();
        }
        save();
    }

    @Override public void keyPressed(KeyEvent e) {}

    @Override public void keyTyped(KeyEvent e) {}

}