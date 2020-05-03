package forgefuck.team.xenobyte.render;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderManager;

public class WorldRender {
    
    public void drawEspLine(double sx, double sy, double sz, double ex, double ey, double ez, float r, float g, float b, float a, float scale) {
        double pX = RenderManager.renderPosX;
        double pY = RenderManager.renderPosY;
        double pZ = RenderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glTranslated(-pX, -pY, -pZ);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(r, g, b, a);
        GL11.glLineWidth(scale);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(sx, sy, sz);
        GL11.glVertex3d(ex, ey, ez);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    public void drawEspBlock(double x, double y, double z, float r, float g, float b, float a, float scale) {
        double pX = RenderManager.renderPosX;
        double pY = RenderManager.renderPosY;
        double pZ = RenderManager.renderPosZ;
        float tr = (1 - scale) / 2;
        GL11.glPushMatrix();
        GL11.glTranslated(-pX, -pY, -pZ);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(r, g, b, a);
        GL11.glTranslated(x, y, z);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glTranslatef(tr, tr, tr);
        GL11.glScalef(scale, scale, scale);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(0, 1, 0);
        GL11.glVertex3f(0, 1, 1);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(1, 0, 1);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(0, 1, 1);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(1, 0, 1);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(0, 1, 0);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(0, 1, 1);
        GL11.glVertex3f(0, 1, 0);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(1, 0, 1);
        GL11.glVertex3f(1, 0, 0);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    public void drawOutlinedEspBlock(double x, double y, double z, float r, float g, float b, float a, float scale) {
        double pX = RenderManager.renderPosX;
        double pY = RenderManager.renderPosY;
        double pZ = RenderManager.renderPosZ;
        float tr = (1 - scale) / 2;
        GL11.glPushMatrix();
        GL11.glTranslated(-pX, -pY, -pZ);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(r, g, b, a);
        GL11.glTranslated(x, y, z);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glTranslatef(tr, tr, tr);
        GL11.glScalef(scale, scale, scale);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(0, 1, 0);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(0, 1, 1);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(0, 1, 1);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(0, 1, 1);
        GL11.glVertex3f(0, 1, 0);
        GL11.glVertex3f(1, 0, 1);
        GL11.glVertex3f(0, 0, 1);
        GL11.glVertex3f(1, 0, 1);
        GL11.glVertex3f(1, 1, 1);
        GL11.glVertex3f(1, 0, 1);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(0, 1, 0);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(1, 1, 1);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    public void drawWayLine(List<double[]> poses, float r, float g, float b, float a, float w) {
        Iterator<double[]> iterator = poses.iterator();
        double pX = RenderManager.renderPosX;
        double pY = RenderManager.renderPosY;
        double pZ = RenderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(r, g, b, a);
        GL11.glLineWidth(w);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        while(iterator.hasNext()) {
            double[] p = iterator.next();
            GL11.glVertex3d(p[0] - pX, p[1] - pY, p[2] - pZ);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}
