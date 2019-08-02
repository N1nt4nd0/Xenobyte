package forgefuck.team.xenobyte.module.MISC;

import java.net.InetAddress;

import forgefuck.team.xenobyte.api.gui.WidgetMessage;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.utils.TickHelper;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.IChatComponent;

public class CheckVanish extends CheatModule {
    
    private INetHandlerStatusClient vanishHandler;
    private WidgetMessage outMessage;
    
    public CheckVanish() {
        outMessage = new WidgetMessage(this, "[...]", WidgetMode.INFO);
        vanishHandler = new INetHandlerStatusClient() {
            @Override public void handleServerInfo(S00PacketServerInfo infoPacket) {
                ServerStatusResponse response = infoPacket.func_149294_c();
                if (response.func_151318_b() != null) {
                    int vCount = response.func_151318_b().func_151333_b() - utils.player().sendQueue.playerInfoList.size();
                    boolean isVanish = vCount > 0;
                    outMessage = new WidgetMessage(CheckVanish.this, "[" + (isVanish ? vCount : 0) + "]", isVanish ? WidgetMode.FAIL : WidgetMode.SUCCESS);
                }
            }
            @Override public void onConnectionStateTransition(EnumConnectionState e, EnumConnectionState e1) {}
            @Override public void onDisconnect(IChatComponent var) {}
            @Override public void handlePong(S01PacketPong var) {}
            @Override public void onNetworkTick() {}
        };
    }
    
    private void sendCheckRequest(ServerData data) {
        if (data != null) {
            try {
                ServerAddress addr = ServerAddress.func_78860_a(data.serverIP);
                NetworkManager manager = NetworkManager.provideLanClient(InetAddress.getByName(addr.getIP()), addr.getPort());
                manager.setNetHandler(vanishHandler);
                manager.scheduleOutboundPacket(new C00Handshake(5, addr.getIP(), addr.getPort(), EnumConnectionState.STATUS), new GenericFutureListener[0]);
                manager.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
            } catch (Exception ex) {}
        } else {
            outMessage = new WidgetMessage(this, "[Singleplayer]", WidgetMode.INFO);
        }
    }
    
    @Override public int tickDelay() {
        return TickHelper.threeSeconds();
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            new Thread(() -> sendCheckRequest(utils.mc().func_147104_D())).start();
            if (outMessage != null) {
                moduleHandler().widgets().infoMessage(outMessage);
            }
        }
    }
    
    @Override public String moduleDesc() {
        return "Выводит на инфопанель количество игроков в ванише";
    }

}