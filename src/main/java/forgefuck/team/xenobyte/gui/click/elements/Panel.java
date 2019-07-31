package forgefuck.team.xenobyte.gui.click.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import forgefuck.team.xenobyte.api.gui.GuiElement;
import forgefuck.team.xenobyte.api.gui.PanelLayout;
import forgefuck.team.xenobyte.api.gui.PanelSorting;
import forgefuck.team.xenobyte.render.Colors;
import forgefuck.team.xenobyte.utils.Delimitter;

public class Panel extends GuiElement {
    
    private List<GuiElement> elements;
    private PanelSorting sorting;
    private PanelLayout layout;
    private GuiElement title;
    private int maxW;
    
    public Panel(GuiElement ... es) {
        this(null, PanelLayout.VERTICAL, PanelSorting.NONE);
        add(es);
        pack();
    }
    
    public Panel(PanelLayout layout, PanelSorting sorting) {
        this(null, layout, sorting);
    }
    
    public Panel(GuiElement title, PanelLayout layout, PanelSorting sorting) {
        elements = new ArrayList<GuiElement>();
        this.sorting = sorting;
        this.layout = layout;
        this.title = title;
        if (title != null) {
            this.title = title;
            add(title);
        }
    }
    
    public void pack() {
        maxW += 10;
        Comparator<GuiElement> sorter = Comparator.comparing(Objects::nonNull);
        switch (sorting) {
        case WIDTH:
            sorter = Comparator.comparingInt(GuiElement::getWidth).reversed();
            break;
        case ALPHABET:
            sorter = Comparator.comparing(Objects::toString);
            break;
        case NONE:
            break;
        }
        if (title != null) {
            elements.remove(title);
        }
        elements = elements.stream().sorted(sorter).collect(Collectors.toList());
        if (title != null) {
            elements.add(0, title);
        }
        for (GuiElement e : elements) {
            if (layout == PanelLayout.HORIZONTAL) {
                e.setPos(getMaxX(), getY());
                setHeight(e.getHeight());
                setWidth(getWidth() + e.getWidth());
            } else if (layout == PanelLayout.VERTICAL) {
                e.setPos(getX(), getMaxY());
                e.setWidth(maxW);
                setHeight(getHeight() + e.getHeight());
                setWidth(maxW);
            }
        }
    }
    
    public void add(GuiElement e) {
        elements.add(e);
        maxW = maxW < e.getWidth() ? e.getWidth() : maxW;
    }
    
    public void add(GuiElement[] es) {
        Stream.of(es).forEach(this::add);
    }
    
    @Override public boolean click(int key) {
        elements.forEach(e -> e.click(key));
        return super.click(key);
    }
    
    @Override public void setPos(int x, int y) {
        super.setPos(x, y);
        for (GuiElement e : elements) {
            e.setPos(x, y);
            x += layout == PanelLayout.HORIZONTAL ? e.getWidth() : 0;
            y += layout == PanelLayout.VERTICAL ? e.getHeight() : 0;
        }
    }
    
    @Override public void keyTyped(char symb, int key) {
        super.keyTyped(symb, key);
        elements.forEach(e -> e.keyTyped(symb, key));
    }
    
    @Override public void scroll(int dir, boolean withShift) {
        super.scroll(dir, withShift);
        elements.forEach(e -> e.scroll(dir, withShift));
    }
    
    @Override public void draw() {
        super.draw();
        elements.forEach(GuiElement::draw);
    }
    
    @Override public void drawDesc() {
        super.drawDesc();
        elements.forEach(GuiElement::drawDesc);
    }
    
    @Override public void setSelected(boolean selected) {
        super.setSelected(selected);
        elements.forEach(e -> e.setSelected(selected));
    }
    
    @Override public void onDraw() {
        render.GUI.drawRect(getX(), getY(), getMaxX(), getMaxY(), Colors.BLACK);
    }
    
    @Override public String toString() {
        return elements.toString();
    }

}