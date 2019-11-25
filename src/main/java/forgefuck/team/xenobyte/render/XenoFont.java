package forgefuck.team.xenobyte.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import forgefuck.team.xenobyte.api.Xeno;

public class XenoFont {
    
    private int rows, charWidth, fontHeight, shadowShift;
    private String letters;
    private int[][] poses;
    
    public XenoFont() {
        letters = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ЁёАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
        poses = new int[fontLetters().length()][2];
        shadowShift = 2;
        fontHeight = 26;
        charWidth = 13;
        rows = 19;
        for (int ch = 0; ch < fontLetters().length(); ch ++) {
            poses[ch][0] = (ch % rows) * charWidth;
            poses[ch][1] = (ch / rows) * fontHeight;
        }
    }
    
    public int textWidth(String text) {
        int outLen = 0;
        for (char ch : text.toCharArray()) {
            if (fontLetters().indexOf(ch) > -1) {
                outLen ++;
            }
        }
        return ((outLen * charWidth) / 2) + 1;
    }
    
    public int fontHeight() {
        return fontHeight / 2;
    }
    
    public String fontLetters() {
        return letters;
    }
    
    public void drawString(String text, int x, int y, int color) {
        int sub = 0;
        float[] rgba = new Color(color, true).getRGBComponents(null);
        GL11.glPushMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.FONT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glScalef(0.5F, 0.5F, 0);
        for (char ch : text.toCharArray()) {
            int index = fontLetters().indexOf(ch);
            if (index > -1) {
                int[] pos = poses[index];
                int xStart = x * 2 + sub;
                int yStart = y * 2;
                GL11.glColor4f(0, 0, 0, rgba[3]);
                Xeno.render.GUI.drawTexturedModalRect(xStart + shadowShift, yStart + shadowShift, pos[0], pos[1], charWidth, fontHeight);
                GL11.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
                Xeno.render.GUI.drawTexturedModalRect(x * 2 + sub, y * 2, pos[0], pos[1], charWidth, fontHeight);
                sub += charWidth;
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}