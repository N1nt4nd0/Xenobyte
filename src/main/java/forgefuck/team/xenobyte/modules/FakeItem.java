package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class FakeItem extends CheatModule {
    
    @Cfg("slot") private int slot;
    
    public FakeItem() {
        super("FakeItem", Category.PLAYER, PerformMode.TOGGLE);
        slot = 1;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.sendPacket(new C09PacketHeldItemChange(slot - 1));
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Players will see an item from the specified slot in their hand", "Игроки будут видеть в руке предмет из заданного слота");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Slot", slot, 9) {
                @Override public void onScroll(int dir, boolean withShift) {
                    slot = processSlider(dir, withShift);
                }
            }
        );
    }

}
