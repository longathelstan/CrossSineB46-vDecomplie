/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.base.ServerboundHandshakePackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundStatusPackets;
import com.viaversion.viaversion.protocols.base.v1_7.ClientboundBaseProtocol1_7;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.IdAndData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.List;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.protocol.StatelessTransitionProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.PacketUtil;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ClientboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ServerboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.provider.EncryptionProvider;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.SoundRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.StatisticRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.TextRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.HandshakeStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.PlayerInfoStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ProtocolMetadataStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.StatisticsStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataTypes1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ClientboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ServerboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider.GameProfileFetcher;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.EntityDataTypes1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_6_4Tor1_7_2_5
extends StatelessTransitionProtocol<ClientboundPackets1_6_4, ClientboundPackets1_7_2, ServerboundPackets1_6_4, ServerboundPackets1_7_2> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_6_4Tor1_7_2_5() {
        super(ClientboundPackets1_6_4.class, ClientboundPackets1_7_2.class, ServerboundPackets1_6_4.class, ServerboundPackets1_7_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientboundTransition(ClientboundPackets1_6_4.LOGIN, new Object[]{ClientboundPackets1_7_2.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    wrapper.user().get(PlayerInfoStorage.class).entityId = wrapper.get(Types.INT, 0);
                    String terrainType = wrapper.read(Types1_6_4.STRING);
                    short gameType = wrapper.read(Types.BYTE).byteValue();
                    byte dimension = wrapper.read(Types.BYTE);
                    short difficulty = wrapper.read(Types.BYTE).byteValue();
                    wrapper.read(Types.BYTE);
                    short maxPlayers = wrapper.read(Types.BYTE).byteValue();
                    wrapper.write(Types.UNSIGNED_BYTE, gameType);
                    wrapper.write(Types.BYTE, dimension);
                    wrapper.write(Types.UNSIGNED_BYTE, difficulty);
                    wrapper.write(Types.UNSIGNED_BYTE, maxPlayers);
                    wrapper.write(Types.STRING, terrainType);
                });
                this.handler(wrapper -> {
                    byte dimensionId = wrapper.get(Types.BYTE, 0);
                    ((ClientWorld)wrapper.user().getClientWorld(Protocolr1_6_4Tor1_7_2_5.class)).setEnvironment(dimensionId);
                    wrapper.user().put(new ChunkTracker(wrapper.user()));
                });
            }
        }, State.LOGIN, wrapper -> {
            ViaLegacy.getPlatform().getLogger().warning("Server skipped LOGIN state");
            PacketWrapper sharedKey = PacketWrapper.create(ClientboundPackets1_6_4.SHARED_KEY, wrapper.user());
            sharedKey.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
            sharedKey.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
            wrapper.user().get(ProtocolMetadataStorage.class).skipEncryption = true;
            sharedKey.send(Protocolr1_6_4Tor1_7_2_5.class, false);
            wrapper.user().get(ProtocolMetadataStorage.class).skipEncryption = false;
            wrapper.setPacketType(ClientboundPackets1_6_4.LOGIN);
            wrapper.send(Protocolr1_6_4Tor1_7_2_5.class, false);
            wrapper.cancel();
        }});
        this.registerClientbound(ClientboundPackets1_6_4.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING, TextRewriter::toClient);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.SHORT);
                this.map(Types1_7_6.ITEM);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), wrapper.get(Types1_7_6.ITEM, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.read(Types.SHORT);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.handler(wrapper -> {
                    if (((ClientWorld)wrapper.user().getClientWorld(Protocolr1_6_4Tor1_7_2_5.class)).setEnvironment(wrapper.get(Types.INT, 0))) {
                        wrapper.user().get(ChunkTracker.class).clear();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.MOVE_PLAYER_STATUS_ONLY, ClientboundPackets1_7_2.PLAYER_POSITION, (PacketWrapper wrapper) -> {
            PlayerInfoStorage playerInfoStorage = wrapper.user().get(PlayerInfoStorage.class);
            boolean supportsFlags = wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_8);
            wrapper.write(Types.DOUBLE, supportsFlags ? 0.0 : playerInfoStorage.posX);
            wrapper.write(Types.DOUBLE, supportsFlags ? 0.0 : playerInfoStorage.posY + (double)1.62f);
            wrapper.write(Types.DOUBLE, supportsFlags ? 0.0 : playerInfoStorage.posZ);
            wrapper.write(Types.FLOAT, Float.valueOf(supportsFlags ? 0.0f : playerInfoStorage.yaw));
            wrapper.write(Types.FLOAT, Float.valueOf(supportsFlags ? 0.0f : playerInfoStorage.pitch));
            if (supportsFlags) {
                wrapper.read(Types.BOOLEAN);
                wrapper.write(Types.BYTE, (byte)31);
                wrapper.setPacketType(ClientboundPackets1_8.PLAYER_POSITION);
                wrapper.send(Protocolr1_7_6_10Tor1_8.class);
                wrapper.cancel();
            } else {
                wrapper.passthrough(Types.BOOLEAN);
            }
            PacketWrapper setVelocityToZero = PacketWrapper.create(ClientboundPackets1_7_2.SET_ENTITY_MOTION, wrapper.user());
            setVelocityToZero.write(Types.INT, playerInfoStorage.entityId);
            setVelocityToZero.write(Types.SHORT, (short)0);
            setVelocityToZero.write(Types.SHORT, (short)0);
            setVelocityToZero.write(Types.SHORT, (short)0);
            if (!wrapper.isCancelled()) {
                wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
            }
            setVelocityToZero.send(Protocolr1_6_4Tor1_7_2_5.class);
            wrapper.cancel();
        });
        this.registerClientbound(ClientboundPackets1_6_4.MOVE_PLAYER_POS, ClientboundPackets1_7_2.PLAYER_POSITION, (PacketWrapper wrapper) -> {
            PlayerInfoStorage playerInfoStorage = wrapper.user().get(PlayerInfoStorage.class);
            boolean supportsFlags = wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_8);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.read(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.write(Types.FLOAT, Float.valueOf(supportsFlags ? 0.0f : playerInfoStorage.yaw));
            wrapper.write(Types.FLOAT, Float.valueOf(supportsFlags ? 0.0f : playerInfoStorage.pitch));
            if (supportsFlags) {
                wrapper.read(Types.BOOLEAN);
                wrapper.write(Types.BYTE, (byte)24);
                wrapper.setPacketType(ClientboundPackets1_8.PLAYER_POSITION);
                wrapper.send(Protocolr1_7_6_10Tor1_8.class);
                wrapper.cancel();
            } else {
                wrapper.passthrough(Types.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.MOVE_PLAYER_ROT, ClientboundPackets1_7_2.PLAYER_POSITION, (PacketWrapper wrapper) -> {
            PlayerInfoStorage playerInfoStorage = wrapper.user().get(PlayerInfoStorage.class);
            boolean supportsFlags = wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_8);
            wrapper.write(Types.DOUBLE, supportsFlags ? 0.0 : playerInfoStorage.posX);
            wrapper.write(Types.DOUBLE, supportsFlags ? 0.0 : playerInfoStorage.posY + (double)1.62f);
            wrapper.write(Types.DOUBLE, supportsFlags ? 0.0 : playerInfoStorage.posZ);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            if (supportsFlags) {
                wrapper.read(Types.BOOLEAN);
                wrapper.write(Types.BYTE, (byte)7);
                wrapper.setPacketType(ClientboundPackets1_8.PLAYER_POSITION);
                wrapper.send(Protocolr1_7_6_10Tor1_8.class);
                wrapper.cancel();
            } else {
                wrapper.passthrough(Types.BOOLEAN);
            }
            PacketWrapper setVelocityToZero = PacketWrapper.create(ClientboundPackets1_7_2.SET_ENTITY_MOTION, wrapper.user());
            setVelocityToZero.write(Types.INT, playerInfoStorage.entityId);
            setVelocityToZero.write(Types.SHORT, (short)0);
            setVelocityToZero.write(Types.SHORT, (short)0);
            setVelocityToZero.write(Types.SHORT, (short)0);
            if (!wrapper.isCancelled()) {
                wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
            }
            setVelocityToZero.send(Protocolr1_6_4Tor1_7_2_5.class);
            wrapper.cancel();
        });
        this.registerClientbound(ClientboundPackets1_6_4.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.read(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_CARRIED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT, Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.PLAYER_SLEEP, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    if (wrapper.read(Types.BYTE) != 0) {
                        wrapper.cancel();
                    }
                });
                this.map(Types1_7_6.BLOCK_POSITION_BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ANIMATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.handler(wrapper -> {
                    short animate = wrapper.read(Types.BYTE).byteValue();
                    if (animate == 0 || animate == 4) {
                        wrapper.cancel();
                    }
                    animate = animate >= 1 && animate <= 3 ? (short)(animate - 1) : (short)(animate - 2);
                    wrapper.write(Types.UNSIGNED_BYTE, animate);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.handler(wrapper -> {
                    String name = wrapper.read(Types1_6_4.STRING);
                    wrapper.write(Types.STRING, (ViaLegacy.getConfig().isLegacySkinLoading() ? Via.getManager().getProviders().get(GameProfileFetcher.class).getMojangUUID(name) : new GameProfile((String)name).uuid).toString().replace("-", ""));
                    wrapper.write(Types.STRING, name);
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    DataItem currentItem = new DataItem(wrapper.read(Types.UNSIGNED_SHORT), 1, 0, null);
                    Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), currentItem);
                    wrapper.write(Types.SHORT, (short)currentItem.identifier());
                });
                this.map(Types1_6_4.ENTITY_DATA_LIST, Types1_7_6.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.rewriteEntityData(wrapper.user(), wrapper.get(Types1_7_6.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int data = wrapper.get(Types.INT, 3);
                    if (EntityTypes1_8.getTypeFromId(wrapper.get(Types.BYTE, 0).byteValue(), true) == EntityTypes1_8.ObjectType.FALLING_BLOCK.getType()) {
                        int id = data & 0xFFFF;
                        int metadata = data >> 16;
                        IdAndData block = new IdAndData(id, metadata);
                        wrapper.user().get(ChunkTracker.class).remapBlockParticle(block);
                        data = block.getId() & 0xFFFF | block.getData() << 16;
                    }
                    wrapper.set(Types.INT, 3, data);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_6_4.ENTITY_DATA_LIST, Types1_7_6.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.rewriteEntityData(wrapper.user(), wrapper.get(Types1_7_6.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types1_7_6.BLOCK_POSITION_INT);
                this.map(Types.INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ADD_EXPERIENCE_ORB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.SHORT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.ENTITY_DATA_LIST, Types1_7_6.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.rewriteEntityData(wrapper.user(), wrapper.get(Types1_7_6.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.UPDATE_ATTRIBUTES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int amount = wrapper.passthrough(Types.INT);
                    for (int i = 0; i < amount; ++i) {
                        wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
                        wrapper.passthrough(Types.DOUBLE);
                        int modifierCount = wrapper.passthrough(Types.SHORT).shortValue();
                        for (int x = 0; x < modifierCount; ++x) {
                            wrapper.passthrough(Types.UUID);
                            wrapper.passthrough(Types.DOUBLE);
                            wrapper.passthrough(Types.BYTE);
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.LEVEL_CHUNK, wrapper -> {
            Chunk chunk = wrapper.passthrough(Types1_7_6.getChunk(((ClientWorld)wrapper.user().getClientWorld(Protocolr1_6_4Tor1_7_2_5.class)).getEnvironment()));
            wrapper.user().get(ChunkTracker.class).trackAndRemap(chunk);
        });
        this.registerClientbound(ClientboundPackets1_6_4.CHUNK_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(wrapper -> {
                    BlockChangeRecord[] blockChangeRecords;
                    int chunkX = wrapper.get(Types.INT, 0);
                    int chunkZ = wrapper.get(Types.INT, 1);
                    for (BlockChangeRecord record : blockChangeRecords = wrapper.get(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                        int targetX = record.getSectionX() + (chunkX << 4);
                        short targetY = record.getY(-1);
                        int targetZ = record.getSectionZ() + (chunkZ << 4);
                        IdAndData block = IdAndData.fromRawData(record.getBlockId());
                        BlockPosition pos = new BlockPosition(targetX, targetY, targetZ);
                        wrapper.user().get(ChunkTracker.class).trackAndRemap(pos, block);
                        record.setBlockId(block.toRawData());
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_SHORT, Types.VAR_INT);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    BlockPosition pos = wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
                    int blockId = wrapper.get(Types.VAR_INT, 0);
                    short data = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    IdAndData block = new IdAndData(blockId, data);
                    wrapper.user().get(ChunkTracker.class).trackAndRemap(pos, block);
                    wrapper.set(Types.VAR_INT, 0, block.getId());
                    wrapper.set(Types.UNSIGNED_BYTE, 0, Short.valueOf(block.getData()));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.map(Types.SHORT, Types.VAR_INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.BLOCK_DESTRUCTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types1_7_6.BLOCK_POSITION_INT);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.MAP_BULK_CHUNK, wrapper -> {
            Chunk[] chunks;
            for (Chunk chunk : chunks = wrapper.passthrough(Types1_7_6.CHUNK_BULK)) {
                wrapper.user().get(ChunkTracker.class).trackAndRemap(chunk);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE, Types.FLOAT);
                this.map(Types.DOUBLE, Types.FLOAT);
                this.map(Types.DOUBLE, Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int x = wrapper.get(Types.FLOAT, 0).intValue();
                    int y = wrapper.get(Types.FLOAT, 1).intValue();
                    int z = wrapper.get(Types.FLOAT, 2).intValue();
                    int recordCount = wrapper.get(Types.INT, 0);
                    ChunkTracker chunkTracker = wrapper.user().get(ChunkTracker.class);
                    for (int i = 0; i < recordCount; ++i) {
                        BlockPosition pos = new BlockPosition(x + wrapper.passthrough(Types.BYTE), y + wrapper.passthrough(Types.BYTE), z + wrapper.passthrough(Types.BYTE));
                        chunkTracker.trackAndRemap(pos, new IdAndData(0, 0));
                    }
                });
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String oldSound = wrapper.read(Types1_6_4.STRING);
                    String newSound = SoundRewriter.map(oldSound);
                    if (oldSound.isEmpty()) {
                        newSound = "";
                    }
                    if (newSound == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            String string = oldSound;
                            ViaLegacy.getPlatform().getLogger().warning("Unable to map 1.6.4 sound '" + string + "'");
                        }
                        newSound = "";
                    }
                    if (newSound.isEmpty()) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.write(Types.STRING, newSound);
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    int effectId = wrapper.get(Types.INT, 0);
                    int data = wrapper.get(Types.INT, 1);
                    boolean disableRelativeVolume = wrapper.get(Types.BOOLEAN, 0);
                    if (!disableRelativeVolume && effectId == 2001) {
                        ChunkTracker chunkTracker = wrapper.user().get(ChunkTracker.class);
                        int blockID = data & 0xFFF;
                        int blockData = data >> 12 & 0xFF;
                        IdAndData block = new IdAndData(blockID, blockData);
                        chunkTracker.remapBlockParticle(block);
                        data = block.getId() & 0xFFF | block.getData() << 12;
                        wrapper.set(Types.INT, 1, data);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    CharSequence[] parts = wrapper.get(Types.STRING, 0).split("_", 3);
                    if (parts[0].equals("tilecrack")) {
                        parts[0] = "blockcrack";
                    }
                    if (parts[0].equals("blockcrack") || parts[0].equals("blockdust")) {
                        int id = Integer.parseInt(parts[1]);
                        int metadata = Integer.parseInt(parts[2]);
                        IdAndData block = new IdAndData(id, metadata);
                        wrapper.user().get(ChunkTracker.class).remapBlockParticle(block);
                        parts[1] = String.valueOf(block.getId());
                        parts[2] = String.valueOf(block.getData());
                    }
                    wrapper.set(Types.STRING, 0, String.join((CharSequence)"_", parts));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.GAME_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.map(Types.BYTE, Types.FLOAT);
                this.handler(wrapper -> {
                    short gameState = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (gameState == 1) {
                        PacketWrapper startRain = PacketWrapper.create(ClientboundPackets1_7_2.GAME_EVENT, wrapper.user());
                        startRain.write(Types.UNSIGNED_BYTE, (short)7);
                        startRain.write(Types.FLOAT, Float.valueOf(1.0f));
                        wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
                        startRain.send(Protocolr1_6_4Tor1_7_2_5.class);
                        wrapper.cancel();
                    } else if (gameState == 2) {
                        PacketWrapper stopRain = PacketWrapper.create(ClientboundPackets1_7_2.GAME_EVENT, wrapper.user());
                        stopRain.write(Types.UNSIGNED_BYTE, (short)7);
                        stopRain.write(Types.FLOAT, Float.valueOf(0.0f));
                        wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
                        stopRain.send(Protocolr1_6_4Tor1_7_2_5.class);
                        wrapper.cancel();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.ADD_GLOBAL_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.OPEN_SCREEN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.CONTAINER_CLOSE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.CONTAINER_SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_7_6.ITEM);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), wrapper.get(Types1_7_6.ITEM, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.CONTAINER_SET_CONTENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    Item[] items;
                    for (Item item : items = wrapper.passthrough(Types1_7_6.ITEM_ARRAY)) {
                        Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), item);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types1_6_4.STRING, Types.STRING);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.SHORT);
                this.map(Types.SHORT, Types.VAR_INT);
                this.map(Types.SHORT_BYTE_ARRAY);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types.BYTE, Types.UNSIGNED_BYTE);
                this.map(Types1_7_6.NBT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.OPEN_SIGN_EDITOR, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.BYTE);
                this.map(Types1_7_6.BLOCK_POSITION_INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.AWARD_STATS, wrapper -> {
            wrapper.cancel();
            StatisticsStorage statisticsStorage = wrapper.user().get(StatisticsStorage.class);
            int statId = wrapper.read(Types.INT);
            int increment = wrapper.read(Types.INT);
            statisticsStorage.values.put(statId, statisticsStorage.values.get(statId) + increment);
        });
        this.registerClientbound(ClientboundPackets1_6_4.PLAYER_INFO, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types.BOOLEAN);
                this.map(Types.SHORT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.COMMAND_SUGGESTIONS, wrapper -> {
            String completions = wrapper.read(Types1_6_4.STRING);
            String[] completionsArray = completions.split("\u0000");
            wrapper.write(Types.VAR_INT, completionsArray.length);
            for (String s : completionsArray) {
                wrapper.write(Types.STRING, s);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_OBJECTIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_SCORE, wrapper -> {
            wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
            byte mode2 = wrapper.passthrough(Types.BYTE);
            if (mode2 == 0) {
                wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
                wrapper.passthrough(Types.INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_DISPLAY_OBJECTIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types1_6_4.STRING, Types.STRING);
            }
        });
        this.registerClientbound(ClientboundPackets1_6_4.SET_PLAYER_TEAM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING);
                this.handler(wrapper -> {
                    byte mode2 = wrapper.passthrough(Types.BYTE);
                    if (mode2 == 0 || mode2 == 2) {
                        wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
                        wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
                        wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
                        wrapper.passthrough(Types.BYTE);
                    }
                    if (mode2 == 0 || mode2 == 3 || mode2 == 4) {
                        int count = wrapper.passthrough(Types.SHORT).shortValue();
                        for (int i = 0; i < count; ++i) {
                            wrapper.write(Types.STRING, wrapper.read(Types1_6_4.STRING));
                        }
                    }
                });
            }
        });
        this.registerClientboundTransition(ClientboundPackets1_6_4.CUSTOM_PAYLOAD, new Object[]{ClientboundPackets1_7_2.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String channel = wrapper.read(Types1_6_4.STRING);
                    int length = wrapper.read(Types.SHORT).shortValue();
                    if (length < 0) {
                        wrapper.write(Types.STRING, channel);
                        wrapper.write(Types.UNSIGNED_SHORT, 0);
                        return;
                    }
                    try {
                        if (channel.equals("MC|TrList")) {
                            wrapper.passthrough(Types.INT);
                            int count = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
                            for (int i = 0; i < count; ++i) {
                                Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), wrapper.passthrough(Types1_7_6.ITEM));
                                Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), wrapper.passthrough(Types1_7_6.ITEM));
                                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                                    Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), wrapper.passthrough(Types1_7_6.ITEM));
                                }
                                wrapper.passthrough(Types.BOOLEAN);
                            }
                            length = PacketUtil.calculateLength(wrapper);
                        }
                    }
                    catch (Exception e) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to handle packet", e);
                        }
                        wrapper.cancel();
                        return;
                    }
                    wrapper.resetReader();
                    wrapper.write(Types.STRING, channel);
                    wrapper.write(Types.UNSIGNED_SHORT, length);
                });
            }
        }, State.LOGIN, PacketWrapper::cancel});
        this.registerClientboundTransition(ClientboundPackets1_6_4.SHARED_KEY, ClientboundLoginPackets.GAME_PROFILE, wrapper -> {
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            ProtocolMetadataStorage protocolMetadata = wrapper.user().get(ProtocolMetadataStorage.class);
            wrapper.read(Types.SHORT_BYTE_ARRAY);
            wrapper.read(Types.SHORT_BYTE_ARRAY);
            wrapper.write(Types.STRING, info.getUuid().toString().replace("-", ""));
            wrapper.write(Types.STRING, info.getUsername());
            if (!protocolMetadata.skipEncryption) {
                Via.getManager().getProviders().get(EncryptionProvider.class).enableDecryption(wrapper.user());
            }
            ClientboundBaseProtocol1_7.onLoginSuccess(wrapper.user());
            PacketWrapper respawn = PacketWrapper.create(ServerboundPackets1_6_4.CLIENT_COMMAND, wrapper.user());
            respawn.write(Types.BYTE, (byte)0);
            respawn.sendToServer(Protocolr1_6_4Tor1_7_2_5.class);
        });
        this.registerClientboundTransition(ClientboundPackets1_6_4.SERVER_AUTH_DATA, ClientboundLoginPackets.HELLO, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING);
                this.map(Types.SHORT_BYTE_ARRAY);
                this.map(Types.SHORT_BYTE_ARRAY);
                this.handler(wrapper -> {
                    ProtocolMetadataStorage protocolMetadata = wrapper.user().get(ProtocolMetadataStorage.class);
                    String serverHash = wrapper.get(Types.STRING, 0);
                    protocolMetadata.authenticate = !serverHash.equals("-");
                });
            }
        });
        this.registerClientboundTransition(ClientboundPackets1_6_4.DISCONNECT, ClientboundStatusPackets.STATUS_RESPONSE, wrapper -> {
            String reason = wrapper.read(Types1_6_4.STRING);
            try {
                String[] motdParts = reason.split("\u0000");
                JsonObject rootObject = new JsonObject();
                JsonObject descriptionObject = new JsonObject();
                JsonObject playersObject = new JsonObject();
                JsonObject versionObject = new JsonObject();
                descriptionObject.addProperty("text", motdParts[3]);
                playersObject.addProperty("max", Integer.parseInt(motdParts[5]));
                playersObject.addProperty("online", Integer.parseInt(motdParts[4]));
                versionObject.addProperty("name", motdParts[2]);
                versionObject.addProperty("protocol", Integer.parseInt(motdParts[1]));
                rootObject.add("description", descriptionObject);
                rootObject.add("players", playersObject);
                rootObject.add("version", versionObject);
                wrapper.write(Types.STRING, rootObject.toString());
            }
            catch (Throwable e) {
                String string = reason;
                ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Could not parse 1.6.4 ping: " + string, e);
                wrapper.cancel();
            }
        }, ClientboundLoginPackets.LOGIN_DISCONNECT, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types1_6_4.STRING, Types.STRING, TextRewriter::toClientDisconnect);
            }
        }, ClientboundPackets1_7_2.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types.STRING, TextRewriter::toClientDisconnect);
            }
        });
        this.cancelClientbound(ClientboundPackets1_6_4.SET_CREATIVE_MODE_SLOT);
        this.registerServerboundTransition(ServerboundHandshakePackets.CLIENT_INTENTION, null, wrapper -> {
            wrapper.cancel();
            wrapper.read(Types.VAR_INT);
            String hostname = wrapper.read(Types.STRING);
            int port = wrapper.read(Types.UNSIGNED_SHORT);
            wrapper.user().put(new HandshakeStorage(hostname, port));
        });
        this.registerServerboundTransition(ServerboundStatusPackets.STATUS_REQUEST, ServerboundPackets1_6_4.SERVER_PING, wrapper -> {
            HandshakeStorage handshakeStorage = wrapper.user().get(HandshakeStorage.class);
            String ip = handshakeStorage.getHostname();
            int port = handshakeStorage.getPort();
            wrapper.write(Types.UNSIGNED_BYTE, (short)1);
            wrapper.write(Types.UNSIGNED_BYTE, (short)ServerboundPackets1_6_4.CUSTOM_PAYLOAD.getId());
            wrapper.write(Types1_6_4.STRING, "MC|PingHost");
            wrapper.write(Types.SHORT, (short)(3 + 2 * ip.length() + 4));
            wrapper.write(Types.UNSIGNED_BYTE, (short)wrapper.user().getProtocolInfo().serverProtocolVersion().getVersion());
            wrapper.write(Types1_6_4.STRING, ip);
            wrapper.write(Types.INT, port);
        });
        this.registerServerboundTransition(ServerboundStatusPackets.PING_REQUEST, null, wrapper -> {
            wrapper.cancel();
            PacketWrapper pong = PacketWrapper.create(ClientboundStatusPackets.PONG_RESPONSE, wrapper.user());
            pong.write(Types.LONG, wrapper.read(Types.LONG));
            pong.send(Protocolr1_6_4Tor1_7_2_5.class);
        });
        this.registerServerboundTransition(ServerboundLoginPackets.HELLO, ServerboundPackets1_6_4.CLIENT_PROTOCOL, wrapper -> {
            HandshakeStorage handshakeStorage = wrapper.user().get(HandshakeStorage.class);
            String name = wrapper.read(Types.STRING);
            wrapper.write(Types.UNSIGNED_BYTE, (short)wrapper.user().getProtocolInfo().serverProtocolVersion().getVersion());
            wrapper.write(Types1_6_4.STRING, name);
            wrapper.write(Types1_6_4.STRING, handshakeStorage.getHostname());
            wrapper.write(Types.INT, handshakeStorage.getPort());
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            if (info.getUsername() == null) {
                info.setUsername(name);
            }
            if (info.getUuid() == null) {
                info.setUuid(ViaLegacy.getConfig().isLegacySkinLoading() ? Via.getManager().getProviders().get(GameProfileFetcher.class).getMojangUUID(name) : new GameProfile((String)name).uuid);
            }
        });
        this.registerServerboundTransition(ServerboundLoginPackets.ENCRYPTION_KEY, ServerboundPackets1_6_4.SHARED_KEY, null);
        this.registerServerbound(ServerboundPackets1_7_2.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Types1_6_4.STRING);
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.INTERACT, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> wrapper.write(Types.INT, wrapper.user().get(PlayerInfoStorage.class).entityId));
                this.map(Types.INT);
                this.map(Types.BYTE);
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    wrapper.user().get(PlayerInfoStorage.class).onGround = wrapper.get(Types.BOOLEAN, 0);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_POS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    PlayerInfoStorage playerInfoStorage = wrapper.user().get(PlayerInfoStorage.class);
                    playerInfoStorage.posX = wrapper.get(Types.DOUBLE, 0);
                    playerInfoStorage.posY = wrapper.get(Types.DOUBLE, 1);
                    playerInfoStorage.posZ = wrapper.get(Types.DOUBLE, 3);
                    playerInfoStorage.onGround = wrapper.get(Types.BOOLEAN, 0);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    PlayerInfoStorage playerInfoStorage = wrapper.user().get(PlayerInfoStorage.class);
                    playerInfoStorage.yaw = wrapper.get(Types.FLOAT, 0).floatValue();
                    playerInfoStorage.pitch = wrapper.get(Types.FLOAT, 1).floatValue();
                    playerInfoStorage.onGround = wrapper.get(Types.BOOLEAN, 0);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_POS_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    PlayerInfoStorage playerInfoStorage = wrapper.user().get(PlayerInfoStorage.class);
                    playerInfoStorage.posX = wrapper.get(Types.DOUBLE, 0);
                    playerInfoStorage.posY = wrapper.get(Types.DOUBLE, 1);
                    playerInfoStorage.posZ = wrapper.get(Types.DOUBLE, 3);
                    playerInfoStorage.yaw = wrapper.get(Types.FLOAT, 0).floatValue();
                    playerInfoStorage.pitch = wrapper.get(Types.FLOAT, 1).floatValue();
                    playerInfoStorage.onGround = wrapper.get(Types.BOOLEAN, 0);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_7_6.ITEM);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToServer(wrapper.user(), wrapper.get(Types1_7_6.ITEM, 0)));
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types1_7_6.ITEM);
                this.handler(wrapper -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToServer(wrapper.user(), wrapper.get(Types1_7_6.ITEM, 0)));
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.SIGN_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types.STRING, Types1_6_4.STRING);
                this.map(Types.STRING, Types1_6_4.STRING);
                this.map(Types.STRING, Types1_6_4.STRING);
                this.map(Types.STRING, Types1_6_4.STRING);
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.COMMAND_SUGGESTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Types1_6_4.STRING);
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.CLIENT_INFORMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Types1_6_4.STRING);
                this.handler(wrapper -> {
                    byte renderDistance = wrapper.read(Types.BYTE);
                    renderDistance = renderDistance <= 2 ? (byte)3 : (renderDistance <= 4 ? (byte)2 : (renderDistance <= 8 ? (byte)1 : (byte)0));
                    wrapper.write(Types.BYTE, renderDistance);
                    byte chatVisibility = wrapper.read(Types.BYTE);
                    boolean enableColors = wrapper.read(Types.BOOLEAN);
                    byte mask = (byte)(chatVisibility | (enableColors ? 1 : 0) << 3);
                    wrapper.write(Types.BYTE, mask);
                });
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
            }
        });
        this.registerServerbound(ServerboundPackets1_7_2.CLIENT_COMMAND, wrapper -> {
            int action = wrapper.read(Types.VAR_INT);
            if (action == 1) {
                Object2IntOpenHashMap<String> loadedStatistics = new Object2IntOpenHashMap<String>();
                for (Int2IntMap.Entry entry : wrapper.user().get(StatisticsStorage.class).values.int2IntEntrySet()) {
                    String string = StatisticRewriter.map(entry.getIntKey());
                    if (string == null) continue;
                    loadedStatistics.put(string, entry.getIntValue());
                }
                PacketWrapper statistics = PacketWrapper.create(ClientboundPackets1_8.AWARD_STATS, wrapper.user());
                statistics.write(Types.VAR_INT, loadedStatistics.size());
                for (Object2IntMap.Entry entry : loadedStatistics.object2IntEntrySet()) {
                    statistics.write(Types.STRING, (String)entry.getKey());
                    statistics.write(Types.VAR_INT, entry.getIntValue());
                }
                statistics.send(Protocolr1_6_4Tor1_7_2_5.class);
            }
            if (action != 0) {
                wrapper.cancel();
                return;
            }
            wrapper.write(Types.BYTE, (byte)1);
        });
        this.registerServerbound(ServerboundPackets1_7_2.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String channel = wrapper.read(Types.STRING);
                    short length = wrapper.read(Types.SHORT);
                    switch (channel) {
                        case "MC|BEdit": 
                        case "MC|BSign": {
                            Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToServer(wrapper.user(), wrapper.passthrough(Types1_7_6.ITEM));
                            length = (short)PacketUtil.calculateLength(wrapper);
                            break;
                        }
                        case "MC|AdvCdm": {
                            byte type = wrapper.read(Types.BYTE);
                            if (type != 0) {
                                wrapper.cancel();
                                return;
                            }
                            wrapper.passthrough(Types.INT);
                            wrapper.passthrough(Types.INT);
                            wrapper.passthrough(Types.INT);
                            wrapper.passthrough(Types.STRING);
                            length = (short)PacketUtil.calculateLength(wrapper);
                        }
                    }
                    wrapper.resetReader();
                    wrapper.write(Types1_6_4.STRING, channel);
                    wrapper.write(Types.SHORT, length);
                });
            }
        });
    }

    void rewriteEntityData(UserConnection user, List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            if (entityData.dataType().equals(EntityDataTypes1_6_4.ITEM)) {
                this.itemRewriter.handleItemToClient(user, (Item)entityData.value());
            }
            entityData.setDataType(EntityDataTypes1_7_6.byId(entityData.dataType().typeId()));
        }
    }

    @Override
    public void register(ViaProviders providers) {
        providers.require(EncryptionProvider.class);
    }

    @Override
    public void init(final UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_6_4Tor1_7_2_5.class, ClientboundPackets1_6_4::getPacket));
        userConnection.addClientWorld(Protocolr1_6_4Tor1_7_2_5.class, new ClientWorld());
        userConnection.put(new ProtocolMetadataStorage());
        userConnection.put(new PlayerInfoStorage());
        userConnection.put(new StatisticsStorage());
        userConnection.put(new ChunkTracker(userConnection));
        if (userConnection.getChannel() != null) {
            userConnection.getChannel().pipeline().addFirst(new ChannelHandler[]{new ChannelOutboundHandlerAdapter(){

                public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                    if (ctx.channel().isWritable() && userConnection.getProtocolInfo().getClientState().equals((Object)State.PLAY) && userConnection.get(PlayerInfoStorage.class).entityId != -1) {
                        PacketWrapper disconnect = PacketWrapper.create(ServerboundPackets1_6_4.DISCONNECT, userConnection);
                        disconnect.write(Types1_6_4.STRING, "Quitting");
                        disconnect.sendToServer(Protocolr1_6_4Tor1_7_2_5.class);
                    }
                    super.close(ctx, promise);
                }
            }});
        }
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

