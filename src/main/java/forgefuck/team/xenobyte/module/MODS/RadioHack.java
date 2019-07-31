package forgefuck.team.xenobyte.module.MODS;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

public class RadioHack extends CheatModule {
    
    @Cfg private List<String> urls;
    @Cfg private boolean kick;
    
    public RadioHack() {
        (urls = new ArrayList<String>()).add("https://files.catbox.moe/s0wkfi.mp3");
    }
    
    private void sendRadioPacket(TileEntity tile, boolean playing) {
        String url = urls.get(0);
        if (!url.isEmpty()) {
            utils.sendPacket("DragonsRadioMod", (byte) 0, 13, (double) tile.xCoord, (double) tile.yCoord, (double) tile.zCoord, utils.worldId(), url.length(), url.getBytes(), playing, (float) 1, (double) 0, (double) 0, (double) 0);
        }
    }
    
    private boolean isRadioTile(TileEntity tile) {
        try {
            return Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio").isInstance(tile);
        } catch(Exception e) {
            return false;
        }
    }
    
    @Override public PerformMode performMode() {
        return PerformMode.SINGLE;
    }
    
    @Override public void onPerform(PerformSource src) {
        for (TileEntity tile : utils.nearTiles()) {
            if (kick && !isRadioTile(tile)) {
                sendRadioPacket(tile, true);
                break;
            } else if (isRadioTile(tile)) {
                sendRadioPacket(tile, false);
                sendRadioPacket(tile, true);
            }
        }
    }
    
    @Override public boolean forceEnabled() {
        return true;
    }
    
    @Override public boolean provideStateEvents() {
        return false;
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("DragonsRadioMod");
    }
    
    @Override public boolean doReceivePacket(Packet packet) {
        if (kick) {
            if (packet instanceof FMLProxyPacket) {
                if ("DragonsRadioMod".equals(((FMLProxyPacket) packet).channel())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return "Замена ссылки в находящихся вблизи блоках радио";
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("RadioUrl") {
                   @Override public void onLeftClick() {
                       new UserInput("Radio url", urls, InputType.SINGLE_STRING).showFrame();
                   }
            },
            new Button("KickMode", kick) {
                   @Override public void onLeftClick() {
                       buttonValue(kick = !kick);
                }
                   @Override public String elementDesc() {
                    return "Режим кика ближайших игроков";
                }
            }
        );
    }

}
