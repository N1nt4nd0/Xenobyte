package forgefuck.team.xenobyte.modules;

import java.util.stream.Stream;

import org.lwjgl.input.Mouse;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class VanillaMagic extends CheatModule {
    
    private final Potion[] badEffects = new Potion[] {
        Potion.moveSlowdown,
        Potion.digSlowdown,
        Potion.blindness,
        Potion.confusion,
        Potion.weakness,
        Potion.hunger,
        Potion.poison,
        Potion.wither,
    };
    
    public VanillaMagic() {
        super("VanillaMagic", Category.PLAYER, PerformMode.TOGGLE);
    }
    
    private void doMagic(int count) {
        for (int i = 0; i <= count; i++) {
            utils.sendPacket(new C03PacketPlayer());
        }
    }
    
    private boolean hasBadEffect() {
        return Stream.of(badEffects).filter(utils.player()::isPotionActive).findFirst().isPresent();
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            ItemStack held = utils.item();
            if (utils.player().isBurning() && utils.player().onGround) {
                doMagic(10);
            } else if (!utils.player().getActivePotionEffects().isEmpty() && hasBadEffect()) {
                doMagic(500);
            } else if (held != null) {
                Item item = held.getItem();
                if ((item instanceof ItemFood || item instanceof ItemPotion) && Mouse.isButtonDown(1)) {
                    doMagic(10);
                }
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Instant: eat, self-extinguish and debuff", "Мгновенное: поедание, самотушение и дебаф");
    }

}
