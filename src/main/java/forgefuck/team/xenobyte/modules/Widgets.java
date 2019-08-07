package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.gui.ElementAligment;
import forgefuck.team.xenobyte.api.gui.WidgetMessage;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.GuiWidget;
import forgefuck.team.xenobyte.render.Colors;
import forgefuck.team.xenobyte.render.GuiScaler;
import forgefuck.team.xenobyte.utils.TickHelper;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;

public class Widgets extends CheatModule {
    
    private Map<CheatModule, GuiWidget> keyabled, modulesInfo;
    private List<GuiWidget> infoWidgets;
    private Keyabled keyabledInfo;
    private int infoWidgetsDelay;
    
    public Widgets() {
        super("Widgets", Category.NONE, PerformMode.ON_START);
        modulesInfo = new LinkedHashMap<CheatModule, GuiWidget>();
        keyabled = new LinkedHashMap<CheatModule, GuiWidget>();
        infoWidgetsDelay = TickHelper.fourSeconds();
        infoWidgets = new ArrayList<GuiWidget>();
    }
    
    @Override public void onPostInit() {
        keyabledMessage(moduleHandler().xenoGui());
        keyabledInfo = (Keyabled) moduleHandler().getModuleByClass(Keyabled.class);
        moduleHandler().categoryedModules().filter(CheatModule::provideStateEvents).forEach(this::keyabledMessage);
    }
    
    public void widgetMessage(WidgetMessage mess) {
        infoWidgets.add(0, new GuiWidget(mess.getMessage(), mess.getMode(), ElementAligment.LEFT, Colors.TRANSPARENT_DARKEST, infoWidgetsDelay));
        updateWidgetPoses();
    }
    
    public void infoMessage(WidgetMessage mess) {
        if (mess.hasModule()) {
            modulesInfo.put(mess.getModule(), new GuiWidget(mess.getMessage(), mess.getMode(), ElementAligment.LEFT, Colors.TRANSPARENT_DARKEST, 0));
            updateInfoPoses();
        }
    }
    
    public void hideInfoMessage(CheatModule m) {
        if (modulesInfo.containsKey(m)) {
            modulesInfo.remove(m);
            updateInfoPoses();
        }
    }
    
    private void keyabledMessage(CheatModule m) {
        String out = moduleHandler().isEnabled(m) || m.hasKeyBind() ? m.getName() + (m.hasKeyBind() ? "-" + m.getKeyName() : "") : null;
        int height = 0;
        if (out != null) {
            keyabled.put(m, new GuiWidget(out, m.getMode() == PerformMode.SINGLE ? WidgetMode.INFO : moduleHandler().isEnabled(m) ? WidgetMode.SUCCESS : WidgetMode.FAIL, ElementAligment.LEFT, Colors.TRANSPARENT_DARK, 0));
        } else {
            if (keyabled.containsKey(m)) {
                keyabled.remove(m);
            } else {
                return;
            }
        }
        keyabled = sorted(keyabled);
        for (GuiWidget w : keyabled.values()) {
            w.setY(height);
            height += w.getHeight(); 
        }
    }
    
    private void updateInfoPoses() {
        if (GuiScaler.isGuiCreated()) {
            modulesInfo = sorted(modulesInfo);
            int height = 0;
            for (GuiWidget w : modulesInfo.values()) {
                height += w.getHeight();
                w.setPos(GuiScaler.scaledScreenWidth() - w.getWidth(), GuiScaler.scaledScreenHeight() - height);
            }
        }
    }
    
    private void updateWidgetPoses() {
        if (GuiScaler.isGuiCreated()) {
            infoWidgets.forEach(w -> {
                w.setY(GuiScaler.scaledScreenHeight() - w.getHeight() * (infoWidgets.indexOf(w) + 1));
            });
        }
    }
    
    private LinkedHashMap sorted(Map <CheatModule, GuiWidget> map) {
        return map.entrySet().stream()
        .sorted((e1, e2) -> Integer.compare(e2.getValue().getWidth(), e1.getValue().getWidth()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    
    @SubscribeEvent public void guiInit(InitGuiEvent.Pre e) {
        updateWidgetPoses();
        updateInfoPoses();
    }
    
    @Override public void onDrawGuiLast() {
        infoWidgets.forEach(GuiWidget::draw);
    }
    
    @Override public void onDrawGuiOverlay() {
        modulesInfo.values().forEach(GuiWidget::draw);
        if (moduleHandler().isEnabled(keyabledInfo)) {
            keyabled.values().forEach(GuiWidget::draw);
        }
    }
    
    @Override public void onTick(boolean inGame) {
        infoWidgets.removeIf(w -> -- w.delay <= 0);
    }
    
    @Override public boolean provideStateEvents() {
        return false;
    }
    
    @Override public void onModuleBinded(CheatModule m) {
        keyabledMessage(m);
    }
    
    @Override public void onModuleEnabled(CheatModule m) {
        keyabledMessage(m);
    }
    
    @Override public void onModuleUnBinded(CheatModule m) {
        keyabledMessage(m);
    }
    
    @Override public void onModuleDisabled(CheatModule m) {
        hideInfoMessage(m);
        keyabledMessage(m);
    }

}