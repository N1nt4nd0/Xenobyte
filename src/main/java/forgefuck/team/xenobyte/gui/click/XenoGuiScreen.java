package forgefuck.team.xenobyte.gui.click;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.gui.ElementAligment;
import forgefuck.team.xenobyte.api.gui.GuiElement;
import forgefuck.team.xenobyte.api.gui.PanelLayout;
import forgefuck.team.xenobyte.api.gui.PanelSorting;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.handlers.ModuleHandler;
import forgefuck.team.xenobyte.render.Colors;
import forgefuck.team.xenobyte.render.GuiScaler;
import forgefuck.team.xenobyte.utils.Config;
import forgefuck.team.xenobyte.utils.Keys;
import net.minecraft.client.gui.GuiScreen;

public class XenoGuiScreen extends GuiScreen {
    
    private Panel mainPanel, tempSetting;
    private List<GuiElement> elements;
    
    public XenoGuiScreen(ModuleHandler HAND) {
        elements = new CopyOnWriteArrayList<GuiElement>();
        mainPanel = new Panel(new Button(Xeno.mod_name, HAND.xenoGui().getKeyName(), ElementAligment.CENTER, Colors.ORANGE, Colors.ORANGE, Colors.ORANGE, Colors.WHITE, Colors.WHITE, Colors.WHITE) {
            @Override public void playClick() {}
            @Override public void onHovered() {
                hideElements();
            }
            @Override public void onKeyTyped(char symb, int key) {
                HAND.bind(HAND.xenoGui(), this, key);
            }
        }, PanelLayout.HORIZONTAL, PanelSorting.WIDTH);
        HAND.categoryedModules()
        .collect(Collectors.groupingBy(CheatModule::getCategory))
        .forEach((CAT, MODS) -> {
            Panel categoryPanel = new Panel(PanelLayout.VERTICAL, PanelSorting.ALPHABET);
            MODS.stream().
            forEach(MOD -> {
                Panel settingsPanel = MOD.settingPanel();
                Button modButton = new Button(MOD.getName(), MOD.getKeyName()) {
                    @Override public void onInit() {
                        setSelected(HAND.isEnabled(MOD));
                    }
                    @Override public void onKeyTyped(char symb, int key) {
                        HAND.bind(MOD, this, key);
                    }
                    @Override public void onLeftClick() {
                        HAND.perform(MOD, this);
                    }
                    @Override public String elementDesc() {
                        return MOD.moduleDesc();
                    }
                    @Override public void onDraw() {
                        super.onDraw();
                        if (settingsPanel != null) {
                            int startX = isShowing(settingsPanel) ? getMaxX() + settingsPanel.getWidth() : getMaxX() - 1;
                            render.GUI.drawRect(startX , getY(), startX + 1, isShowing(settingsPanel) ? settingsPanel.getMaxY() : getMaxY(), Colors.ORANGE);
                        }
                    }
                    @Override public void onHovered() {
                        if (tempSetting != null && tempSetting != settingsPanel) {
                            hideElement(tempSetting);
                        }
                        if (settingsPanel != null && !isShowing(settingsPanel)) {
                            tempSetting = settingsPanel;
                            settingsPanel.setPos(getMaxX() + settingsPanel.getWidth() > GuiScaler.scaledScreenWidth() ? getX() - settingsPanel.getWidth() : getMaxX(), getY() + settingsPanel.getHeight() > GuiScaler.scaledScreenHeight() ? getMaxY() - settingsPanel.getHeight() : getY());
                            showElement(settingsPanel);
                        }
                    }
                };
                categoryPanel.add(modButton);
            });
            categoryPanel.pack();
            mainPanel.add(new Button(CAT) {
                @Override public void onHovered() {
                    if(!isShowing(categoryPanel)) {
                        hideElements();
                        setSelected(true);
                        categoryPanel.setPos(getX(), getMaxY());
                        showElement(categoryPanel);
                    }
                }
            });
        });
        mainPanel.pack();
        showElement(mainPanel);
    }
    
    private boolean isShowing(GuiElement element) {
        return elements.contains(element);
    }
    
    private void showElement(GuiElement element) {
        elements.add(element);
        element.onShow();
    }
    
    private void hideElement(GuiElement element) {
        elements.remove(element);
        element.onHide();
    }
    
    private void hideElements() {
        elements.stream().filter(e -> e != mainPanel).forEach(this::hideElement);
        mainPanel.setSelected(false);
    }

    @Override public void drawScreen(int mouseX, int mouseY, float ticks) {
        drawGradientRect(0, 0, width, height, Colors.WHITE_BG, Colors.NONE);
        GL11.glPushMatrix();
        GuiScaler.setGuiScale();
        elements.forEach(GuiElement::draw);
        if (Keys.isShiftDown()) {
            elements.forEach(GuiElement::drawDesc);
        }
        GL11.glPopMatrix();
    }
    
    @Override public void keyTyped(char ch, int key) {
        super.keyTyped(ch, key);
        if (Keys.isAvalible(key)) {
            elements.forEach(e -> e.keyTyped(ch, key));
        }
    }
    
    @Override protected void mouseClicked(int mouseX, int mouseY, int key) {
        if(!elements.stream().anyMatch(p -> p.click(key))) {
            Xeno.utils.closeGuis();
        };
    }
    
    @Override public void handleMouseInput() {
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) {
            int dir = scroll > 0 ? 1 : -1;
            elements.forEach(e -> e.scroll(dir, isShiftKeyDown()));
        }
    }
    
    @Override public void initGui() {
        mainPanel.setPos(GuiScaler.scaledScreenWidth() / 2 - mainPanel.getWidth() / 2, 0);
        hideElements();
    }
    
    @Override public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override public void onGuiClosed() {
        Config.save();
    }

}