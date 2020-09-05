package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.client.event.MouseEvent;

public class ShearDupe extends CheatModule {
    
    public ShearDupe() {
        super("ShearDupe", Category.MISC, PerformMode.TOGGLE);
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 1 && e.buttonstate) {
            Entity ent = utils.entity();
            ItemStack handItem = utils.item();
            if (ent != null && !utils.isInCreative() && handItem != null && handItem.getItem() instanceof ItemShears) {
                for (int i = 0; i < 2000; i++) {
                    utils.sendPacket(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.INTERACT));
                }
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Dupe cows and mushrooms (right click with scissors on a mushroom cow)", "Дюп коров и грибов (ПКМ ножницами по грибной корове)");
    }

}
