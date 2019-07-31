package forgefuck.team.xenobyte.handlers;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.utils.XenoLogger;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;

public class PacketHandler {
    
    public PacketHandler(ModuleHandler handler, NetHandlerPlayClient clientHandler) {
        if (handler != null && clientHandler != null) {
            clientHandler.getNetworkManager().channel().pipeline().addBefore("packet_handler", Xeno.mod_id + "_ph", new ChannelDuplexHandler() {
                @Override public void channelRead(ChannelHandlerContext ctx, Object in) throws Exception {
                    if (handler.enabledModules().allMatch(m -> m.doReceivePacket((Packet) in))) {
                        super.channelRead(ctx, in);
                    }
                }
                @Override public void write(ChannelHandlerContext ctx, Object out, ChannelPromise pr) throws Exception {
                    if (handler.enabledModules().allMatch(m -> m.doSendPacket((Packet) out))) {
                        super.write(ctx, out, pr);
                    }
                }
                @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                    XenoLogger.info("PacketHandler зарегистрирован");
                }
            });
        }
    }

}
