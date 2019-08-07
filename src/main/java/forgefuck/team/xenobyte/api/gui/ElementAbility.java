package forgefuck.team.xenobyte.api.gui;

import forgefuck.team.xenobyte.api.Xeno;

public abstract class ElementAbility implements Xeno {
    
    /**
     * Вызывается при инициализации элемента
     */
    public void onInit() {}

    /**
     * Отрисовка элемента
     */
    public void onDraw() {}
    
    /**
     * Вызывается при добавлении элемента в список отрисовки
     */
    public void onShow() {}
    
    /**
     * Вызывается при удалении элемента из списка отрисовки
     */
    public void onHide() {}
    
    /**
     * Вызывается при наведении курсора на элемент
     */
    public void onHovered() {}
    
    /**
     * Вызывается по клику левой кнопки мыши по элементу
     */
    public void onLeftClick() {}
    
    /**
     * Вызывается когда курсор покидает элемент
     */
    public void onDishovered() {}
    
    /**
     * Вызывается по клику правой кнопки мыши по элементу
     */
    public void onRightClick() {}
    
    /**
     * Вызывается по клику средней кнопки мыши по элементу
     */
    public void onScrollClick() {}
    
    /**
     * Вызывается при нажатии клавиши на элементе в фокусе
     * @param symb char символ
     * @param key int код клавиши
     */
    public void onKeyTyped(char symb, int key) {}
    
    /**
     * Вызывается при прокрутке скролла на элементе в фокусе
     * @param dir int направление: 1 вниз, -1 вверх
     * @param withShift зажата ли клавиша Shift
     */
    public void onScroll(int dir, boolean withShift) {}
    
    /**
     * Описание элемента для тултипа
     * @return String
     */
    public String elementDesc() {
        return null;
    }

}
