package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;

public class CreativeGive extends CheatModule {
    
    @Cfg("drop") boolean drop;
    
    public CreativeGive() {
        super("CreativeGive", Category.PLAYER, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        utils.creativeGive(drop ? -1 : utils.activeSlot(), giveSelector().givedItem());
    }
    
    @Override public String moduleDesc() {
        return "Выдача предмета (только в креативе)";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("DropItem", drop) {
                @Override public void onLeftClick() {
                    buttonValue(drop = !drop);
                }
                @Override public String elementDesc() {
                    return "Выбрасывание предмета при выдаче, иначе в активный слот";
                }
            }
        );
    }

}