package forgefuck.team.xenobyte.gui.click.elements;

import forgefuck.team.xenobyte.render.Colors;
import forgefuck.team.xenobyte.utils.Delimitter;

public class ScrollSlider extends Button {
    
    private int processed, shiftFactor, indent;
    private Delimitter counter;
    
    public ScrollSlider(String text, int value, int max) {
        this(text, value, 1, max);
    }
    
    public ScrollSlider(String text, int value, int min, int max) {
        this(text, value, min, max, 6);
    }
    
    public ScrollSlider(String text, int value, int min, int max, int indent) {
        super(text, value);
        shiftFactor = (int)Math.ceil((float)(max - min) / 10);
        counter = new Delimitter(value, min, max);
        this.indent = indent < 0 ? 0 : indent;
        processSlider(0, false);
    }
    
    public int processSlider(int step, boolean withShift) {
        buttonValue(processed = counter.calc(withShift ? step * shiftFactor : step));
        return processed;
    }
    
    public int getSliderX() {
        return getX() + indent + (int)((float) processed * ((float) (getWidth() - indent * 2) / (float) counter.getMax()));
    }
    
    @Override public void playClick() {}
    
    @Override public void onDraw() {
        super.onDraw();
        render.GUI.drawRect(getX() + indent, getY() + getHeight() - 1, getSliderX(), getMaxY(), Colors.ORANGE);
    }

}