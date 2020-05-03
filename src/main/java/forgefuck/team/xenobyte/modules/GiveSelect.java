package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.integration.NEI;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.gui.swing.ItemChanter;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
        count = 1;
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
    
    @Override public void onPerform(PerformSource src) {
        if (src == PerformSource.KEY) {
            ItemStack checkItem = NEI.getStackMouseOver();
            if (checkItem != null) {
                widgetMessage("выбран " + (givedItem = checkItem).getDisplayName() + " [x" + count + "]", WidgetMode.INFO);
            } else if (utils.isInGameGui()) {
                NBTTagCompound outTag = new NBTTagCompound();
                String outMessage = new String();
                TileEntity tile = Xeno.utils.tile();
                Entity entity = Xeno.utils.entity();
                ItemStack item = Xeno.utils.item();
                if (item != null) {
                    if (item.hasTagCompound()) {
                        outTag = item.getTagCompound();
                        outMessage = "загружен NBT предмета " + item.getDisplayName();
                    }
                } else if (tile != null) {
                    tile.writeToNBT(outTag);
                    outMessage = "загружен NBT тайла " + Xeno.utils.formatCoords(tile.xCoord, tile.yCoord, tile.zCoord);
                } else if (entity != null) {
                    entity.writeToNBT(outTag);
                    outMessage = "загружен NBT моба " + entity.getCommandSenderName();
                }
                if (!outTag.hasNoTags()) {
                    widgetMessage(outMessage, WidgetMode.INFO);
                    chanter.loadCustomNBT(outTag);
                }
                chanter.showFrame();
            }
        } else {
            widgetMessage("для выбора предмета необходимо навести на него и нажать кейбинд", WidgetMode.FAIL);
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
        return "Выбор предмета в NEI по кейбинду для выдачи через эксплойты (" + givedItem.getDisplayName() + ")";
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
                    return "По возможности заполнить все слоты в инвентаре при выдаче";
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
                    return "Редактор NBT (по кейбинду GiveSelect'a загрузится NBT предмета в рукe, моба или блока в фокусе)";
                }
            }
        );
    }

}
