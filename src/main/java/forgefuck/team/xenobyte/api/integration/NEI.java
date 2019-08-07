package forgefuck.team.xenobyte.api.integration;

import java.util.List;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.utils.Reflections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;

public class NEI {
    
    public static boolean isAvailable() {
        return Loader.isModLoaded("NotEnoughItems") || Reflections.exists("codechicken.nei.api.API") || Reflections.exists("codechicken.nei.guihook.GuiContainerManager");
    }
    
    public static void openGui(String searchText) {
        setSearchField(searchText);
        Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(Minecraft.getMinecraft().thePlayer));
    }
    
    public static void setSearchField(String text) {
        try {
            Class.forName("codechicken.nei.TextField").getDeclaredMethod("setText", String.class).invoke(Class.forName("codechicken.nei.LayoutManager").getDeclaredField("searchField").get(null), text);
        } catch (Exception e) {}
    }
    
    public static void clearHiddenItems() {
        try {
            Class.forName("codechicken.nei.ItemStackMap").getDeclaredMethod("clear").invoke(Class.forName("codechicken.nei.api.ItemInfo").getDeclaredField("hiddenItems").get(null));
        } catch (Exception e) {}
    }
    
    public static void addSubset(String name, List<ItemStack> items) {
        try {
            Class.forName("codechicken.nei.api.API").getDeclaredMethod("addSubset", String.class, Iterable.class).invoke(null, name, items);
        } catch (Exception e) {}
    }
    
    public static ItemStack getStackMouseOver() {
        try {
            GuiContainer container = Xeno.utils.guiContainer();
            if (container instanceof GuiContainer) {
                Object checkItem = Class.forName("codechicken.nei.guihook.GuiContainerManager").getDeclaredMethod("getStackMouseOver", GuiContainer.class).invoke(null, container);
                if (checkItem != null) {
                    return ((ItemStack)checkItem).copy();
                }
            }
        } catch (Exception e) {}
        return null;
    }

}