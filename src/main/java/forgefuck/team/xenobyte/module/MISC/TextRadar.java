package forgefuck.team.xenobyte.module.MISC;

import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.CheatModule;
import net.minecraft.entity.player.EntityPlayer;

public class TextRadar extends CheatModule {
    
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
            infoMessage(empty ? "[пусто]" : out.toString(), empty ? WidgetMode.SUCCESS : WidgetMode.FAIL);
        }
    }
    
    @Override public String moduleDesc() {
        return "Выводит на инфопанель ближайших игроков и расстояние до них";
    }

}