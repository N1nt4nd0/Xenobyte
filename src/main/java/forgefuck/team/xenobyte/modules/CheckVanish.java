package forgefuck.team.xenobyte.modules;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.WidgetMessage;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.utils.NetUtils;
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
    
    private INetHandlerStatusClient simpleHandler;
    @Cfg("withNames") private boolean withNames;
    private List<String> vanishNames;
    private WidgetMessage outMessage;
    private boolean doMcsrv;
    
    public CheckVanish() {
        super("CheckVanish", Category.MISC, PerformMode.TOGGLE);
        outMessage = new WidgetMessage(this, "...", WidgetMode.INFO);
        vanishNames = new ArrayList<String>();
        simpleHandler = new INetHandlerStatusClient() {
            @Override public void handleServerInfo(S00PacketServerInfo infoPacket) {
                ServerStatusResponse response = infoPacket.func_149294_c();
                if (response.func_151318_b() != null) {
                    int vCount = response.func_151318_b().func_151333_b() - utils.tabList().size();
                    int count = vCount > 0 ? vCount : 0;
                    outMessage = new WidgetMessage(CheckVanish.this, count + (withNames ? " " + vanishNames : ""), count > 0 ? WidgetMode.FAIL : WidgetMode.SUCCESS);
                }
            }
            @Override public void onConnectionStateTransition(EnumConnectionState e, EnumConnectionState e1) {}
            @Override public void onDisconnect(IChatComponent var) {}
            @Override public void handlePong(S01PacketPong var) {}
            @Override public void onNetworkTick() {}
        };
        withNames = true;
        doMcsrv = true;
    }

    private void simpleRequest(String ip) {
        try {
            ServerAddress addr = ServerAddress.func_78860_a(ip);
            NetworkManager manager = NetworkManager.provideLanClient(InetAddress.getByName(addr.getIP()), addr.getPort());
            manager.setNetHandler(simpleHandler);
            manager.scheduleOutboundPacket(new C00Handshake(5, addr.getIP(), addr.getPort(), EnumConnectionState.STATUS), new GenericFutureListener[0]);
            manager.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
        } catch (Exception ex) {}
    }
    
    private List<String> mcsrvRequest(String ip) {
        List<String> pls = new ArrayList<String>();
        JsonObject check = NetUtils.getServerInfo(ip);
        if (check != null && check.has("players")) {
            JsonObject players = check.getAsJsonObject("players");
            if (players != null && players.has("list")) {
                players.getAsJsonArray("list").forEach(p -> {
                    pls.add(p.getAsString());
                });
            }
        }
        return pls;
    }
    
    @Override public void onPostInit() {
        new Thread(() -> {
            while (true) {
                if (utils.isInGame()) {
                    ServerData data = utils.mc().func_147104_D();
                    if (data != null) {
                        simpleRequest(data.serverIP);
                        if (doMcsrv && withNames) {
                            List<String> check = mcsrvRequest(data.serverIP);
                            doMcsrv = !check.isEmpty();
                            vanishNames.clear();
                            check.stream().filter(n -> !utils.tabList().contains(n)).forEach(vanishNames::add);
                        }
                    } else {
                        outMessage = new WidgetMessage(this, "SP", WidgetMode.INFO);
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch(Exception e) {}
            }
        }).start();
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            moduleHandler().widgets().infoMessage(outMessage);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Displays the number of invisible players on the info panel", "Выводит на инфопанель количество игроков в ванише");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("WithNames", withNames) {
                @Override public void onLeftClick() {
                    buttonValue(withNames = !withNames);
                }
                @Override public String elementDesc() {
                    return lang.get("Output names whenever possible (beta)", "По возможности выводить имена (beta)");
                }
            }
        );
    }

}