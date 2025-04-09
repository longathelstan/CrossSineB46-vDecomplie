/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.Protocola1_2_3_5_1_2_6Tob1_0_1_1_1;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.packet.ServerboundPacketsa1_2_6;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider.AlphaInventoryProvider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.InventoryStorage;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ServerboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;

public class AlphaInventoryUpdateTask
implements Runnable {
    @Override
    public void run() {
        for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
            InventoryStorage inventoryStorage = info.get(InventoryStorage.class);
            if (inventoryStorage == null) continue;
            info.getChannel().eventLoop().submit(() -> {
                if (!info.getChannel().isActive()) {
                    return;
                }
                try {
                    boolean hasChanged;
                    Item[] mainInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItems(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getMainInventoryItems(info));
                    Item[] craftingInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItems(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getCraftingInventoryItems(info));
                    Item[] armorInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItems(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getArmorInventoryItems(info));
                    Item handItem = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getHandItem(info));
                    if (!Objects.equals(handItem, inventoryStorage.handItem)) {
                        PacketWrapper heldItemChange = PacketWrapper.create(ServerboundPacketsb1_1.SET_CARRIED_ITEM, info);
                        heldItemChange.write(Types.SHORT, inventoryStorage.selectedHotbarSlot);
                        heldItemChange.sendToServer(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class, false);
                    }
                    Object[] mergedMainInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(inventoryStorage.mainInventory);
                    Object[] mergedCraftingInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(inventoryStorage.craftingInventory);
                    Object[] mergedArmorInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(inventoryStorage.armorInventory);
                    System.arraycopy(mainInventory, 0, mergedMainInventory, 0, mainInventory.length);
                    System.arraycopy(craftingInventory, 0, mergedCraftingInventory, 0, craftingInventory.length);
                    System.arraycopy(armorInventory, 0, mergedArmorInventory, 0, armorInventory.length);
                    boolean bl = hasChanged = !Arrays.equals(mergedMainInventory, inventoryStorage.mainInventory) || !Arrays.equals(mergedCraftingInventory, inventoryStorage.craftingInventory) || !Arrays.equals(mergedArmorInventory, inventoryStorage.armorInventory);
                    if (!hasChanged) {
                        return;
                    }
                    inventoryStorage.mainInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems((Item[])mergedMainInventory);
                    inventoryStorage.craftingInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems((Item[])mergedCraftingInventory);
                    inventoryStorage.armorInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems((Item[])mergedArmorInventory);
                    PacketWrapper mainContent = PacketWrapper.create(ServerboundPacketsa1_2_6.PLAYER_INVENTORY, info);
                    mainContent.write(Types.INT, -1);
                    mainContent.write(Types1_4_2.NBTLESS_ITEM_ARRAY, mergedMainInventory);
                    PacketWrapper craftingContent = PacketWrapper.create(ServerboundPacketsa1_2_6.PLAYER_INVENTORY, info);
                    craftingContent.write(Types.INT, -2);
                    craftingContent.write(Types1_4_2.NBTLESS_ITEM_ARRAY, mergedCraftingInventory);
                    PacketWrapper armorContent = PacketWrapper.create(ServerboundPacketsa1_2_6.PLAYER_INVENTORY, info);
                    armorContent.write(Types.INT, -3);
                    armorContent.write(Types1_4_2.NBTLESS_ITEM_ARRAY, mergedArmorInventory);
                    mainContent.sendToServer(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
                    craftingContent.sendToServer(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
                    armorContent.sendToServer(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
                }
                catch (Throwable e) {
                    ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Error sending inventory update packets", e);
                }
            });
        }
    }
}

