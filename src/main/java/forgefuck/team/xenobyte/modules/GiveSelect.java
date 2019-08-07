package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.integration.NEI;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.gui.swing.ItemChanter;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GiveSelect extends CheatModule {
    
    @Cfg("withChant") private boolean withChant;
    @Cfg("fillSlots") private boolean fillSlots;
    @Cfg("count") private int count;
    private ItemStack givedItem;
    private ItemChanter chanter;

    public GiveSelect() {
        super("GiveSelect", Category.NEI, PerformMode.SINGLE);
        givedItem = new ItemStack(Blocks.command_block);
        chanter = new ItemChanter();
    }
    
    public ItemStack givedItem() {
        NBTTagCompound chant = givedNBT();
        ItemStack outItem = givedItem.copy();
        return Xeno.utils.item(outItem.splitStack(count), withChant && !chant.hasNoTags() ? chant : null).copy();
    }
    
    public NBTTagCompound givedNBT() {
        return chanter.getOutNBT();
    }
    
    public int itemCount() {
        return count;
    }
    
    public boolean fillAllSlots() {
        return fillSlots;
    }
    
    @Override public void onPreInit() {
        count = 1;
    }
    
    @Override public void onPerform(PerformSource src) {
        switch(src) {
        case BUTTON:
            utils.openGui(new GuiInventory(utils.player()));
            break;
        case KEY:
            ItemStack checkItem = NEI.getStackMouseOver();
            if (checkItem != null) {
                givedItem = checkItem;
                widgetMessage("выбран " + givedItem.getDisplayName() + " [x" + count + "]", WidgetMode.INFO);
            } else {
                if (utils.isInGameGui()) {
                    ItemStack item = utils.item();
                    if (item != null && item.hasTagCompound()) {
                        chanter.loadCustomNBT(item.getTagCompound());
                    }
                    chanter.showFrame();
                }
            }
        }
    }
    
    @Override public boolean isWorking() {
        return NEI.isAvailable();
    }
    
    @Override public boolean inGuiPerform() {
        return true;
    }
    
    @Override public boolean allowStateMessages() {
        return false;
    }
    
    @Override public String moduleDesc() {
        return "Выбор предмета в NEI по кейбинду для выдачи через эксплойты";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Count", count, 64) {
                @Override public void onScroll(int dir, boolean withShift) {
                    count = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Количество выдаваемого предмета";
                }
            },
            new Button("FillSlots", fillSlots) {
                @Override public void onLeftClick() {
                    buttonValue(fillSlots = !fillSlots);
                }
                @Override public String elementDesc() {
                    return "По возможности заполнить все слоты в инвентаре при выдаче предмета";
                }
            },
            new Button("WithChant", withChant) {
                @Override public void onLeftClick() {
                    buttonValue(withChant = !withChant);
                }
                @Override public String elementDesc() {
                    return "С применением NBT из Chanter'a";
                }
            },
            new Button("Chanter") {
                @Override public void onLeftClick() {
                    chanter.showFrame();
                }
                @Override public String elementDesc() {
                    return "Зачаровыватель/редактор NBT предмета (по кейбинду GiveSelect'a откроется с NBT предмета в руке)";
                }
            }
        );
    }

}
