/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import java.util.UUID;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.Protocolr1_7_2_5Tor1_7_6_10;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ClientboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider.GameProfileFetcher;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_7_2, ServerboundPackets1_8, Protocolr1_7_6_10Tor1_8> {
    public ItemRewriter(Protocolr1_7_6_10Tor1_8 protocol) {
        super(protocol, "1.7.10", Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
        this.addRemappedItem(8, 326, "Water Block");
        this.addRemappedItem(9, 326, "Stationary Water Block");
        this.addRemappedItem(10, 327, "Lava Block");
        this.addRemappedItem(11, 327, "Stationary Lava Block");
        this.addRemappedItem(51, 385, "Fire");
        this.addRemappedItem(90, 399, "Nether portal");
        this.addRemappedItem(119, 381, "End portal");
        this.addRemappedItem(127, 351, 3, "Cocoa Block");
        this.addRemappedItem(141, 391, "Carrot Crops");
        this.addRemappedItem(142, 392, "Potato Crops");
        this.addRemappedItem(43, 44, "Double Stone Slab");
        this.addRemappedItem(125, 126, "Double Wood Slab");
        this.addNonExistentItem(1, 1, 6);
        this.addNonExistentItem(3, 1);
        this.addNonExistentItem(19, 1);
        this.addNonExistentItemRange(165, 169);
        this.addNonExistentItemRange(179, 192);
        this.addNonExistentItem(383, 67);
        this.addNonExistentItem(383, 68);
        this.addNonExistentItem(383, 101);
        this.addNonExistentItemRange(409, 416);
        this.addNonExistentItemRange(423, 425);
        this.addNonExistentItemRange(427, 431);
    }

    @Override
    public Item handleItemToClient(UserConnection user, Item item) {
        super.handleItemToClient(user, item);
        if (item == null) {
            return null;
        }
        if (item.identifier() == ItemList1_6.skull.itemId() && item.data() == 3 && item.tag() != null) {
            Tag skullOwnerTag;
            if (!item.tag().contains("SkullOwner")) {
                return item;
            }
            String skullOwnerName = null;
            if (!item.tag().getString("SkullOwner", "").isEmpty()) {
                skullOwnerTag = (StringTag)item.tag().removeUnchecked("SkullOwner");
                item.tag().put("1_7_SkullOwner", skullOwnerTag);
                skullOwnerName = ((StringTag)skullOwnerTag).getValue();
            } else {
                CompoundTag skullOwnerTag2;
                Tag tag;
                skullOwnerTag = item.tag().get("SkullOwner");
                if (skullOwnerTag instanceof CompoundTag && (tag = (skullOwnerTag2 = (CompoundTag)skullOwnerTag).get("Name")) instanceof StringTag) {
                    StringTag skullOwnerNameTag = (StringTag)tag;
                    if (!skullOwnerTag2.contains("Id")) {
                        skullOwnerName = skullOwnerNameTag.getValue();
                    }
                }
            }
            if (skullOwnerName != null && ViaLegacy.getConfig().isLegacySkullLoading()) {
                UUID uuid;
                GameProfileFetcher gameProfileFetcher = Via.getManager().getProviders().get(GameProfileFetcher.class);
                if (gameProfileFetcher.isUUIDLoaded(skullOwnerName) && gameProfileFetcher.isGameProfileLoaded(uuid = gameProfileFetcher.getMojangUUID(skullOwnerName))) {
                    GameProfile skullProfile = gameProfileFetcher.getGameProfile(uuid);
                    if (skullProfile == null || skullProfile.isOffline()) {
                        return item;
                    }
                    item.tag().put("SkullOwner", Protocolr1_7_2_5Tor1_7_6_10.writeGameProfileToTag(skullProfile));
                    return item;
                }
                gameProfileFetcher.getMojangUUIDAsync(skullOwnerName).thenAccept(gameProfileFetcher::getGameProfile);
            }
        } else if ((item.identifier() == ItemList1_6.writtenBook.itemId() || item.identifier() == ItemList1_6.writableBook.itemId()) && item.tag() != null) {
            ListTag<StringTag> pages = item.tag().getListTag("pages", StringTag.class);
            if (pages == null) {
                return item;
            }
            for (int i = 0; i < pages.size(); ++i) {
                String text = pages.get(i).getValue();
                pages.set(i, new StringTag(TextComponentSerializer.V1_8.serialize(new StringComponent(text))));
            }
        }
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection user, Item item) {
        ListTag<StringTag> pages;
        if (item == null) {
            return null;
        }
        if (item.identifier() == ItemList1_6.skull.itemId() && item.data() == 3 && item.tag() != null) {
            if (item.tag().contains("1_7_SkullOwner") && item.tag().get("1_7_SkullOwner") instanceof StringTag) {
                item.tag().put("SkullOwner", item.tag().remove("1_7_SkullOwner"));
            }
        } else if ((item.identifier() == ItemList1_6.writtenBook.itemId() || item.identifier() == ItemList1_6.writableBook.itemId()) && item.tag() != null && (pages = item.tag().getListTag("pages", StringTag.class)) != null) {
            for (int i = 0; i < pages.size(); ++i) {
                String text = pages.get(i).getValue();
                pages.set(i, new StringTag(TextComponentSerializer.V1_8.deserialize(text).asLegacyFormatString()));
            }
        }
        return super.handleItemToServer(user, item);
    }
}

