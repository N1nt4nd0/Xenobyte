package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class BlinkCam extends CheatModule {
    
    private EntityOtherPlayerMP fake;
    
    public BlinkCam() {
        super("BlinkCam", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onDisabled() {
        utils.world().removeEntity(fake);
        fake = null;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame && fake == null) {
            EntityPlayer pl = utils.player();
            fake = new EntityOtherPlayerMP(utils.world(), pl.getGameProfile());
            fake.setPositionAndRotation(pl.posX, pl.boundingBox.minY, pl.posZ, pl.rotationYaw, pl.rotationPitch);
            fake.setRotationYawHead(pl.rotationYawHead);
            fake.inventory = pl.inventory;
            utils.world().spawnEntityInWorld(fake);
        }
    }

    @Override public boolean doSendPacket(Packet packet) {
        if (packet instanceof C03PacketPlayer) {
            return false;
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return lang.get("Aka Blink, aka FreeCam", "Он же Blink, он же FreeCam");
    }
    
}
