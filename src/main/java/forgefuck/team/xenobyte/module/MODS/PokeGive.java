package forgefuck.team.xenobyte.module.MODS;

import java.lang.reflect.Array;

import com.pixelmonmod.pixelmon.client.ServerStorageDisplay;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;

public class PokeGive extends CheatModule {
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        try {
            int[] pId = (int[]) Class.forName("com.pixelmonmod.pixelmon.comm.PixelmonData").getField("pokemonID").get(Array.get(Class.forName("com.pixelmonmod.pixelmon.client.ServerStorageDisplay").getField("pokemon").get(null), 0));
            utils.sendPacket("pixelmon", (byte) 25, pId[0], pId[1], giveSelector().givedItem());
        } catch(Exception e) {
            utils.chatMessage(e.getMessage());
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("pixelmon");
    }
    
    @Override public String moduleDesc() {
        return "Выдача предмета в инвентарь (первая активация - в покемона, последующие - в инвентарь игрока)";
    }

}