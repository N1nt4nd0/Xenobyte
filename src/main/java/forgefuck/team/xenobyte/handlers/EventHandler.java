package forgefuck.team.xenobyte.handlers;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.render.GuiScaler;
import forgefuck.team.xenobyte.utils.Config;
import forgefuck.team.xenobyte.utils.EventHelper;
import forgefuck.team.xenobyte.utils.Keys;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class EventHandler {
    
    private ModuleHandler handler;
    
    public EventHandler(ModuleHandler handler) {
        this.handler = handler;
        handler.enabledModules().filter(CheatModule::provideForgeEvents).forEach(EventHelper::register);
        EventHelper.register(this);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST) public void guiInit(InitGuiEvent.Pre e) {
        GuiScaler.updateResolution();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST) public void drawGuiScreen(DrawScreenEvent.Pre e) {
        if (GuiScaler.isGuiCreated()) {
            GuiScaler.updateMouse(e.mouseX, e.mouseY);
        }
    }
    
    @SubscribeEvent public void logOut(ClientDisconnectionFromServerEvent e) {
        Config.save();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST) public void logIn(ClientConnectedToServerEvent e) {
        new PacketHandler(handler, (NetHandlerPlayClient) e.handler);
    }
    
    @SubscribeEvent public void clientTick(ClientTickEvent e) {
        if (Xeno.utils.isInGame()) {
            handler.workingModules().filter(CheatModule::hasKeyBind).filter(Keys::isPressed).findFirst().ifPresent(handler::perform);
        }
        handler.enabledModules().forEach(CheatModule::handleTick);
    }
    
    @SubscribeEvent public void drawGuiOverlay(RenderGameOverlayEvent.Post e) {
        if (e.type == ElementType.ALL && GuiScaler.isGuiCreated()) {
            GL11.glPushMatrix();
            GuiScaler.setOnTop();
            GuiScaler.setGuiScale();
            Iterator<CheatModule> iterator = handler.enabledModules().iterator();
            while (iterator.hasNext()) {
                iterator.next().onDrawGuiOverlay();
            }
            GL11.glPopMatrix();
        }
    }
    
    @SubscribeEvent public void renderTick(RenderTickEvent e) {
        switch (e.phase) {
        case START:
            GuiScaler.updateResolution();
            break;
        case END:
            if (GuiScaler.isGuiCreated()) {
                GL11.glPushMatrix();
                GuiScaler.setOnTop();
                GuiScaler.setGuiScale();
                GL11.glDisable(GL11.GL_LIGHTING);
                Iterator<CheatModule> iterator = handler.enabledModules().iterator();
                while (iterator.hasNext()) {
                    iterator.next().onDrawGuiLast();
                }
                GL11.glPopMatrix();
            }
        }
    }

}