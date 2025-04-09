/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viarewind.api.type.chunk.BulkChunkType1_7_6;
import com.viaversion.viarewind.api.type.chunk.ChunkType1_7_6;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.Particles1_8;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.WorldBorderEmulator;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.FixedByteArrayType;
import com.viaversion.viaversion.api.type.types.chunk.BulkChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.IdAndData;

public class WorldPacketRewriter1_8
extends RewriterBase<Protocol1_8To1_7_6_10> {
    public WorldPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LEVEL_CHUNK, wrapper -> {
            Object world = wrapper.user().getClientWorld(Protocol1_8To1_7_6_10.class);
            Chunk chunk = wrapper.read(ChunkType1_8.forEnvironment(((ClientWorld)world).getEnvironment()));
            ((Protocol1_8To1_7_6_10)this.protocol).getItemRewriter().handleChunk(chunk);
            wrapper.write(ChunkType1_7_6.TYPE, chunk);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.U_BYTE_POSITION);
                this.handler(wrapper -> {
                    int data = wrapper.read(Types.VAR_INT);
                    data = ((Protocol1_8To1_7_6_10)WorldPacketRewriter1_8.this.protocol).getItemRewriter().handleBlockId(data);
                    wrapper.write(Types.VAR_INT, IdAndData.getId(data));
                    wrapper.write(Types.UNSIGNED_BYTE, (short)IdAndData.getData(data));
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CHUNK_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    BlockChangeRecord[] records = wrapper.read(Types.BLOCK_CHANGE_ARRAY);
                    wrapper.write(Types.SHORT, (short)records.length);
                    wrapper.write(Types.INT, records.length * 4);
                    for (BlockChangeRecord record : records) {
                        wrapper.write(Types.SHORT, (short)(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY()));
                        wrapper.write(Types.SHORT, (short)((Protocol1_8To1_7_6_10)WorldPacketRewriter1_8.this.protocol).getItemRewriter().handleBlockId(record.getBlockId()));
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.SHORT_POSITION);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_DESTRUCTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
                this.map(Types.BYTE);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, wrapper -> {
            Chunk[] chunks;
            for (Chunk chunk : chunks = wrapper.read(BulkChunkType1_8.TYPE)) {
                ((Protocol1_8To1_7_6_10)this.protocol).getItemRewriter().handleChunk(chunk);
            }
            wrapper.write(BulkChunkType1_7_6.TYPE, chunks);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.BYTE_POSITION);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    int particleId = wrapper.read(Types.INT);
                    Particles1_8 particle = Particles1_8.find(particleId);
                    if (particle == null) {
                        particle = Particles1_8.CRIT;
                    }
                    wrapper.write(Types.STRING, particle.name);
                });
                this.read(Types.BOOLEAN);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    String name = wrapper.get(Types.STRING, 0);
                    Particles1_8 particle = Particles1_8.find(name);
                    if (particle == Particles1_8.ICON_CRACK || particle == Particles1_8.BLOCK_CRACK || particle == Particles1_8.BLOCK_DUST) {
                        int data;
                        int id = wrapper.read(Types.VAR_INT);
                        int n = data = particle == Particles1_8.ICON_CRACK ? wrapper.read(Types.VAR_INT) : id / 4096;
                        if ((id %= 4096) >= 256 && id <= 422 || id >= 2256 && id <= 2267) {
                            particle = Particles1_8.ICON_CRACK;
                        } else if (id >= 0 && id <= 164 || id >= 170 && id <= 175) {
                            if (particle == Particles1_8.ICON_CRACK) {
                                particle = Particles1_8.BLOCK_CRACK;
                            }
                        } else {
                            wrapper.cancel();
                            return;
                        }
                        int n2 = data;
                        int n3 = id;
                        String string = particle.name;
                        name = string + "_" + n3 + "_" + n2;
                    }
                    wrapper.set(Types.STRING, 0, name);
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.SHORT_POSITION);
                this.handler(wrapper -> {
                    for (int i = 0; i < 4; ++i) {
                        String line = wrapper.read(Types.STRING);
                        line = ChatUtil.jsonToLegacy(line);
                        if ((line = ChatUtil.removeUnusedColor(line, '0')).length() > 15 && (line = ChatColorUtil.stripColor(line)).length() > 15) {
                            line = line.substring(0, 15);
                        }
                        wrapper.write(Types.STRING, line);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MAP_ITEM_DATA, wrapper -> {
            wrapper.cancel();
            int id = wrapper.read(Types.VAR_INT);
            byte scale = wrapper.read(Types.BYTE);
            int iconCount = wrapper.read(Types.VAR_INT);
            byte[] icons = new byte[iconCount * 4];
            for (int i = 0; i < iconCount; ++i) {
                byte directionAndType = wrapper.read(Types.BYTE);
                icons[i * 4] = (byte)(directionAndType >> 4 & 0xF);
                icons[i * 4 + 1] = wrapper.read(Types.BYTE);
                icons[i * 4 + 2] = wrapper.read(Types.BYTE);
                icons[i * 4 + 3] = (byte)(directionAndType & 0xF);
            }
            int columns = wrapper.read(Types.UNSIGNED_BYTE).shortValue();
            if (columns > 0) {
                int rows = wrapper.read(Types.UNSIGNED_BYTE).shortValue();
                short x = wrapper.read(Types.UNSIGNED_BYTE);
                short z = wrapper.read(Types.UNSIGNED_BYTE);
                byte[] data = wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                for (int column = 0; column < columns; ++column) {
                    byte[] columnData = new byte[rows + 3];
                    columnData[0] = 0;
                    columnData[1] = (byte)(x + column);
                    columnData[2] = (byte)z;
                    for (int i = 0; i < rows; ++i) {
                        columnData[i + 3] = data[column + i * columns];
                    }
                    PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_ITEM_DATA, wrapper.user());
                    mapData.write(Types.VAR_INT, id);
                    mapData.write(Types.SHORT, (short)columnData.length);
                    mapData.write(new FixedByteArrayType(columnData.length), columnData);
                    mapData.send(Protocol1_8To1_7_6_10.class);
                }
            }
            if (iconCount > 0) {
                byte[] iconData = new byte[iconCount * 3 + 1];
                iconData[0] = 1;
                for (int i = 0; i < iconCount; ++i) {
                    iconData[i * 3 + 1] = (byte)(icons[i * 4] << 4 | icons[i * 4 + 3] & 0xF);
                    iconData[i * 3 + 2] = icons[i * 4 + 1];
                    iconData[i * 3 + 3] = icons[i * 4 + 2];
                }
                PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_ITEM_DATA, wrapper.user());
                mapData.write(Types.VAR_INT, id);
                mapData.write(Types.SHORT, (short)iconData.length);
                mapData.write(new FixedByteArrayType(iconData.length), iconData);
                mapData.send(Protocol1_8To1_7_6_10.class);
            }
            PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_ITEM_DATA, wrapper.user());
            mapData.write(Types.VAR_INT, id);
            mapData.write(Types.SHORT, (short)2);
            mapData.write(new FixedByteArrayType(2), new byte[]{2, scale});
            mapData.send(Protocol1_8To1_7_6_10.class);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.SHORT_POSITION);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG, RewindTypes.COMPRESSED_NBT);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.CHANGE_DIFFICULTY);
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_BORDER, null, wrapper -> {
            WorldBorderEmulator emulator = wrapper.user().get(WorldBorderEmulator.class);
            wrapper.cancel();
            int action = wrapper.read(Types.VAR_INT);
            if (action == 0) {
                emulator.setSize(wrapper.read(Types.DOUBLE));
            } else if (action == 1) {
                emulator.lerpSize(wrapper.read(Types.DOUBLE), wrapper.read(Types.DOUBLE), wrapper.read(Types.VAR_LONG));
            } else if (action == 2) {
                emulator.setCenter(wrapper.read(Types.DOUBLE), wrapper.read(Types.DOUBLE));
            } else if (action == 3) {
                emulator.init(wrapper.read(Types.DOUBLE), wrapper.read(Types.DOUBLE), wrapper.read(Types.DOUBLE), wrapper.read(Types.DOUBLE), wrapper.read(Types.VAR_LONG));
            }
        });
    }
}

