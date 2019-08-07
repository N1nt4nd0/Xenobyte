package forgefuck.team.xenobyte.api.gui;

import forgefuck.team.xenobyte.api.render.IDraw;
import forgefuck.team.xenobyte.render.GuiScaler;

public abstract class GuiElement extends ElementAbility implements IDraw {
    
    private boolean hovered, selected, hoverCheck;
    private int x, y, width, height;
    private String desc;
    
    public GuiElement() {
        desc = elementDesc();
        onInit();
    }
    
    public void setPos(int x, int y) {
        setX(x);
        setY(y);
    }
    
    public void shift(int x, int y) {
        this.x += x;
        this.y += y; 
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getMaxX() {
        return getX() + getWidth();
    }
    
    public int getMaxY() {
        return getY() + getHeight();
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public boolean isHovered() {
        return hovered;
    }
    
    public void scroll(int dir, boolean withShift) {
        if (isHovered()) {
            onScroll(dir, withShift);
        }
    }
    
    public void keyTyped(char symb, int key) {
        if (isHovered()) {
            onKeyTyped(symb, key);
        }
    }
    
    public boolean click(int key) {
        if (isHovered()) {
            switch (key) {
            case 0:
                onLeftClick();
                break;
            case 1:
                onRightClick();
                break;
            case 2:
                onScrollClick();
                break;
            }
            return true;
        }
        return false;
    }
    
    public void drawDesc() {
        if (desc != null && isHovered()) {
            render.GUI.drawDesc(desc);
        }
    }
    
    @Override public void draw() {
        int mouseX = GuiScaler.mouseX();
        int mouseY = GuiScaler.mouseY();
        if (hovered = (mouseX >= getX() && mouseX < getMaxX()) && (mouseY >= getY() && mouseY < getMaxY())) {
            if (!hoverCheck) {
                hoverCheck = true;
                onHovered();
            }
        } else {
            if (hoverCheck) {
                hoverCheck = false;
                onDishovered();
            }
        }
        onDraw();
    }

}