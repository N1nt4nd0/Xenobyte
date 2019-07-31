package forgefuck.team.xenobyte.module.MISC;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class AdvancedTooltip extends CheatModule {
    
    @SubscribeEvent public void tooltipHook(ItemTooltipEvent e) {
        ItemStack item = e.itemStack;
        e.toolTip.add(utils.stringId(item) + " (" + Item.getIdFromItem(item.getItem()) + ":" + item.getItemDamage() + ")");
    }
    
    @Override public String moduleDesc() {
        return "Отображает в описании предмета его метаданные";
    }

}