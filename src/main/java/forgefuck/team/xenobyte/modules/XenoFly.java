package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.utils.Keys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class XenoFly extends CheatModule {
    
    @Cfg("noclip") private boolean noclip;
    @Cfg("inGui") private boolean inGui;
    @Cfg("vSpeed") private float vSpeed;
    @Cfg("hSpeed") private float hSpeed;
    
    public XenoFly() {
        super("XenoFly", Category.MOVE, PerformMode.TOGGLE);
        vSpeed = 0.4F;
        hSpeed = 0.8F;
    }
    
    @Override public void onDisabled() {
        utils.player().noClip = false;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            EntityPlayer pl = utils.player();
            float xm = 0;
            float ym = 0; 
            float zm = 0;
            if (Keys.isPressed(utils.mc().gameSettings.keyBindLeft)) {
                xm = hSpeed;
            }
            if (Keys.isPressed(utils.mc().gameSettings.keyBindRight)) {
                xm = -hSpeed;
            }
            if (Keys.isPressed(utils.mc().gameSettings.keyBindJump)) {
                ym = vSpeed;
            }
            if (Keys.isPressed(utils.mc().gameSettings.keyBindSneak)) {
                ym = -vSpeed;
            }
            if (Keys.isPressed(utils.mc().gameSettings.keyBindForward)) {
                zm = hSpeed;
            }
            if (Keys.isPressed(utils.mc().gameSettings.keyBindBack)) {
                zm = -hSpeed;
            }
            float sin = MathHelper.sin(pl.rotationYaw * (float) Math.PI / 180.0F);
            float cos = MathHelper.cos(pl.rotationYaw * (float) Math.PI / 180.0F);
            if (inGui ? true : utils.isInGameGui()) {
                pl.motionX = xm * cos - zm * sin;
                pl.motionY = ym;
                pl.motionZ = zm * cos + xm * sin;
            } else {
                pl.motionY = 0;
            }
            pl.noClip = noclip;
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Flight with setted parameters", "Полёт с заданными параметрами");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("VSpeed", (int) (vSpeed * 10), 50) {
                @Override public void onScroll(int dir, boolean withShift) {
                    vSpeed = (float) processSlider(dir, withShift) / 10;
                }
                @Override public String elementDesc() {
                    return lang.get("Vertical flight speed", "Вертикальная скорость полёта");
                }
            },
            new ScrollSlider("HSpeed", (int) (hSpeed * 10), 100) {
                @Override public void onScroll(int dir, boolean withShift) {
                    hSpeed = (float) processSlider(dir, withShift) / 10;
                }
                @Override public String elementDesc() {
                    return lang.get("Horizontal flight speed", "Горизонтальная скорость полёта");
                }
            },
            new Button("NoClip", noclip) {
                @Override public void onLeftClick() {
                    buttonValue(noclip = !noclip);
                }
                @Override public String elementDesc() {
                    return lang.get("Through walls (works best in BlinkCam)", "Сквозь стены (лучше работает в BlinkCam)");
                }
            },
            new Button("InGui", inGui) {
                @Override public void onLeftClick() {
                    buttonValue(inGui = !inGui);
                }
                @Override public String elementDesc() {
                    return lang.get("Flying with an open GUI", "Полёт при открытом GUI");
                }
            }
        );
    }

}
