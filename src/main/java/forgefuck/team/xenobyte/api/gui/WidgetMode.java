package forgefuck.team.xenobyte.api.gui;

import forgefuck.team.xenobyte.render.Colors;

public enum WidgetMode {
    
    INFO(Colors.SKY), SUCCESS(Colors.GREEN), FAIL(Colors.RED);
    
    private int color;
    
    WidgetMode(int color) {
        this.color = color;
    }
    
    public int getColor() {
        return color;
    }
    
}