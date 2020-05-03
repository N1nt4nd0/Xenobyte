package forgefuck.team.xenobyte.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.reflect.TypeToken;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.ASMEventHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.IEventListener;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraftforge.common.MinecraftForge;

public class EventHelper {
    
    public static void register(Object o) {
        registerBus(FMLCommonHandler.instance().bus(), o);
        registerBus(MinecraftForge.EVENT_BUS, o);
    }
    
    public static void unregister(Object o) {
        FMLCommonHandler.instance().bus().unregister(o);
        MinecraftForge.EVENT_BUS.unregister(o);
    }
    
    private static void registerBus(EventBus bus, Object target) {
        ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = ReflectionHelper.getPrivateValue(EventBus.class, bus, 1);
        Map<Object,ModContainer> listenerOwners = ReflectionHelper.getPrivateValue(EventBus.class, bus, 2);
        if (!listeners.containsKey(target)) {
            ModContainer activeModContainer =  Loader.instance().getMinecraftModContainer();
            listenerOwners.put(target, activeModContainer);
            ReflectionHelper.setPrivateValue(EventBus.class, bus, listenerOwners, 2);
            Set<? extends Class<?>> supers = TypeToken.of(target.getClass()).getTypes().rawTypes();
            for (Method method : target.getClass().getMethods()) {
                for (Class<?> cls : supers) {
                    try {
                        Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                        if (real.isAnnotationPresent(SubscribeEvent.class)) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            Class<?> eventType = parameterTypes[0];
                            int busID = ReflectionHelper.getPrivateValue(EventBus.class, bus, 3);
                            Constructor<?> ctr = eventType.getConstructor();
                            ctr.setAccessible(true);
                            Event event = (Event)ctr.newInstance();
                            ASMEventHandler listener = new ASMEventHandler(target, method, activeModContainer);
                            event.getListenerList().register(busID, listener.getPriority(), listener);
                            ArrayList<IEventListener> others = listeners.get(target);
                            if (others == null) {
                                others = new ArrayList<IEventListener>();
                                listeners.put(target, others);
                                ReflectionHelper.setPrivateValue(EventBus.class, bus, listeners, 1);
                            }
                            others.add(listener);
                            break;
                        }
                    }
                    catch (Exception e) {}
                }
            }
        }        
    }

}
