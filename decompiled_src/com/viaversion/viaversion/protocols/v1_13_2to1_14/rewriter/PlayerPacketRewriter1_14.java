/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;

public class PlayerPacketRewriter1_14 {
    public static void register(Protocol1_13_2To1_14 protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.OPEN_SIGN_EDITOR, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8, Types.BLOCK_POSITION1_14);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.BLOCK_ENTITY_TAG_QUERY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> {
            Item item = wrapper.passthrough(Types.ITEM1_13_2);
            protocol.getItemRewriter().handleItemToServer(wrapper.user(), item);
            if (item == null) {
                return;
            }
            CompoundTag tag = item.tag();
            if (tag == null) {
                return;
            }
            ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
            if (pages == null) {
                pages = new ListTag<StringTag>(StringTag.class);
                pages.add(new StringTag());
                tag.put("pages", pages);
                return;
            }
            if (Via.getConfig().isTruncate1_14Books() && pages.size() > 50) {
                pages.setValue(pages.getValue().subList(0, 50));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.PLAYER_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.RECIPE_BOOK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int type = wrapper.get(Types.VAR_INT, 0);
                    if (type == 0) {
                        wrapper.passthrough(Types.STRING);
                    } else if (type == 1) {
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.read(Types.BOOLEAN);
                        wrapper.read(Types.BOOLEAN);
                        wrapper.read(Types.BOOLEAN);
                        wrapper.read(Types.BOOLEAN);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.SET_COMMAND_BLOCK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.SET_STRUCTURE_BLOCK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.SIGN_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.USE_ITEM_ON, wrapper -> {
            int hand = wrapper.read(Types.VAR_INT);
            BlockPosition position = wrapper.read(Types.BLOCK_POSITION1_14);
            int face = wrapper.read(Types.VAR_INT);
            float x = wrapper.read(Types.FLOAT).floatValue();
            float y = wrapper.read(Types.FLOAT).floatValue();
            float z = wrapper.read(Types.FLOAT).floatValue();
            wrapper.read(Types.BOOLEAN);
            wrapper.write(Types.BLOCK_POSITION1_8, position);
            wrapper.write(Types.VAR_INT, face);
            wrapper.write(Types.VAR_INT, hand);
            wrapper.write(Types.FLOAT, Float.valueOf(x));
            wrapper.write(Types.FLOAT, Float.valueOf(y));
            wrapper.write(Types.FLOAT, Float.valueOf(z));
        });
    }
}

