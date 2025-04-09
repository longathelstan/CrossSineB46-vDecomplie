/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_11_1to1_12;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.data.ChatItemRewriter;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.data.TranslateRewriter;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.provider.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.rewriter.EntityPacketRewriter1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.rewriter.ItemPacketRewriter1_12;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.SoundRewriter;

public class Protocol1_11_1To1_12
extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_12, ServerboundPackets1_9_3, ServerboundPackets1_12> {
    final EntityPacketRewriter1_12 entityRewriter = new EntityPacketRewriter1_12(this);
    final ItemPacketRewriter1_12 itemRewriter = new ItemPacketRewriter1_12(this);

    public Protocol1_11_1To1_12() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_9_3.CHAT, wrapper -> {
            if (!Via.getConfig().is1_12NBTArrayFix()) {
                return;
            }
            JsonElement element = wrapper.passthrough(Types.COMPONENT);
            TranslateRewriter.toClient(wrapper.user(), element);
            ChatItemRewriter.toClient(element);
            wrapper.set(Types.COMPONENT, 0, element);
        });
        this.registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_11_1To1_12.class);
            ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.passthrough(type);
            for (int s = 0; s < chunk.getSections().length; ++s) {
                ChunkSection section = chunk.getSections()[s];
                if (section == null) continue;
                DataPalette blocks = section.palette(PaletteType.BLOCKS);
                for (int idx = 0; idx < 4096; ++idx) {
                    int id = blocks.idAt(idx) >> 4;
                    if (id != 26) continue;
                    CompoundTag tag = new CompoundTag();
                    tag.put("color", new IntTag(14));
                    tag.put("x", new IntTag(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4)));
                    tag.put("y", new IntTag(ChunkSection.yFromIndex(idx) + (s << 4)));
                    tag.put("z", new IntTag(ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4)));
                    tag.put("id", new StringTag("minecraft:bed"));
                    chunk.getBlockEntities().add(tag);
                }
            }
        });
        new SoundRewriter<ClientboundPackets1_9_3>(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        this.cancelServerbound(ServerboundPackets1_12.CRAFTING_RECIPE_PLACEMENT);
        this.registerServerbound(ServerboundPackets1_12.CLIENT_INFORMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    String locale = wrapper.get(Types.STRING, 0);
                    if (locale.length() > 7) {
                        wrapper.set(Types.STRING, 0, locale.substring(0, 7));
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_UPDATE);
        this.cancelServerbound(ServerboundPackets1_12.SEEN_ADVANCEMENTS);
    }

    int getNewSoundId(int id) {
        int newId = id;
        if (id >= 26) {
            newId += 2;
        }
        if (id >= 70) {
            newId += 4;
        }
        if (id >= 74) {
            ++newId;
        }
        if (id >= 143) {
            newId += 3;
        }
        if (id >= 185) {
            ++newId;
        }
        if (id >= 263) {
            newId += 7;
        }
        if (id >= 301) {
            newId += 33;
        }
        if (id >= 317) {
            newId += 2;
        }
        if (id >= 491) {
            newId += 3;
        }
        return newId;
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(InventoryQuickMoveProvider.class, new InventoryQuickMoveProvider());
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_12.EntityType.PLAYER));
        userConnection.addClientWorld(this.getClass(), new ClientWorld());
    }

    public EntityPacketRewriter1_12 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_12 getItemRewriter() {
        return this.itemRewriter;
    }
}

