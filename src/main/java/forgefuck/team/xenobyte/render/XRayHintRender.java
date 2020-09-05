package forgefuck.team.xenobyte.render;

import org.lwjgl.opengl.GL11;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.modules.XRaySelect;
import forgefuck.team.xenobyte.modules.XRaySelect.SelectedBlock;
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
        return block != null && selector.guiHint && type == ItemRenderType.INVENTORY;
    }
    
    @Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }
    
    @Override public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
        GL11.glPushMatrix();
        if (block.hidden) {
            Xeno.render.GUI.drawRect(0, 14, 8, 16, Colors.RED);
        }
        if (block.tracer) {
            Xeno.render.GUI.drawRect(8, 14, 16, 16, Colors.SKY);
        }
        Xeno.render.GUI.drawBorderRect(1, 1, 15, 15, 1, Colors.TRANSPARENT, block.rgb);
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance().renderItemIntoGUI(Xeno.utils.mc().fontRenderer, Xeno.utils.mc().getTextureManager(), item, 0, 0);
        GL11.glPopMatrix();
    }

}