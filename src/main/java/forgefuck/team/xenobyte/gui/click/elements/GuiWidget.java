package forgefuck.team.xenobyte.gui.click.elements;

import forgefuck.team.xenobyte.api.gui.ElementAligment;
import forgefuck.team.xenobyte.api.gui.TextElement;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.render.Colors;

public class GuiWidget extends TextElement {

    private static int indicatorWidth = 2;
    private ElementAligment aligment;
    private final int bgColor;
    private WidgetMode mode;
    public int delay;
    
    public GuiWidget(String text, WidgetMode mode, ElementAligment indicatorAligment, int bgColor, int delay) {
        super(text, indicatorAligment == ElementAligment.LEFT ? ElementAligment.RIGHT : ElementAligment.LEFT, indicatorWidth + (indicatorAligment == ElementAligment.LEFT ? 1 : 0), 0);
        this.aligment = indicatorAligment;
        this.bgColor = bgColor;
        this.delay = delay;
        this.mode = mode;
    }
    
    @Override public void draw() {
        render.GUI.drawRect(getX(), getY(), getMaxX(), getMaxY(), bgColor);
        int indicatorX = aligment == ElementAligment.LEFT ? getX() : getMaxX() - indicatorWidth;
        render.GUI.drawRect(indicatorX, getY(), indicatorX + indicatorWidth, getMaxY(), mode.getColor());
        render.GUI.xenoFont().drawString(getText(), getTextX(), getY(), Colors.WHITE);
    }

}