package forgefuck.team.xenobyte.render;

import org.lwjgl.opengl.GL11;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.module.NEI.XRaySelect;
import forgefuck.team.xenobyte.module.NEI.XRaySelect.SelectedBlock;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class XRayHintRender implements IItemRenderer {
    
    private RenderItem itemRender;
    private XRaySelect selector;
    private SelectedBlock block;
    
    public XRayHintRender(XRaySelect selector) {
        this.itemRender = new RenderItem();
        this.selector = selector;
    }
    
    @Override public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        block = selector.getBlock(item);
        return selector.guiHint && type == ItemRenderType.INVENTORY && block != null;
    }
    
    @Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }
    
    @Override public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
        GL11.glPushMatrix();
        Xeno.render.GUI.drawBorderRect(1, 1, 15, 15, 1, Colors.NONE, block.rgb);
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance().renderItemIntoGUI(Xeno.utils.mc().fontRenderer, Xeno.utils.mc().getTextureManager(), item, 0, 0);
        GL11.glPopMatrix();
    }

}