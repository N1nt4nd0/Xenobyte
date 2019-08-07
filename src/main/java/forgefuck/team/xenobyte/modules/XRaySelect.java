package forgefuck.team.xenobyte.modules;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JSlider;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.ColorPicker;
import forgefuck.team.xenobyte.api.integration.NEI;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.ColorPickerGui;
import forgefuck.team.xenobyte.render.Colors;
import forgefuck.team.xenobyte.render.XRayHintRender;
import forgefuck.team.xenobyte.utils.Config;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockTorch;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.RotationHelper;

public class XRaySelect extends CheatModule {
    
    @Cfg private List<String> configBlocks;
    private List<String> missingBlocks;
    public List<SelectedBlock> blocks;
    @Cfg public boolean guiHint;
    private String neiSubset;
    
    public XRaySelect() {
        super("XRaySelect", Category.NEI, PerformMode.SINGLE);
        blocks = new CopyOnWriteArrayList<SelectedBlock>();
        missingBlocks = new ArrayList<String>();
        configBlocks = new ArrayList<String>();
        neiSubset = "X-Ray";
    }
    
    private void updateNEI() {
        NEI.addSubset(mod_name + "." + neiSubset, blocks.stream().map(b -> b.itemBlock).collect(Collectors.toList()));
    }
    
    public SelectedBlock getBlock(ItemStack stack) {
        return getBlock(b -> b.itemBlock.isItemEqual(stack));
    }
    
    public SelectedBlock getBlock(Block block, int meta) {
        return getBlock(b -> Block.isEqualTo(b.block, block) && b.meta == meta);
    }
    
    private SelectedBlock getBlock(Predicate<SelectedBlock> predicate) {
        Optional<SelectedBlock> optional = blocks.stream().filter(predicate).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
    
    public int resetMetaFor(Block block, int meta) {
        if (RotationHelper.getValidVanillaBlockRotations(block) != ForgeDirection.VALID_DIRECTIONS || block instanceof BlockTorch || block instanceof BlockMobSpawner || block instanceof BlockLever || block instanceof BlockDropper || block instanceof BlockDispenser) {
            return 0;
        }
        return meta;
    }
    
    @Override public void onPreInit() {
        guiHint = true;
    }
    
    @Override public void onPostInit() {
        IdentityHashMap<Item, IItemRenderer> customRender = Reflections.getPrivateValue(MinecraftForgeClient.class, null, 0);
        for (Object obj : Item.itemRegistry) {
            if (obj instanceof ItemBlock && !customRender.containsKey(obj)) {
                MinecraftForgeClient.registerItemRenderer((Item) obj, new XRayHintRender(this));
            }
        }
        for (String cBlock : configBlocks) {
            String[] data = cBlock.split(":");
            Block block = (Block) Block.blockRegistry.getObject(data[0] + ":" + data[1]);
            if (block instanceof BlockAir) {
                missingBlocks.add(cBlock);
            } else {
                blocks.add(new SelectedBlock(new ItemStack(block, 1, Integer.parseInt(data[2])), Integer.parseInt(data[3]), Float.parseFloat(data[4])));
            }
        }
        updateNEI();
    }
    
    @Override public void onPerform(PerformSource src) {
        switch (src) {
        case BUTTON:
               NEI.openGui("@" + neiSubset);
            break;
        case KEY:
            ItemStack stack = NEI.getStackMouseOver();
            if (stack != null) {
                if (stack.getItem() instanceof ItemBlock) {
                    SelectedBlock block = getBlock(stack);
                    if (block == null) {
                        block = new SelectedBlock(stack, Colors.BLACK, 1);
                    }
                    new XRaySettings(block).showFrame();
                }
            } else {
                if (utils.isInGameGui()) {
                    NEI.openGui("@" + neiSubset);
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
        return "Выбор блока в NEI по кейбинду для X-Ray (также добавляет вкладку @" + neiSubset + ")";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("GuiHint", guiHint) {
                @Override public void onLeftClick() {
                    buttonValue(guiHint = !guiHint);
                }
                @Override public String elementDesc() {
                    return "Подсветка выбранных предметов в инвентаре";
                }
            }
        );
    }
    
    class XRaySettings extends ColorPickerGui {
        
        SelectedBlock block;
        JSlider s;

        XRaySettings(SelectedBlock block) {
            super(block.itemBlock.getDisplayName(), block);
            this.block = block;
            s = new JSlider(0, 100);
            s.setPreferredSize(new Dimension(350, 50));
            s.setBorder(customTitledBorder("Scale"));
            s.setValue((int)(block.scale * 100));
            s.addChangeListener((e) -> {
                block.scale = (float) s.getValue() / 100;
            });
            clear.setText("Удалить");
            buttonsBar.add(clear);
            sliders.add(s, GBC);
            pack();
        }
        
        @Override public void actionPerformed(ActionEvent e) {
            if (e.getSource() == accept) {
                if (!blocks.contains(block)) {
                    blocks.add(block);
                }
            } else if (e.getSource() == clear) {
                blocks.remove(block);
            }
            configBlocks.clear();
            configBlocks.addAll(blocks.stream().map(SelectedBlock::toString).collect(Collectors.toList()));
            configBlocks.addAll(missingBlocks);
            Config.save();
            updateNEI();
            dispose();
        }

    }
    
    public class SelectedBlock extends ColorPicker {
        
        final ItemStack itemBlock;
        public float scale;
        final Block block;
        final String id;
        final int meta;
        
        SelectedBlock(ItemStack itemBlock, int color, float scale) {
            super(color);
            this.scale = scale;
            this.itemBlock = itemBlock;
            this.id = itemBlock.getItem().delegate.name();
            this.block = Block.getBlockFromItem(itemBlock.getItem());
            this.meta = resetMetaFor(block, itemBlock.getItemDamage());
        }
        
        @Override public String toString() {
            return id + ":" + meta + ":" + rgba + ":" + scale;
        }
        
    }

}