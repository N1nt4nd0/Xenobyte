package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.utils.Keys;

public class BunnyHop extends CheatModule {

    @Cfg("hSpeed") private float hSpeed;

    public BunnyHop() {
        super("BunnyHop", Category.MOVE, PerformMode.TOGGLE);
        hSpeed = 1F;
    }

    @Override public void onTick(boolean inGame) {
        if (inGame) {
            if (utils.player().isSneaking() || utils.player().isInWater() || Keys.isPressed(utils.mc().gameSettings.keyBindJump) || Keys.isPressed(utils.mc().gameSettings.keyBindBack)) {
                return;
            }
            if (Keys.isPressed(utils.mc().gameSettings.keyBindForward) || Keys.isPressed(utils.mc().gameSettings.keyBindLeft) || Keys.isPressed(utils.mc().gameSettings.keyBindRight)) {
                if (utils.player().onGround) {
                    utils.player().jump();
                    utils.player().setSprinting(true);
                    getStrafe(hSpeed * 5 * utils.player().capabilities.getWalkSpeed());
                }
            }
        }
    }

    private void getStrafe(double speed) {
        double yaw = utils.player().rotationYaw;
        boolean isMoving = utils.player().moveForward != 0 || utils.player().moveStrafing != 0;
        boolean isMovingForward = utils.player().moveForward > 0;
        boolean isMovingBackward = utils.player().moveForward < 0;
        boolean isMovingRight = utils.player().moveStrafing > 0;
        boolean isMovingLeft = utils.player().moveStrafing < 0;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45;
            } else if (isMovingForward) {
                yaw -= 45;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135;
            } else if (isMovingBackward) {
                yaw -= 135;
            }
            yaw = Math.toRadians(yaw);
            utils.player().motionX = -Math.sin(yaw) * speed;
            utils.player().motionZ = Math.cos(yaw) * speed;
        }
    }

    @Override public String moduleDesc() {
        return lang.get("Acceleration through the bunny", "Ускорение через распрыжку");
    }

    @Override public Panel settingPanel() {
        return new Panel(new ScrollSlider("Speed", (int) (hSpeed * 10), 50) {
            @Override
            public void onScroll(int dir, boolean withShift) {
                hSpeed = (float) processSlider(dir, withShift) / 10;
            }

            @Override
            public String elementDesc() {
                return lang.get("Bounce speed", "Cкорость распрыжки");
            }
        });
    }

}