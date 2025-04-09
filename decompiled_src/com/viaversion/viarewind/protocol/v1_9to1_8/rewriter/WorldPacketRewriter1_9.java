/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.data.EffectIdMappings1_8;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import java.util.ArrayList;

public class WorldPacketRewriter1_9
extends RewriterBase<Protocol1_9To1_8> {
    public WorldPacketRewriter1_9(Protocol1_9To1_8 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    CompoundTag spawnData;
                    Tag id;
                    CompoundTag tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    Tag patt2864$temp = tag.remove("SpawnData");
                    if (patt2864$temp instanceof CompoundTag && (id = (spawnData = (CompoundTag)patt2864$temp).remove("id")) instanceof StringTag) {
                        tag.put("EntityId", id);
                    }
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int block = wrapper.get(Types.VAR_INT, 0);
                    if (block >= 219 && block <= 234) {
                        wrapper.set(Types.VAR_INT, 0, 130);
                    }
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    String name = wrapper.get(Types.STRING, 0);
                    name = ((Protocol1_9To1_8)WorldPacketRewriter1_9.this.protocol).getMappingData().getMappedNamedSound(name);
                    if (name == null) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Types.STRING, 0, name);
                    }
                });
                this.read(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.FORGET_LEVEL_CHUNK, ClientboundPackets1_8.LEVEL_CHUNK, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    Environment environment = ((ClientWorld)wrapper.user().getClientWorld(Protocol1_9To1_8.class)).getEnvironment();
                    int chunkX = wrapper.read(Types.INT);
                    int chunkZ = wrapper.read(Types.INT);
                    wrapper.write(ChunkType1_8.forEnvironment(environment), new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>()));
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.LEVEL_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    Environment environment = ((ClientWorld)wrapper.user().getClientWorld(Protocol1_9To1_8.class)).getEnvironment();
                    Chunk chunk = wrapper.read(ChunkType1_9_1.forEnvironment(environment));
                    for (ChunkSection section : chunk.getSections()) {
                        if (section == null) continue;
                        DataPalette palette = section.palette(PaletteType.BLOCKS);
                        for (int i = 0; i < palette.size(); ++i) {
                            int block = palette.idByIndex(i);
                            int replacedBlock = ((Protocol1_9To1_8)WorldPacketRewriter1_9.this.protocol).getItemRewriter().handleBlockId(block);
                            palette.setIdByIndex(i, replacedBlock);
                        }
                    }
                    if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                        boolean skylight = environment == Environment.NORMAL;
                        ChunkSection[] sections = new ChunkSection[16];
                        ChunkSectionImpl section = new ChunkSectionImpl(true);
                        sections[0] = section;
                        section.palette(PaletteType.BLOCKS).addId(0);
                        if (skylight) {
                            section.getLight().setSkyLight(new byte[2048]);
                        }
                        chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
                    }
                    wrapper.write(ChunkType1_8.forEnvironment(environment), chunk);
                    chunk.getBlockEntities().forEach(nbt -> {
                        short action;
                        String id;
                        if (!(nbt.contains("x") && nbt.contains("y") && nbt.contains("z") && nbt.contains("id"))) {
                            return;
                        }
                        BlockPosition position = new BlockPosition((Integer)nbt.get("x").getValue(), (Integer)nbt.get("y").getValue(), (Integer)nbt.get("z").getValue());
                        switch (id = (String)nbt.get("id").getValue()) {
                            case "minecraft:mob_spawner": {
                                action = 1;
                                break;
                            }
                            case "minecraft:command_block": {
                                action = 2;
                                break;
                            }
                            case "minecraft:beacon": {
                                action = 3;
                                break;
                            }
                            case "minecraft:skull": {
                                action = 4;
                                break;
                            }
                            case "minecraft:flower_pot": {
                                action = 5;
                                break;
                            }
                            case "minecraft:banner": {
                                action = 6;
                                break;
                            }
                            default: {
                                return;
                            }
                        }
                        PacketWrapper blockEntityData = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, wrapper.user());
                        blockEntityData.write(Types.BLOCK_POSITION1_8, position);
                        blockEntityData.write(Types.UNSIGNED_BYTE, action);
                        blockEntityData.write(Types.NAMED_COMPOUND_TAG, nbt);
                        blockEntityData.scheduleSend(Protocol1_9To1_8.class, false);
                    });
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if ((id = EffectIdMappings1_8.getOldId(id)) == -1) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.set(Types.INT, 0, id);
                    if (id == 2001) {
                        int replacedBlock = ((Protocol1_9To1_8)WorldPacketRewriter1_9.this.protocol).getItemRewriter().handleBlockId(wrapper.get(Types.INT, 1));
                        wrapper.set(Types.INT, 1, replacedBlock);
                    }
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if (id > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                        wrapper.cancel();
                        return;
                    }
                    if (id == 42) {
                        wrapper.set(Types.INT, 0, 24);
                    } else if (id == 43) {
                        wrapper.set(Types.INT, 0, 3);
                    } else if (id == 44) {
                        wrapper.set(Types.INT, 0, 34);
                    } else if (id == 45) {
                        wrapper.set(Types.INT, 0, 1);
                    }
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.read(Types.BOOLEAN);
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.SOUND, ClientboundPackets1_8.CUSTOM_SOUND, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    int soundId = wrapper.read(Types.VAR_INT);
                    String soundName = ((Protocol1_9To1_8)WorldPacketRewriter1_9.this.protocol).getMappingData().soundName(soundId);
                    if (soundName == null) {
                        wrapper.cancel();
                    } else {
                        wrapper.write(Types.STRING, ((Protocol1_9To1_8)WorldPacketRewriter1_9.this.protocol).getMappingData().getMappedNamedSound(soundName));
                    }
                });
                this.read(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
    }
}

