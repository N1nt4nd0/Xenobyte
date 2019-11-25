package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.utils.TickHelper;

public class AutoDrop extends CheatModule {
    
    @Cfg("allStack") private boolean allStack;
    @Cfg("delay") private int delay;
    @Cfg("slot") private int slot;
    
    public AutoDrop() {
        super("AutoDrop", Category.PLAYER, PerformMode.TOGGLE);
        slot = 1;
    }
    
    @Override public int tickDelay() {
        return delay;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.dropSlot(utils.mySlotsCount() + (slot - 1), allStack);
        }
    }
    
    @Override public String moduleDesc() {
        return "Дроп предмета из активного слота";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Delay", delay, 0, TickHelper.ONE_SEC) {
                @Override public void onScroll(int dir, boolean withShift) {
                    delay = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Задержка дропа предмета";
                }
            },
            new ScrollSlider("Slot", slot, 9) {
                @Override public void onScroll(int dir, boolean withShift) {
                    slot = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return "Слот из которого дропаются предметы";
                }
            },
            new Button("AllStack", allStack) {
                @Override public void onLeftClick() {
                    buttonValue(allStack = !allStack);
                }
                @Override public String elementDesc() {
                    return "Дропать весь стак или по одному предмету";
                }
            }
        );
    }

}