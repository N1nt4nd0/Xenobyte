package forgefuck.team.xenobyte.api.gui;

import forgefuck.team.xenobyte.api.Xeno;

public abstract class ElementAbility implements Xeno {
    
    /**
     * Called when an element is initialized
     */
    public void onInit() {}

    /**
     * Rendering an element
     */
    public void onDraw() {}
    
    /**
     * Called when an item is added to the render list
     */
    public void onShow() {}
    
    /**
     * Called when an item is removed from the render list
     */
    public void onHide() {}
    
    /**
     * Called when hovering over an element
     */
    public void onHovered() {}
    
    /**
     * Called by clicking the left mouse button on an element
     */
    public void onLeftClick() {}
    
    /**
     * Called when the cursor leaves the element
     */
    public void onDishovered() {}
    
    /**
     * Called by right-clicking on an element
     */
    public void onRightClick() {}
    
    /**
     * Called by clicking the mouse wheel mouse button on an element
     */
    public void onScrollClick() {}
    
    /**
     * Called when a key is pressed on the focused element
     * @param symb char
     * @param key int code
     */
    public void onKeyTyped(char symb, int key) {}
    
    /**
     * Called when scrolling on the element with focus
     * @param dir int direction: 1 mw down, -1 mw up
     * @param withShift
     */
    public void onScroll(int dir, boolean withShift) {}
    
    /**
     * Description of the element for the tooltip
     * @return String
     */
    public String elementDesc() {
        return null;
    }

}
