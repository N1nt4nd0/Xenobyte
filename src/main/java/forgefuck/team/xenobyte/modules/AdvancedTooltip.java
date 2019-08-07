package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class AdvancedTooltip extends CheatModule {
    
    public AdvancedTooltip() {
        super("AdvancedTooltip", Category.MISC, PerformMode.TOGGLE);
    }
    
    @SubscribeEvent public void tooltipHook(ItemTooltipEvent e) {
        ItemStack item = e.itemStack;
        e.toolTip.add(utils.stringId(item) + " (" + Item.getIdFromItem(item.getItem()) + ":" + item.getItemDamage() + ")");
    }
    
    @Override public String moduleDesc() {
        return "Отображает в описании предмета его метаданные";
    }

}