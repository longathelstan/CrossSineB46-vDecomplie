/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_9_3to1_10;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_3to1_10.rewriter.ItemPacketRewriter1_10;
import com.viaversion.viaversion.protocols.v1_9_3to1_10.storage.ResourcePackTracker;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Protocol1_9_3To1_10
extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Float> TO_NEW_PITCH = new ValueTransformer<Short, Float>((Type)Types.FLOAT){

        @Override
        public Float transform(PacketWrapper wrapper, Short inputValue) {
            return Float.valueOf((float)inputValue.shortValue() / 63.0f);
        }
    };
    public static final ValueTransformer<List<EntityData>, List<EntityData>> TRANSFORM_ENTITY_DATA = new ValueTransformer<List<EntityData>, List<EntityData>>(Types1_9.ENTITY_DATA_LIST){

        @Override
        public List<EntityData> transform(PacketWrapper wrapper, List<EntityData> inputValue) {
            CopyOnWriteArrayList<EntityData> dataList = new CopyOnWriteArrayList<EntityData>(inputValue);
            for (EntityData data : dataList) {
                if (data.id() < 5) continue;
                data.setId(data.id() + 1);
            }
            return dataList;
        }
    };
    final ItemPacketRewriter1_10 itemRewriter = new ItemPacketRewriter1_10(this);

    public Protocol1_9_3To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.itemRewriter.register();
        this.registerClientbound(ClientboundPackets1_9_3.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.UNSIGNED_BYTE, TO_NEW_PITCH);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.UNSIGNED_BYTE, TO_NEW_PITCH);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.VAR_INT, 0);
                    wrapper.set(Types.VAR_INT, 0, Protocol1_9_3To1_10.this.getNewSoundId(id));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types1_9.ENTITY_DATA_LIST, TRANSFORM_ENTITY_DATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_9.ENTITY_DATA_LIST, TRANSFORM_ENTITY_DATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_9.ENTITY_DATA_LIST, TRANSFORM_ENTITY_DATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_10.class);
                    int dimensionId = wrapper.get(Types.INT, 1);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_10.class);
                    int dimensionId = wrapper.get(Types.INT, 0);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_10.class);
            Chunk chunk = wrapper.passthrough(ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment()));
            if (Via.getConfig().isReplacePistons()) {
                int replacementId = Via.getConfig().getPistonReplacementId();
                for (ChunkSection section : chunk.getSections()) {
                    if (section == null) continue;
                    section.palette(PaletteType.BLOCKS).replaceId(36, replacementId);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    ResourcePackTracker tracker = wrapper.user().get(ResourcePackTracker.class);
                    tracker.setLastHash(wrapper.get(Types.STRING, 1));
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    ResourcePackTracker tracker = wrapper.user().get(ResourcePackTracker.class);
                    wrapper.write(Types.STRING, tracker.getLastHash());
                    wrapper.write(Types.VAR_INT, wrapper.read(Types.VAR_INT));
                });
            }
        });
    }

    public int getNewSoundId(int id) {
        int newId = id;
        if (id >= 24) {
            ++newId;
        }
        if (id >= 248) {
            newId += 4;
        }
        if (id >= 296) {
            newId += 6;
        }
        if (id >= 354) {
            newId += 4;
        }
        if (id >= 372) {
            newId += 4;
        }
        return newId;
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addClientWorld(this.getClass(), new ClientWorld());
        userConnection.put(new ResourcePackTracker());
    }

    public ItemPacketRewriter1_10 getItemRewriter() {
        return this.itemRewriter;
    }
}

