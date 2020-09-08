package forgefuck.team.xenobyte.modules;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.io.Closer;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class OpenCreative extends CheatModule {
    
    @Cfg("inRadius") private boolean inRadius;
    
    public OpenCreative() {
        super("OpenCreative", Category.MODS, PerformMode.SINGLE);
    }
    
    private void sendOpenPacket(EntityPlayer pl) {
        try {
            Class byteUtils = Class.forName("openmods.utils.ByteUtils");
            ByteBuf payload = Unpooled.buffer();
            Closer closer = Closer.create();
            OutputStream raw = (OutputStream)closer.register((Closeable)new ByteBufOutputStream(payload));
            OutputStream compressed = (OutputStream)closer.register((Closeable)new GZIPOutputStream(raw));
            DataOutputStream output = new DataOutputStream(compressed);
            output.writeUTF("rpc_methods");
            byteUtils.getMethod("writeVLI", DataOutput.class, int.class).invoke(null, output, 1);
            output.writeUTF("net.minecraft.entity.player.EntityPlayer;" + utils.getObfuscated("func_71033_a", "setGameType") + ";(Lnet/minecraft/world/WorldSettings$GameType;)V");
            output.writeInt(1337);
            closer.close();
            utils.sendPacket(new FMLProxyPacket(payload.copy(), "OpenMods|I"));
            payload = Unpooled.buffer();
            output = new DataOutputStream(new ByteBufOutputStream(payload));
            byteUtils.getMethod("writeVLI", DataOutput.class, int.class).invoke(null, output, 0);
            output.writeInt(0);
            output.writeInt(pl.getEntityId());
            byteUtils.getMethod("writeVLI", DataOutput.class, int.class).invoke(null, output, 0);
            byteUtils.getMethod("writeVLI", DataOutput.class, int.class).invoke(null, output, utils.isInCreative(pl) ? 1 : 2);
            utils.sendPacket(new FMLProxyPacket(payload.copy(), "OpenMods|RPC"));
        } catch(Exception e) {}
    }
    
    @Override public void onPerform(PerformSource src) {
        if (inRadius) {
            utils.nearEntityes().filter(e -> e instanceof EntityPlayer).map(e -> (EntityPlayer) e).forEach(this::sendOpenPacket);
        } else {
            Entity checkEntity = utils.entity();
            EntityPlayer pl = checkEntity instanceof EntityPlayer ? (EntityPlayer) checkEntity : utils.player();
            sendOpenPacket(pl);
        }
    }

    @Override public boolean isWorking() {
        return Loader.isModLoaded("OpenMods");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Changing your Gamemode or players (if kicks during execution - fixed)", "Изменение своего Gamemode или игроков (если кикает при выполнении - фикс)");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("InRadius", inRadius) {
                @Override public void onLeftClick() {
                    buttonValue(inRadius = !inRadius);
                }
                @Override public String elementDesc() {
                    return lang.get("By radius or sight", "По радиусу или взгляду");
                }
            }
        );
    }

}
