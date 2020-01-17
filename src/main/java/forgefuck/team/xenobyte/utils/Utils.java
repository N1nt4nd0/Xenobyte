package forgefuck.team.xenobyte.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.SimpleIndexedCodec;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.modules.GiveSelect;
import forgefuck.team.xenobyte.modules.XRaySelect;
import gnu.trove.map.hash.TByteObjectHashMap;
import gnu.trove.map.hash.TObjectByteHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class Utils {
    
    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }
    
    public int[] mop() {
        MovingObjectPosition mop = mc().renderViewEntity.rayTrace(200, 1.0F);
        Entity ent = pointedEntity();
        return new int[] { mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, (ent != null) ? ent.getEntityId() : -1 };
    }
    
    public int[] myCoords() {
        return coords(player());
    }
    
    public int[] coords(Entity curEnt) {
        return new int[] { (int) Math.floor(curEnt.posX), (int) Math.floor(curEnt.posY) - 2, (int) Math.floor(curEnt.posZ) };
    }
    
    public int[] coords(TileEntity curTile) {
        return new int[] { curTile.xCoord, curTile.yCoord, curTile.zCoord };
    }
    
    public String formatCoords(int ... crd) {
    	return String.format("[%s, %s, %s]", crd[0], crd[1], crd[2]);
    }
    
    public EntityPlayer player(String nick) {
        return world().getPlayerEntityByName(nick);
    }
    
    public EntityClientPlayerMP player() {
        return mc().thePlayer;
    }
    
    public boolean isInCreative() {
        return mc().playerController.isInCreativeMode();
    }
    
    public boolean isInCreative(EntityPlayer pl) {
        return pl.capabilities.isCreativeMode;
    }
    
    public String uuid(Entity curEnt) {
        return curEnt.getUniqueID().toString();
    }
    
    public int id(Entity curEnt) {
        return curEnt.getEntityId();
    }
    
    public int myId() {
        return id(player());
    }
    
    public String myUuid() {
        return uuid(player());
    }
    
    public String name(Entity entity) {
        return entity.getCommandSenderName();
    }
    
    public String myName() {
        return player().getCommandSenderName();
    }
    
    public String stringId(ItemStack item) {
        return item.getItem().delegate.name();
    }
    
    public WorldClient world() {
        return mc().theWorld;
    }
    
    public int worldId() {
        return world().provider.dimensionId;
    }
    
    public void reloadWorld() {
        world().provider.registerWorld(world());
    }
    
    public void disconnect() {
        NetHandlerPlayClient netHandler = mc().getNetHandler();
        if (netHandler != null) {
            netHandler.getNetworkManager().closeChannel(new ChatComponentText("Тупа бан"));
        }
    }
    
    public NBTTagCompound jsonToNbt(String json) {
        NBTBase base = null;
        try { base = JsonToNBT.func_150315_a(json);
        } catch (NBTException err) {
        	XenoLogger.getLogger().error(err.getMessage());
        }
        return (NBTTagCompound) base;
    }
    
    public String nbtToJson(NBTTagCompound nbt) {
        return nbt.toString().replaceAll("\\s+", " ").replaceAll("\"", "");
    }
    
    public ItemStack item() {
        return player().getCurrentEquippedItem();
    }
    
    public ItemStack hoveredItem() {
        return player().inventory.getItemStack();
    }
    
    public ItemStack item(int id, int meta, int count) {
        Item item = Item.getItemById(id);
        ItemStack stack = item != null ? new ItemStack(item) : null;
        if (stack != null) {
            stack.setItemDamage(meta);
            stack.stackSize = count;
        }
        return stack;
    }
    
    public ItemStack item(int id, int meta) {
        return item(id, meta, 1);
    }
    
    public ItemStack item(int id) {
        return item(id, 0, 1);
    }
    
    public ItemStack item(ItemStack toItem, String json) {
        return item(toItem, jsonToNbt(json));
    }
    
    public ItemStack item(ItemStack toItem, NBTTagCompound tag) {
        if (toItem != null && tag != null) {
            toItem.setTagCompound(collideNbt(toItem.hasTagCompound() ? toItem.stackTagCompound : new NBTTagCompound(), tag));
        }
        return toItem;
    }
    
    public ItemStack heldItem() {
        return player().inventory.getItemStack();
    }
    
    public ItemStack rename(ItemStack toItem, String name) {
        return toItem.setStackDisplayName(name);
    }
    
    public void setHardness(Block block, int hardness) {
        block.setHardness(hardness);
    }
    
    public void setHardness(int hardness) {
        setHardness(block(), hardness);
    }
    
    public int blockMeta() {
        return blockMeta(mop());
    }
    
    public int blockMeta(int[] mop) {
        return blockMeta(mop[0], mop[1], mop[2]);
    }
    
    public int blockMeta(int x, int y, int z) {
        return world().getBlockMetadata(x, y, z);
    }
    
    public Block block() {
        return block(mop());
    }
    
    public Block block(int[] mop) {
        return block(mop[0], mop[1], mop[2]);
    }
    
    public Block block(int x, int y, int z) {
        return world().getBlock(x, y, z);
    }
    
    public Block block(String id) {
        return (Block) Block.blockRegistry.getObject(id);
    }
    
    public TileEntity tile() {
        return tile(mop());
    }
    
    public TileEntity tile(int[] mop) {
        return tile(mop[0], mop[1], mop[2]);
    }
    
    public TileEntity tile(int x, int y, int z) {
        return world().getTileEntity(x, y, z);
    }
    
    public TileEntity tile(NBTTagCompound tag) {
        return tile(tile(), tag);
    }
    
    public TileEntity tile(TileEntity toTile, NBTTagCompound tag) {
        if (toTile != null && tag != null) {
            NBTTagCompound tileTag = new NBTTagCompound();
            toTile.writeToNBT(tileTag);
            toTile.readFromNBT(collideNbt(tileTag, tag));
        }
        return toTile;
    }
    
    public Entity pointedEntity() {
        return mc().objectMouseOver.entityHit;
    }
    
    public Entity entity() {
        return entity(null);
    }
    
    public void singlePlayerGui() {
        mc().displayGuiScreen(new GuiSelectWorld(currentScreen()));
    }
    
    public Entity entity(NBTTagCompound tag) {
        return entity(entity(mop()[4]), tag);
    }
    
    public Entity entity(Entity toEntity, NBTTagCompound tag) {
        if (toEntity != null && tag != null) {
            toEntity.readFromNBT(tag);
        }
        return toEntity;
    }
    
    public Entity entity(int id) {
        return world().getEntityByID(id);
    }
    
    public Stream<Entity> nearEntityes() {
        return nearEntityes(50);
    }
    
    public Stream<Entity> nearEntityes(int radius) {
        return world().loadedEntityList.stream().filter(e -> player().getDistanceToEntity((Entity) e) <= radius && e != player());
    }
    
    public String longString(String text, int len) {
        return StringUtils.repeat(text, len);
    }
    
    public void clipboardMessage(Object clipboardTextObj) {
        String clipboardText = nullHelper(clipboardTextObj);
        if (!clipboardText.isEmpty()) {
            StringSelection clipText = new StringSelection(clipboardText.replaceAll("§.", ""));
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipText, null);
        }
    }
    
    public void creativeGive(int slot, int id, int meta, int count) {
        creativeGive(slot, item(id, meta, count));
    }
    
    public void creativeGive(int id, int meta, int count) {
        creativeGive(activeSlot(), item(id, meta, count));
    }
    
    public void creativeGive(int id, int meta) {
        creativeGive(activeSlot(), item(id, meta));
    }
    
    public void creativeGive(int id) {
        creativeGive(activeSlot(), item(id));
    }
    
    public void creativeGive(ItemStack item) {
        creativeGive(activeSlot(), item);
    }
    
    public void creativeGive(int slot, ItemStack item) {
        sendPacket(new C10PacketCreativeInventoryAction(slot, item));
    }
    
    public void windowMessage(Object messageTextObj) {
        JOptionPane.showMessageDialog(null, nullHelper(messageTextObj));
    }
    
    public boolean confirmDialog(Object messageTextObj) {
        return JOptionPane.showConfirmDialog(null, nullHelper(messageTextObj)) == JOptionPane.YES_OPTION;
    }
    
    public void serverChatMessage(Object serverChatMessageText) {
        mc().thePlayer.sendChatMessage(nullHelper(serverChatMessageText));
    }
    
    public void chatMessage(Object chatMessageText) {
        mc().thePlayer.addChatMessage(new ChatComponentText(Xeno.format_prefix + nullHelper(chatMessageText)));
    }
    
    public void chatMessage(Object ... data) {
        chatMessage(Arrays.asList(data));
    }
    
    public void debugMessage() {
        chatMessage(formatTime().concat(" ").concat(Rand.str()));
    }
    
    public void playSound(String location, int type) {
        mc().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(location), type));
    }
    
    public String nullHelper(Object o) {
        return new String() + o;
    }
    
    public String separator(String text) {
        return "==================== " + text + " ====================";
    }
    
    public int windowID() {
        return player().openContainer.windowId;
    }
    
    public GuiContainer guiContainer() {
        return guiContainer(currentScreen());
    }
    
    public GuiContainer guiContainer(GuiScreen screen) {
        return screen instanceof GuiContainer ? (GuiContainer) screen : null;
    }
    
    public void shutDown() {
        mc().shutdown();
    }
    
    public void dropSlots(int range) {
        for(int slot = 0; slot < range; slot++) {
            dropSlot(slot, true);
        }
    }
    
    public void dropSlot(int slot, boolean allStacks) {
        clickSlot(slot, allStacks ? 1 : 0, 4);
    }
    
    public void swapSlot(int from, int to) {
        clickSlot(from, to, 2);
    }
    
    public void clickSlot(int slot, int shift, int action) {
        mc().playerController.windowClick(windowID(), slot, shift, action, player());
    }
    
    public int activeSlot() {
        return mySlotsCount() + player().inventory.currentItem;
    }
    
    public boolean isInGame() {
        return player() != null && world() != null;
    }
    
    public int mySlotsCount() {
        return player().inventory.mainInventory.length;
    }
    
    public int slots(TileEntity tile) {
        if (tile instanceof IInventory) {
            IInventory inventory = (IInventory) tile;
            return inventory.isUseableByPlayer(player()) ? inventory.getSizeInventory() : 0;
        }
        return 0;
    }
    
    public String entityInfo(Entity entity) {
        return "[" + entity.getCommandSenderName() + ": UUID(" + uuid(entity) + "), ID("+ id(entity) + ")]";
    }
    
    public List<String> tabList() {
    	return ((List<GuiPlayerInfo>) player().sendQueue.playerInfoList).stream().map(r -> r.name).collect(Collectors.toList());
    }
    
    public List<TileEntity> nearTiles() {
        List<TileEntity> out = new ArrayList<TileEntity>();
        IChunkProvider chunkProvider = world().getChunkProvider();
        if (chunkProvider instanceof ChunkProviderClient) {
            for (Chunk chunk : (List<Chunk>) ReflectionHelper.getPrivateValue(ChunkProviderClient.class, (ChunkProviderClient) chunkProvider, 3)) {
                for (Object entityObj : chunk.chunkTileEntityMap.values()) {
                    if (entityObj instanceof TileEntity) {
                        out.add((TileEntity)entityObj);
                    }
                }
            }
        }
        return out;
    }
    
    public List<String> playersList() {
        List<String> out = new ArrayList<String>();
        for (GuiPlayerInfo info : (List<GuiPlayerInfo>) mc().thePlayer.sendQueue.playerInfoList) {
            out.add(info.name);
        }
        return out;
    }
    
    public NBTTagCompound nbtItem(ItemStack item) {
        return item.writeToNBT(new NBTTagCompound());
    }
    
    public NBTTagCompound nbtItem(ItemStack item, int slot, String slotName) {
        NBTTagCompound tag = nbtItem(item);
        tag.setByte(slotName, (byte) slot);
        return tag;
    }
    
    public String formatTime() {
        return new SimpleDateFormat("'['HH:mm:ss']'").format(new Date());
    }
    
    public boolean hasTag(Object obj, String key) {
        NBTTagCompound tag = nbt(obj);
        if (tag != null) {
            return tag.hasKey(key);
        }
        return false;
    }
    
    public NBTTagCompound nbt(Object obj) {
        NBTTagCompound tag = new NBTTagCompound();;
        if (obj instanceof ItemStack) {
            tag = ((ItemStack)obj).getTagCompound();
        } else if (obj instanceof TileEntity) {
            ((TileEntity)obj).writeToNBT(tag);
        } else if (obj instanceof Entity) {
            ((Entity)obj).writeToNBT(tag);
        }
        return tag;
    }
    
    public NBTTagCompound collideNbt(NBTTagCompound toCollide, NBTTagCompound in) {
        Map outMap = nbtMap(toCollide);
        outMap.putAll(nbtMap(in));
        setNbtMap(toCollide, outMap);
        return toCollide;
    }
    
    public Map<String, NBTBase> nbtMap(NBTTagCompound tag){
        return ReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, 1);
    }
    
    public void setNbtMap(NBTTagCompound tag, Map map) {
        ReflectionHelper.setPrivateValue(NBTTagCompound.class, tag, map, 1);
    }
    
    public boolean isDoubleChest(TileEntity tile) {
        if (tile instanceof TileEntityChest) {
            int[] pos = coords(tile);
            int x = pos[0];
            int y = pos[1];
            int z = pos[2];
            if (tile(x - 1, y, z) instanceof TileEntityChest || tile(x + 1, y, z) instanceof TileEntityChest || tile(x, y, z - 1) instanceof TileEntityChest || tile(x, y, z + 1) instanceof TileEntityChest) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPlayer(Entity e) {
        return e instanceof EntityPlayer;
    }
    
    public boolean isVillager(Entity e) {
    	return e instanceof EntityVillager;
    }
    
    public boolean isMonster(Entity e) {
    	return e instanceof EntityMob || e instanceof IMob;
    }
    
    public boolean isDrop(Entity e) {
        return e instanceof EntityItem;
    }
    
    public boolean isAnimal(Entity e) {
    	return e instanceof EntityAnimal || e instanceof EntityAmbientCreature || e instanceof EntityWaterMob || e instanceof EntityGolem;
    }
    
    public boolean isCustom(Entity e) {
    	try {
    		return Class.forName("noppes.npcs.entity.EntityNPCInterface").isInstance(e);
    	} catch(Exception ex) {
    		return false;
    	}
    }
    
    private double lastCoord = Double.MAX_VALUE;
    public boolean isAfk(EntityLivingBase e) {
        double coord = e.posX + e.posY + e.posZ;
        if (coord == lastCoord) {
            return true;
        } else {
            lastCoord = coord;
            return false;
        }
    }
    
    public NBTTagCompound chestGiveHelper(TileEntityChest chest, GiveSelect selector) {
        NBTTagCompound root = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        root.setInteger("x", chest.xCoord);
        root.setInteger("y", chest.yCoord);
        root.setInteger("z", chest.zCoord);
        root.setString("id", "Chest");
        int slots = selector.fillAllSlots() ? slots(chest) : 1;
        for (int i = 0; i < slots; i++) {
            list.appendTag(nbtItem(selector.givedItem(), i, "Slot"));
        }
        root.setTag("Items", list);
        return root;
    }
    
    public List<int[]> nukerList(int[] c, int r, XRaySelect selector) {
        r --;
        List<int[]> list = new ArrayList<int[]>();
        for (int i = r; i >= -r; i--) {
            for (int k = r; k >= -r; k--) {
                for (int j = -r; j <= r; j++) {
                    int x = c[0] + i;
                    int y = c[1] + j;
                    int z = c[2] + k;
                    Block block = block(x, y, z);
                    int meta = blockMeta(x, y, z);
                    if (selector != null ? selector.getBlock(block, meta) == null : block instanceof BlockAir) {
                        continue;
                    }
                    list.add(new int[] { x, y, z });
                }
            }
        }
        return list;
    }
    
    public void runWorldSave(String saveName) {
        try {
            mc().launchIntegratedServer(saveName, saveName, null);
        } catch(Exception e) {}
    }
    
    public boolean isGuiOpen(String className) {
        try {
            return isGuiOpen((Class<? extends GuiScreen>) Class.forName(className));
        } catch(Exception e) {
            return false;
        }
    }
    
    public String getObfuscated(String obf, String mcp) {
        return isObfuscated() ? obf : mcp;
    }
    
    public boolean isObfuscated() {
        try {
            Minecraft.class.getDeclaredMethod("getMinecraft");
            return false;
        } catch(Exception e) {
            return true;
        }
    }
    
    public boolean isGuiOpen(Class<? extends GuiScreen> guiClass) {
        return currentScreen() == null ? false : currentScreen().getClass() == guiClass;
    }
    
    public GuiScreen currentScreen() {
        return mc().currentScreen;
    }
    
    public void openGui(GuiScreen gui) {
        openGui(gui, false);
    }
    
    private double boxHeight;
    public void verticalTeleport(int yPos, boolean sound) {
        if (boxHeight == 0) {
            boxHeight = player().boundingBox.maxY - player().boundingBox.minY; 
        }
        player().boundingBox.minY = yPos;
        player().boundingBox.maxY = yPos + boxHeight;
        if (sound) {
            playSound("mob.endermen.portal", 1);
        }
    }

    private double boxWidth;
    public void horizontalTeleport(int xPos, int zPos, boolean sound) {
        if (boxWidth == 0) {
            boxWidth = player().boundingBox.maxX - player().boundingBox.minX; 
        }
        player().boundingBox.minX = xPos;
        player().boundingBox.maxX = xPos + boxWidth;
        player().boundingBox.minZ = zPos;
        player().boundingBox.maxZ = zPos + boxWidth;
        if (sound) {
            playSound("mob.endermen.portal", 1);
        }
    }
    
    public void openGui(GuiScreen gui, boolean silent) {
        if (silent) {
            ScaledResolution sr = new ScaledResolution(mc(), mc().displayWidth, mc().displayHeight);
            mc().currentScreen = gui;
            mc().setIngameNotInFocus();
            gui.width = sr.getScaledWidth();
            gui.height = sr.getScaledHeight();
            gui.mc = mc();
            gui.mc.skipRenderWorld = false;
            gui.initGui();
        } else {
            mc().displayGuiScreen(gui);
        }
    }
    
    public void closeGuis() {
        openGui(null);
        mc().setIngameFocus();
    }
    
    public boolean isInGameGui() {
        return isInGame() && currentScreen() == null;
    }
    
    public ByteBuf itemWriter(int id) {
        return itemWriter(id, 0, 1, null);
    }
    
    public ByteBuf itemWriter(int id, int meta) {
        return itemWriter(id, meta, 1, null);
    }
    
    public ByteBuf itemWriter(int id, int meta, int count) {
        return itemWriter(id, meta, count, null);
    }
    
    public ByteBuf itemWriter(int id, int meta, int count, NBTTagCompound nbt) {
        ByteBuf buf = Unpooled.buffer(0).writeShort(id).writeByte(count).writeShort(meta);
        ByteBufUtils.writeTag(buf, nbt);
        return buf;
    }
    
    public Object getPacket(SimpleNetworkWrapper wrapper, int id) {
        SimpleIndexedCodec codec = Reflections.getPrivateValue(SimpleNetworkWrapper.class, wrapper, 1);
        TByteObjectHashMap descs = Reflections.getPrivateValue(FMLIndexedMessageToMessageCodec.class, codec, 0);
        return descs.get((byte) id);
    }
    
    public int getPacket(SimpleNetworkWrapper wrapper, Class packet) {
        SimpleIndexedCodec codec = Reflections.getPrivateValue(SimpleNetworkWrapper.class, wrapper, 1);
        TObjectByteHashMap types = Reflections.getPrivateValue(FMLIndexedMessageToMessageCodec.class, codec, 1);
        return types.get(packet);
    }
    
    public void sendPacket(Packet packet) {
        mc().thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    public void sendPacket(String channel, ByteBuf data) {
        sendPacket(new FMLProxyPacket(data, channel));
    }
    
    public void sendPacket(String channel, Object ... data) {
        sendPacket(channel, bufWriter(data));
    }
    
    public ByteBuf bufWriter(Object ... data) {
        return bufWriter(Unpooled.buffer(0), data);
    }
    
    public ByteBuf bufWriter(ByteBuf buf, Object ... data) {    
        for (Object o : data) {
            if (o instanceof Integer) {
                buf.writeInt((Integer)o);
            } else if (o instanceof Byte) {
                buf.writeByte((Byte)o);
            } else if (o instanceof Short) {
                buf.writeShort((Short)o);
            } else if (o instanceof Float) {
                buf.writeFloat((Float)o);
            } else if (o instanceof String) {
                ByteBufUtils.writeUTF8String(buf, (String)o);
            } else if (o instanceof ItemStack) {
                ByteBufUtils.writeItemStack(buf, (ItemStack)o);
            } else if (o instanceof NBTTagCompound) {
                ByteBufUtils.writeTag(buf, (NBTTagCompound)o);
            } else if (o instanceof ByteBuf) {
                buf.writeBytes((ByteBuf)o);
            } else if (o instanceof Double) {
                buf.writeDouble((Double)o);
            } else if (o instanceof Boolean) {
                buf.writeBoolean((Boolean)o);
            } else if (o instanceof byte[]) {
                buf.writeBytes((byte[])o);
            } else if (o instanceof Long) {
                buf.writeLong((Long)o);
            } else if (o instanceof int[]) {
                for (int i : (int[]) o) {
                    bufWriter(buf, i);
                }
            }
        }
        return buf;
    }
    
}