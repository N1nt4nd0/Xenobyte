package forgefuck.team.xenobyte.api.gui;

public abstract class TextElement extends GuiElement {
    
    private ElementAligment aligment;
    private int indentX, indentY;
    private String text;
    
    public TextElement(String text) {
        this(text, ElementAligment.CENTER);
    }
    
    public TextElement(String text, ElementAligment aligment) {
        this(text, aligment, 0, 0);
    }
    
    public TextElement(String text, ElementAligment aligment, int indentX, int indentY) {
        this.aligment = aligment;
        this.indentX = indentX;
        this.indentY = indentY;
        setText(text);
    }
    
    public void setText(String text) {
        setWidth(render.GUI.xenoFont().textWidth(text) + indentX);
        setHeight(render.GUI.xenoFont().fontHeight() + indentY);
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    protected int getTextY() {
        return  getY() + indentY / 2;
    }
    
    protected int getTextX() {
        return getTextX(text);
    }
    
    protected int getTextX(String in) {
        switch (aligment) {
        case CENTER:
            return getX() + (getWidth() - render.GUI.xenoFont().textWidth(in)) / 2;
        case RIGHT:
            return getX() + (getWidth() - render.GUI.xenoFont().textWidth(in));
        default:
            return getX();
        }
    }
}
