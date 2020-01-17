package forgefuck.team.xenobyte.api.gui;

import java.awt.Color;

public abstract class ColorPicker {
    
    public int r, g, b, a, rgb, rgba;
    public float rf, gf, bf, af;
    public Color color;
    
    public ColorPicker(int c) {
        this(new Color(c, true));
    }
    
    public ColorPicker(Color c) {
        setColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }
    
    public void setColor(int r, int g, int b, int a) {
        this.rgb = new Color(r, g, b).getRGB();
        this.color = new Color(r, g, b, a);
        this.rgba = color.getRGB();
        this.rf = r / 255F;
        this.gf = g / 255F;
        this.bf = b / 255F;
        this.af = a / 255F;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        onColorUpdate();
    }
    
    protected void onColorUpdate() {}

}