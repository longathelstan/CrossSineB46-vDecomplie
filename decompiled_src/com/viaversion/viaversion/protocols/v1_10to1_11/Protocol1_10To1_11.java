/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_10to1_11;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_10to1_11.data.BlockEntityMappings1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.data.EntityMappings1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.data.PotionColorMappings1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.rewriter.EntityPacketRewriter1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.rewriter.ItemPacketRewriter1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.storage.EntityTracker1_11;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.Pair;

public class Protocol1_10To1_11
extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    static final ValueTransformer<Float, Short> toOldByte = new ValueTransformer<Float, Short>((Type)Types.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper wrapper, Float inputValue) {
            return (short)(inputValue.floatValue() * 16.0f);
        }
    };
    final EntityPacketRewriter1_11 entityRewriter = new EntityPacketRewriter1_11(this);
    final ItemPacketRewriter1_11 itemRewriter = new ItemPacketRewriter1_11(this);

    public Protocol1_10To1_11() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        new SoundRewriter<ClientboundPackets1_9_3>(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        this.registerClientbound(ClientboundPackets1_9_3.SET_TITLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Types.VAR_INT, 0);
                    if (action >= 2) {
                        wrapper.set(Types.VAR_INT, 0, action + 1);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(actionWrapper -> {
                    int id;
                    if (Via.getConfig().isPistonAnimationPatch() && ((id = actionWrapper.get(Types.VAR_INT, 0).intValue()) == 33 || id == 29)) {
                        actionWrapper.cancel();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_10To1_11.class);
            Chunk chunk = wrapper.passthrough(ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment()));
            if (chunk.getBlockEntities() == null) {
                return;
            }
            for (CompoundTag tag : chunk.getBlockEntities()) {
                StringTag idTag = tag.getStringTag("id");
                if (idTag == null) continue;
                String identifier = idTag.getValue();
                if (identifier.equals("MobSpawner")) {
                    EntityMappings1_11.toClientSpawner(tag);
                }
                idTag.setValue(BlockEntityMappings1_11.toNewIdentifier(identifier));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.handler(packetWrapper -> {
                    int effectID = packetWrapper.get(Types.INT, 0);
                    if (effectID == 2002) {
                        int data = packetWrapper.get(Types.INT, 1);
                        boolean isInstant = false;
                        Pair<Integer, Boolean> newData = PotionColorMappings1_11.getNewData(data);
                        if (newData == null) {
                            int n = data;
                            Protocol1_10To1_11.this.getLogger().warning("Received unknown potion data: " + n);
                            data = 0;
                        } else {
                            data = newData.key();
                            isInstant = newData.value();
                        }
                        if (isInstant) {
                            packetWrapper.set(Types.INT, 0, 2007);
                        }
                        packetWrapper.set(Types.INT, 1, data);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.FLOAT, toOldByte);
                this.map(Types.FLOAT, toOldByte);
                this.map(Types.FLOAT, toOldByte);
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    String msg = wrapper.get(Types.STRING, 0);
                    if (msg.length() > 100) {
                        wrapper.set(Types.STRING, 0, msg.substring(0, 100));
                    }
                });
            }
        });
    }

    int getNewSoundId(int id) {
        if (id == 196) {
            return -1;
        }
        if (id >= 85) {
            id += 2;
        }
        if (id >= 176) {
            ++id;
        }
        if (id >= 197) {
            id += 8;
        }
        if (id >= 207) {
            --id;
        }
        if (id >= 279) {
            id += 9;
        }
        if (id >= 296) {
            ++id;
        }
        if (id >= 390) {
            id += 4;
        }
        if (id >= 400) {
            id += 3;
        }
        if (id >= 450) {
            ++id;
        }
        if (id >= 455) {
            ++id;
        }
        if (id >= 470) {
            ++id;
        }
        return id;
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_11(userConnection));
        userConnection.addClientWorld(this.getClass(), new ClientWorld());
    }

    public EntityPacketRewriter1_11 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_11 getItemRewriter() {
        return this.itemRewriter;
    }
}

