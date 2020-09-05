package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;
import net.minecraft.tileentity.TileEntity;

public class RadioHack extends CheatModule {
    
    @Cfg("urls") private List<String> urls;
    @Cfg("kick") private boolean kick;
    
    public RadioHack() {
        super("RadioHack", Category.MODS, PerformMode.SINGLE);
        urls = new ArrayList<String>();
        urls.add("https://files.catbox.moe/s0wkfi.mp3");
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
    
    public boolean getKick() {
        return kick;
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
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("DragonsRadioMod");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Replacing links in nearby radio blocks", "Замена ссылки в находящихся вблизи блоках радио");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("RadioUrl") {
                @Override public void onLeftClick() {
                    new UserInput(lang.get("Url", "Ссылка"), urls, InputType.SINGLE_STRING).showFrame();
                }
            },
            new Button("KickMode", kick) {
                @Override public void onLeftClick() {
                    buttonValue(kick = !kick);
                }
                @Override public String elementDesc() {
                    return lang.get("Kick nearby players", "Режим кика ближайших игроков");
                }
            }
        );
    }

}