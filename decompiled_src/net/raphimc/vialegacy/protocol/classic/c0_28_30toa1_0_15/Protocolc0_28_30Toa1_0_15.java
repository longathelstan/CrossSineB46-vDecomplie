/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.model.ChunkCoord;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.BlockFaceUtil;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ServerboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.storage.TimeLockStorage;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1;
import net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet.ClientboundPacketsa1_1_0;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider.AlphaInventoryProvider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.AlphaInventoryTracker;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.storage.BlockDigStorage;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.Protocolb1_5_0_2Tob1_6_0_6;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.Protocolb1_7_0_3Tob1_8_0_1;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ClientboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ClientboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.data.ClassicBlocks;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.model.ClassicLevel;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ClientboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ServerboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicCustomCommandProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicMPPassProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicWorldHeightProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicLevelStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicOpLevelStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicPositionTracker;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicProgressStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicServerTitleStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.task.ClassicLevelStorageTickTask;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.Typesc0_30;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.Protocolc0_30cpeToc0_28_30;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage.ExtBlockPermissionsStorage;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolc0_28_30Toa1_0_15
extends StatelessProtocol<ClientboundPacketsc0_28, ClientboundPacketsa1_0_15, ServerboundPacketsc0_28, ServerboundPacketsa1_0_15> {
    public Protocolc0_28_30Toa1_0_15() {
        super(ClientboundPacketsc0_28.class, ClientboundPacketsa1_0_15.class, ServerboundPacketsc0_28.class, ServerboundPacketsa1_0_15.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsc0_28.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.BYTE);
                this.handler(wrapper -> {
                    String title2 = wrapper.read(Typesc0_30.STRING).replace("&", "\u00a7");
                    String motd = wrapper.read(Typesc0_30.STRING).replace("&", "\u00a7");
                    byte opLevel = wrapper.read(Types.BYTE);
                    wrapper.user().put(new ClassicServerTitleStorage(wrapper.user(), title2, motd));
                    wrapper.user().get(ClassicOpLevelStorage.class).setOpLevel(opLevel);
                    wrapper.write(Types.INT, wrapper.user().getProtocolInfo().getUsername().hashCode());
                    wrapper.write(Typesb1_7_0_3.STRING, wrapper.user().getProtocolInfo().getUsername());
                    wrapper.write(Typesb1_7_0_3.STRING, "");
                    if (wrapper.user().has(ClassicLevelStorage.class)) {
                        wrapper.cancel();
                    }
                    if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolr1_7_6_10Tor1_8.class)) {
                        PacketWrapper tabList = PacketWrapper.create(ClientboundPackets1_8.TAB_LIST, wrapper.user());
                        String string = title2;
                        tabList.write(Types.STRING, Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON.transform(wrapper, "\u00a76" + string + "\n"));
                        String string2 = motd;
                        tabList.write(Types.STRING, Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON.transform(wrapper, "\n\u00a7b" + string2));
                        tabList.send(Protocolr1_7_6_10Tor1_8.class);
                    }
                    ClassicProgressStorage classicProgressStorage = wrapper.user().get(ClassicProgressStorage.class);
                    classicProgressStorage.progress = 1;
                    classicProgressStorage.upperBound = 2;
                    classicProgressStorage.status = "Waiting for server...";
                });
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.LEVEL_INIT, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            if (wrapper.user().has(ClassicLevelStorage.class) && wrapper.user().getProtocolInfo().getPipeline().contains(Protocolb1_5_0_2Tob1_6_0_6.class)) {
                PacketWrapper fakeRespawn = PacketWrapper.create(ClientboundPacketsb1_7.RESPAWN, wrapper.user());
                fakeRespawn.write(Types.BYTE, (byte)-1);
                fakeRespawn.send(Protocolb1_5_0_2Tob1_6_0_6.class);
                PacketWrapper respawn = PacketWrapper.create(ClientboundPacketsb1_7.RESPAWN, wrapper.user());
                respawn.write(Types.BYTE, (byte)0);
                respawn.send(Protocolb1_5_0_2Tob1_6_0_6.class);
                wrapper.user().get(ClassicPositionTracker.class).spawned = false;
            }
            if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolb1_7_0_3Tob1_8_0_1.class)) {
                PacketWrapper gameEvent = PacketWrapper.create(ClientboundPacketsb1_8.GAME_EVENT, wrapper.user());
                gameEvent.write(Types.BYTE, (byte)3);
                gameEvent.write(Types.BYTE, (byte)1);
                gameEvent.send(Protocolb1_7_0_3Tob1_8_0_1.class);
            }
            wrapper.user().get(ClassicOpLevelStorage.class).updateAbilities();
            wrapper.user().put(new ClassicLevelStorage(wrapper.user()));
            ClassicProgressStorage classicProgressStorage = wrapper.user().get(ClassicProgressStorage.class);
            classicProgressStorage.progress = 2;
            classicProgressStorage.upperBound = 2;
            classicProgressStorage.status = "Waiting for server...";
        });
        this.registerClientbound(ClientboundPacketsc0_28.LEVEL_DATA, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            short partSize = wrapper.read(Types.SHORT);
            byte[] data = wrapper.read(Typesc0_30.BYTE_ARRAY);
            byte progress = wrapper.read(Types.BYTE);
            wrapper.user().get(ClassicLevelStorage.class).addDataPart(data, partSize);
            ClassicProgressStorage classicProgressStorage = wrapper.user().get(ClassicProgressStorage.class);
            classicProgressStorage.upperBound = 100;
            classicProgressStorage.progress = progress;
            byte by = progress;
            classicProgressStorage.status = "Receiving level... \u00a77" + by + "%";
        });
        this.registerClientbound(ClientboundPacketsc0_28.LEVEL_FINALIZE, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            short sizeX = wrapper.read(Types.SHORT);
            short sizeY = wrapper.read(Types.SHORT);
            short sizeZ = wrapper.read(Types.SHORT);
            ClassicProgressStorage classicProgressStorage = wrapper.user().get(ClassicProgressStorage.class);
            ClassicLevelStorage levelStorage = wrapper.user().get(ClassicLevelStorage.class);
            short maxChunkSectionCount = Via.getManager().getProviders().get(ClassicWorldHeightProvider.class).getMaxChunkSectionCount(wrapper.user());
            classicProgressStorage.upperBound = 2;
            classicProgressStorage.progress = 0;
            classicProgressStorage.status = "Finishing level... \u00a77Decompressing";
            levelStorage.finish(sizeX, sizeY, sizeZ);
            levelStorage.sendChunk(new ChunkCoord(0, 0));
            if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolr1_7_6_10Tor1_8.class)) {
                PacketWrapper worldBorder = PacketWrapper.create(ClientboundPackets1_8.SET_BORDER, wrapper.user());
                worldBorder.write(Types.VAR_INT, 3);
                worldBorder.write(Types.DOUBLE, (double)sizeX / 2.0);
                worldBorder.write(Types.DOUBLE, (double)sizeZ / 2.0);
                worldBorder.write(Types.DOUBLE, 0.0);
                worldBorder.write(Types.DOUBLE, Double.valueOf(Math.max(sizeX, sizeZ)));
                worldBorder.write(Types.VAR_LONG, 0L);
                worldBorder.write(Types.VAR_INT, Math.max(sizeX, sizeZ));
                worldBorder.write(Types.VAR_INT, 0);
                worldBorder.write(Types.VAR_INT, 0);
                worldBorder.send(Protocolr1_7_6_10Tor1_8.class);
            }
            short s = sizeZ;
            short s2 = sizeY;
            short s3 = sizeX;
            this.sendChatMessage(wrapper.user(), "\u00a7aWorld dimensions: \u00a76" + s3 + "\u00a7ax\u00a76" + s2 + "\u00a7ax\u00a76" + s);
            if (sizeY > maxChunkSectionCount << 4) {
                int n = maxChunkSectionCount << 4;
                this.sendChatMessage(wrapper.user(), "\u00a7cThis server has a world higher than " + n + " blocks! Expect world errors");
            }
            classicProgressStorage.progress = 1;
            classicProgressStorage.status = "Finishing level... \u00a77Waiting for server";
        });
        this.registerClientbound(ClientboundPacketsc0_28.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Typesc0_30.BLOCK_POSITION, Types1_7_6.BLOCK_POSITION_UBYTE);
                this.handler(wrapper -> {
                    ClassicLevelStorage levelStorage = wrapper.user().get(ClassicLevelStorage.class);
                    if (levelStorage == null || !levelStorage.hasReceivedLevel()) {
                        wrapper.cancel();
                        return;
                    }
                    ClassicBlockRemapper remapper = wrapper.user().get(ClassicBlockRemapper.class);
                    BlockPosition pos = wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
                    byte blockId = wrapper.read(Types.BYTE);
                    levelStorage.getClassicLevel().setBlock(pos, blockId);
                    if (!levelStorage.isChunkLoaded(pos)) {
                        wrapper.cancel();
                        return;
                    }
                    IdAndData mappedBlock = remapper.mapper().get(blockId);
                    wrapper.write(Types.UNSIGNED_BYTE, (short)mappedBlock.getId());
                    wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf(mappedBlock.getData()));
                });
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
                this.map(Typesc0_30.STRING, Typesb1_7_0_3.STRING, n -> n.replace("&", "\u00a7"));
                this.map(Types.SHORT, Types.INT);
                this.map(Types.SHORT, Types.INT);
                this.map(Types.SHORT, Types.INT);
                this.map(Types.BYTE, Types.BYTE, yaw -> (byte)(yaw + 128));
                this.map(Types.BYTE);
                this.create(Types.UNSIGNED_SHORT, 0);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.INT, 0) < 0) {
                        wrapper.cancel();
                        int x = wrapper.get(Types.INT, 1);
                        int y = wrapper.get(Types.INT, 2);
                        int z = wrapper.get(Types.INT, 3);
                        byte yaw = wrapper.get(Types.BYTE, 0);
                        byte pitch = wrapper.get(Types.BYTE, 1);
                        ClassicProgressStorage classicProgressStorage = wrapper.user().get(ClassicProgressStorage.class);
                        classicProgressStorage.progress = 2;
                        classicProgressStorage.status = "Finishing level... \u00a77Loading spawn chunks";
                        ClassicPositionTracker classicPositionTracker = wrapper.user().get(ClassicPositionTracker.class);
                        classicPositionTracker.posX = (float)x / 32.0f;
                        classicPositionTracker.stance = (float)y / 32.0f + 0.714f;
                        classicPositionTracker.posZ = (float)z / 32.0f;
                        classicPositionTracker.yaw = (float)(yaw * 360) / 256.0f;
                        classicPositionTracker.pitch = (float)(pitch * 360) / 256.0f;
                        wrapper.user().get(ClassicLevelStorage.class).sendChunks(classicPositionTracker.getChunkPosition(), 1);
                        if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1.class)) {
                            PacketWrapper spawnPosition = PacketWrapper.create(ClientboundPacketsa1_1_0.SET_DEFAULT_SPAWN_POSITION, wrapper.user());
                            spawnPosition.write(Types1_7_6.BLOCK_POSITION_INT, new BlockPosition((int)classicPositionTracker.posX, (int)classicPositionTracker.stance, (int)classicPositionTracker.posZ));
                            spawnPosition.send(Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1.class);
                        }
                        PacketWrapper playerPosition = PacketWrapper.create(ClientboundPacketsa1_0_15.PLAYER_POSITION, wrapper.user());
                        playerPosition.write(Types.DOUBLE, classicPositionTracker.posX);
                        playerPosition.write(Types.DOUBLE, classicPositionTracker.stance);
                        playerPosition.write(Types.DOUBLE, classicPositionTracker.stance - (double)1.62f);
                        playerPosition.write(Types.DOUBLE, classicPositionTracker.posZ);
                        playerPosition.write(Types.FLOAT, Float.valueOf(classicPositionTracker.yaw));
                        playerPosition.write(Types.FLOAT, Float.valueOf(classicPositionTracker.pitch));
                        playerPosition.write(Types.BOOLEAN, true);
                        playerPosition.send(Protocolc0_28_30Toa1_0_15.class);
                        classicPositionTracker.spawned = true;
                    } else {
                        wrapper.set(Types.INT, 2, wrapper.get(Types.INT, 2) - Float.valueOf(51.84f).intValue());
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
                this.map(Types.SHORT, Types.INT);
                this.map(Types.SHORT, Types.INT);
                this.map(Types.SHORT, Types.INT);
                this.map(Types.BYTE, Types.BYTE, yaw -> (byte)(yaw + 128));
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.INT, 0) < 0) {
                        wrapper.set(Types.INT, 2, wrapper.get(Types.INT, 2) - 29);
                        wrapper.set(Types.INT, 0, wrapper.user().getProtocolInfo().getUsername().hashCode());
                    } else {
                        wrapper.set(Types.INT, 2, wrapper.get(Types.INT, 2) - Float.valueOf(51.84f).intValue());
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.MOVE_ENTITY_POS_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE, Types.BYTE, yaw -> (byte)(yaw + 128));
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.MOVE_ENTITY_POS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.MOVE_ENTITY_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
                this.map(Types.BYTE, Types.BYTE, yaw -> (byte)(yaw + 128));
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.REMOVE_ENTITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    byte senderId = packetWrapper.read(Types.BYTE);
                    String message = packetWrapper.read(Typesc0_30.STRING).replace("&", "\u00a7");
                    if (senderId < 0) {
                        String string = message;
                        message = "\u00a7e" + string;
                    }
                    packetWrapper.write(Typesb1_7_0_3.STRING, message);
                });
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Typesc0_30.STRING, Typesb1_7_0_3.STRING, s -> s.replace("&", "\u00a7"));
            }
        });
        this.registerClientbound(ClientboundPacketsc0_28.OP_LEVEL_UPDATE, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            ClassicOpLevelStorage opLevelStorage = wrapper.user().get(ClassicOpLevelStorage.class);
            byte opLevel = wrapper.read(Types.BYTE);
            opLevelStorage.setOpLevel(opLevel);
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.BYTE);
                this.map(Typesb1_7_0_3.STRING, Typesc0_30.STRING);
                this.read(Typesb1_7_0_3.STRING);
                this.handler(wrapper -> {
                    wrapper.write(Typesc0_30.STRING, Via.getManager().getProviders().get(ClassicMPPassProvider.class).getMpPass(wrapper.user()));
                    wrapper.write(Types.BYTE, (byte)0);
                    ClassicProgressStorage classicProgressStorage = wrapper.user().get(ClassicProgressStorage.class);
                    classicProgressStorage.upperBound = 2;
                    classicProgressStorage.status = "Logging in...";
                });
            }
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.CHAT, wrapper -> {
            String message = wrapper.read(Typesb1_7_0_3.STRING);
            wrapper.write(Types.BYTE, (byte)-1);
            wrapper.write(Typesc0_30.STRING, message);
            if (Via.getManager().getProviders().get(ClassicCustomCommandProvider.class).handleChatMessage(wrapper.user(), message)) {
                wrapper.cancel();
            }
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_STATUS_ONLY, ServerboundPacketsc0_28.MOVE_PLAYER_POS_ROT, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.BOOLEAN);
                this.handler(wrapper -> wrapper.user().get(ClassicPositionTracker.class).writeToPacket(wrapper));
            }
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_POS, ServerboundPacketsc0_28.MOVE_PLAYER_POS_ROT, (PacketWrapper wrapper) -> {
            ClassicPositionTracker positionTracker = wrapper.user().get(ClassicPositionTracker.class);
            positionTracker.posX = wrapper.read(Types.DOUBLE);
            wrapper.read(Types.DOUBLE);
            positionTracker.stance = wrapper.read(Types.DOUBLE);
            positionTracker.posZ = wrapper.read(Types.DOUBLE);
            wrapper.read(Types.BOOLEAN);
            positionTracker.writeToPacket(wrapper);
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_ROT, ServerboundPacketsc0_28.MOVE_PLAYER_POS_ROT, (PacketWrapper wrapper) -> {
            ClassicPositionTracker positionTracker = wrapper.user().get(ClassicPositionTracker.class);
            positionTracker.yaw = wrapper.read(Types.FLOAT).floatValue();
            positionTracker.pitch = wrapper.read(Types.FLOAT).floatValue();
            wrapper.read(Types.BOOLEAN);
            positionTracker.writeToPacket(wrapper);
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_POS_ROT, wrapper -> {
            ClassicPositionTracker positionTracker = wrapper.user().get(ClassicPositionTracker.class);
            positionTracker.posX = wrapper.read(Types.DOUBLE);
            wrapper.read(Types.DOUBLE);
            positionTracker.stance = wrapper.read(Types.DOUBLE);
            positionTracker.posZ = wrapper.read(Types.DOUBLE);
            positionTracker.yaw = wrapper.read(Types.FLOAT).floatValue();
            positionTracker.pitch = wrapper.read(Types.FLOAT).floatValue();
            wrapper.read(Types.BOOLEAN);
            positionTracker.writeToPacket(wrapper);
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.PLAYER_ACTION, ServerboundPacketsc0_28.USE_ITEM_ON, (PacketWrapper wrapper) -> {
            wrapper.user().getStoredObjects().remove(BlockDigStorage.class);
            ClassicLevel level = wrapper.user().get(ClassicLevelStorage.class).getClassicLevel();
            ClassicOpLevelStorage opTracker = wrapper.user().get(ClassicOpLevelStorage.class);
            boolean extendedVerification = wrapper.user().has(ExtBlockPermissionsStorage.class);
            short status = wrapper.read(Types.UNSIGNED_BYTE);
            BlockPosition pos = wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
            wrapper.read(Types.UNSIGNED_BYTE);
            int blockId = level.getBlock(pos);
            boolean hasCreative = wrapper.user().getProtocolInfo().getPipeline().contains(Protocolb1_7_0_3Tob1_8_0_1.class);
            if (status == 0 && hasCreative || status == 2 && !hasCreative) {
                if (!extendedVerification && blockId == 7 && opTracker.getOpLevel() < 100) {
                    wrapper.cancel();
                    this.sendChatMessage(wrapper.user(), "\u00a7cOnly op players can break bedrock!");
                    this.sendBlockChange(wrapper.user(), pos, new IdAndData(BlockList1_6.bedrock.blockId(), 0));
                    return;
                }
                if (!extendedVerification) {
                    level.setBlock(pos, 0);
                    this.sendBlockChange(wrapper.user(), pos, new IdAndData(0, 0));
                }
                wrapper.write(Typesc0_30.BLOCK_POSITION, pos);
                wrapper.write(Types.BOOLEAN, false);
                wrapper.write(Types.BYTE, (byte)1);
            } else {
                wrapper.cancel();
            }
        });
        this.registerServerbound(ServerboundPacketsa1_0_15.USE_ITEM_ON, wrapper -> {
            ClassicLevel level = wrapper.user().get(ClassicLevelStorage.class).getClassicLevel();
            ClassicBlockRemapper remapper = wrapper.user().get(ClassicBlockRemapper.class);
            boolean extendedVerification = wrapper.user().has(ExtBlockPermissionsStorage.class);
            wrapper.read(Types.SHORT);
            BlockPosition pos = wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
            short direction = wrapper.read(Types.UNSIGNED_BYTE);
            Item item = Via.getManager().getProviders().get(AlphaInventoryProvider.class).getHandItem(wrapper.user());
            if (item == null || direction == 255) {
                wrapper.cancel();
                return;
            }
            if ((pos = pos.getRelative(BlockFaceUtil.getFace(direction))).y() >= level.getSizeY()) {
                wrapper.cancel();
                int n = level.getSizeY();
                this.sendChatMessage(wrapper.user(), "\u00a7cHeight limit for building is " + n + " blocks");
                this.sendBlockChange(wrapper.user(), pos, new IdAndData(0, 0));
                return;
            }
            byte classicBlock = (byte)remapper.reverseMapper().getInt(new IdAndData(item.identifier(), item.data() & 0xF));
            if (!extendedVerification) {
                level.setBlock(pos, classicBlock);
                this.sendBlockChange(wrapper.user(), pos, remapper.mapper().get(classicBlock));
            }
            wrapper.write(Typesc0_30.BLOCK_POSITION, pos);
            wrapper.write(Types.BOOLEAN, true);
            wrapper.write(Types.BYTE, classicBlock);
        });
        this.cancelServerbound(ServerboundPacketsa1_0_15.KEEP_ALIVE);
        this.cancelServerbound(ServerboundPacketsa1_0_15.SET_CARRIED_ITEM);
        this.cancelServerbound(ServerboundPacketsa1_0_15.SWING);
        this.cancelServerbound(ServerboundPacketsa1_0_15.SPAWN_ITEM);
        this.cancelServerbound(ServerboundPacketsa1_0_15.DISCONNECT);
    }

    void sendChatMessage(UserConnection user, String msg) {
        PacketWrapper message = PacketWrapper.create(ClientboundPacketsa1_0_15.CHAT, user);
        message.write(Typesb1_7_0_3.STRING, msg);
        message.send(Protocolc0_28_30Toa1_0_15.class);
    }

    void sendBlockChange(UserConnection user, BlockPosition pos, IdAndData block) {
        PacketWrapper blockChange = PacketWrapper.create(ClientboundPacketsa1_0_15.BLOCK_UPDATE, user);
        blockChange.write(Types1_7_6.BLOCK_POSITION_UBYTE, pos);
        blockChange.write(Types.UNSIGNED_BYTE, (short)block.getId());
        blockChange.write(Types.UNSIGNED_BYTE, Short.valueOf(block.getData()));
        blockChange.send(Protocolc0_28_30Toa1_0_15.class);
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(ClassicWorldHeightProvider.class, new ClassicWorldHeightProvider());
        providers.register(ClassicMPPassProvider.class, new ClassicMPPassProvider());
        providers.register(ClassicCustomCommandProvider.class, new ClassicCustomCommandProvider());
        Via.getPlatform().runRepeatingSync(new ClassicLevelStorageTickTask(), 2L);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolc0_28_30Toa1_0_15.class, ClientboundPacketsc0_28::getPacket));
        userConnection.put(new ClassicPositionTracker());
        userConnection.put(new ClassicOpLevelStorage(userConnection, ViaLegacy.getConfig().enableClassicFly()));
        userConnection.put(new ClassicProgressStorage());
        userConnection.put(new ClassicBlockRemapper(i -> (IdAndData)ClassicBlocks.MAPPING.get(i), o -> {
            int block = ClassicBlocks.REVERSE_MAPPING.getInt(o);
            if (!userConnection.getProtocolInfo().getPipeline().contains(Protocolc0_30cpeToc0_28_30.class)) {
                if (block == 2) {
                    block = 3;
                } else if (block == 7) {
                    block = 1;
                } else if (block == 9) {
                    block = 29;
                } else if (block == 11) {
                    block = 22;
                }
            }
            return block;
        }));
        if (userConnection.has(AlphaInventoryTracker.class)) {
            userConnection.get(AlphaInventoryTracker.class).setCreativeMode(true);
        }
        if (userConnection.has(TimeLockStorage.class)) {
            userConnection.get(TimeLockStorage.class).setTime(6000L);
        }
    }
}

