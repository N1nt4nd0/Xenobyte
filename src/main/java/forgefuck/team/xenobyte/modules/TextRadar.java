package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.entity.player.EntityPlayer;

public class TextRadar extends CheatModule {
    
    public TextRadar() {
        super("TextRadar", Category.MISC, PerformMode.TOGGLE);
    }
    
    @Override public int tickDelay() {
        return 10;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            int[] my = utils.myCoords();
            StringBuilder out = new StringBuilder();
            utils.nearEntityes(200)
            .filter(e -> e instanceof EntityPlayer && !e.isDead)
            .forEach(e -> {
                int[] pl = utils.coords(e);
                int dist = (int)Math.sqrt((my[0] - pl[0])*(my[0] - pl[0]) + (my[1] - pl[1])*(my[1] - pl[1]) + (my[2] - pl[2])*(my[2] - pl[2]));
                out.append("[" + utils.name(e) + " " + dist + "]");
            });
            boolean empty = out.length() == 0;
            infoMessage(empty ? lang.get("nobody", "пусто") : out.toString(), empty ? WidgetMode.SUCCESS : WidgetMode.FAIL);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Displays the nearest players and the distance to them on the info panel", "Выводит на инфопанель ближайших игроков и расстояние до них");
    }

}