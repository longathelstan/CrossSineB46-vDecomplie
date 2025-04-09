/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_17_1to1_17;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.v1_17_1to1_17.storage.InventoryStateIds;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;

public final class Protocol1_17_1To1_17
extends BackwardsProtocol<ClientboundPackets1_17_1, ClientboundPackets1_17, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final int MAX_PAGE_LENGTH = 8192;
    private static final int MAX_TITLE_LENGTH = 128;
    private static final int MAX_PAGES = 200;

    public Protocol1_17_1To1_17() {
        super(ClientboundPackets1_17_1.class, ClientboundPackets1_17.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_17_1.REMOVE_ENTITIES, null, (PacketWrapper wrapper) -> {
            int[] entityIds = wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
            wrapper.cancel();
            for (int entityId : entityIds) {
                PacketWrapper newPacket = wrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
                newPacket.write(Types.VAR_INT, entityId);
                newPacket.send(Protocol1_17_1To1_17.class);
            }
        });
        this.registerClientbound(ClientboundPackets1_17_1.CONTAINER_CLOSE, wrapper -> {
            short containerId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.user().get(InventoryStateIds.class).removeStateId(containerId);
        });
        this.registerClientbound(ClientboundPackets1_17_1.CONTAINER_SET_SLOT, wrapper -> {
            short containerId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            int stateId = wrapper.read(Types.VAR_INT);
            wrapper.user().get(InventoryStateIds.class).setStateId(containerId, stateId);
        });
        this.registerClientbound(ClientboundPackets1_17_1.CONTAINER_SET_CONTENT, wrapper -> {
            short containerId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            int stateId = wrapper.read(Types.VAR_INT);
            wrapper.user().get(InventoryStateIds.class).setStateId(containerId, stateId);
            wrapper.write(Types.ITEM1_13_2_SHORT_ARRAY, wrapper.read(Types.ITEM1_13_2_ARRAY));
            Item carried = wrapper.read(Types.ITEM1_13_2);
            PlayerLastCursorItem lastCursorItem = wrapper.user().get(PlayerLastCursorItem.class);
            if (lastCursorItem != null) {
                lastCursorItem.setLastCursorItem(carried);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CONTAINER_CLOSE, wrapper -> {
            short containerId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.user().get(InventoryStateIds.class).removeStateId(containerId);
        });
        this.registerServerbound(ServerboundPackets1_17.CONTAINER_CLICK, wrapper -> {
            short containerId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            int stateId = wrapper.user().get(InventoryStateIds.class).removeStateId(containerId);
            wrapper.write(Types.VAR_INT, stateId == Integer.MAX_VALUE ? 0 : stateId);
        });
        this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, wrapper -> {
            ListTag<StringTag> pagesTag;
            Item item = wrapper.read(Types.ITEM1_13_2);
            boolean signing = wrapper.read(Types.BOOLEAN);
            wrapper.passthrough(Types.VAR_INT);
            if (item == null) {
                wrapper.write(Types.VAR_INT, 0);
                wrapper.write(Types.BOOLEAN, false);
                return;
            }
            CompoundTag tag = item.tag();
            StringTag titleTag = null;
            if (tag == null || (pagesTag = tag.getListTag("pages", StringTag.class)) == null || signing && (titleTag = tag.getStringTag("title")) == null) {
                wrapper.write(Types.VAR_INT, 0);
                wrapper.write(Types.BOOLEAN, false);
                return;
            }
            if (pagesTag.size() > 200) {
                pagesTag = new ListTag(pagesTag.getValue().subList(0, 200));
            }
            wrapper.write(Types.VAR_INT, pagesTag.size());
            for (StringTag pageTag : pagesTag) {
                String page = pageTag.getValue();
                if (page.length() > 8192) {
                    page = page.substring(0, 8192);
                }
                wrapper.write(Types.STRING, page);
            }
            wrapper.write(Types.BOOLEAN, signing);
            if (signing) {
                String title2 = titleTag.getValue();
                if (title2.length() > 128) {
                    title2 = title2.substring(0, 128);
                }
                wrapper.write(Types.STRING, title2);
            }
        });
    }

    @Override
    public void init(UserConnection connection) {
        connection.put(new InventoryStateIds());
    }
}

