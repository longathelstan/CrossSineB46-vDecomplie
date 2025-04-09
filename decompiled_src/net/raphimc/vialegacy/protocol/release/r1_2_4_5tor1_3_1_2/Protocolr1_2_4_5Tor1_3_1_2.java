/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2;

import com.google.common.collect.Lists;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.ChunkUtil;
import com.viaversion.viaversion.util.IdAndData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.model.Location;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.EntityList1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.Sound;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundType;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.AbstractTrackedEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.TrackedEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.TrackedLivingEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ClientboundPackets1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ServerboundPackets1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.provider.OldAuthProvider;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.ChestStateTracker;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.task.EntityTrackerTickTask;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ClientboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ServerboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.EntityDataTypes1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.data.EntityDataIndex1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ProtocolMetadataStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_2_4_5Tor1_3_1_2
extends StatelessProtocol<ClientboundPackets1_2_4, ClientboundPackets1_3_1, ServerboundPackets1_2_4, ServerboundPackets1_3_1> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_2_4_5Tor1_3_1_2() {
        super(ClientboundPackets1_2_4.class, ClientboundPackets1_3_1.class, ServerboundPackets1_2_4.class, ServerboundPackets1_3_1.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_2_4.HANDSHAKE, ClientboundPackets1_3_1.SHARED_KEY, (PacketWrapper wrapper) -> {
            String serverHash = wrapper.read(Types1_6_4.STRING);
            if (!serverHash.trim().isEmpty() && !serverHash.equalsIgnoreCase("-")) {
                try {
                    Via.getManager().getProviders().get(OldAuthProvider.class).sendAuthRequest(wrapper.user(), serverHash);
                }
                catch (Throwable e) {
                    ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Could not authenticate with mojang for joinserver request!", e);
                    wrapper.cancel();
                    PacketWrapper kick = PacketWrapper.create(ClientboundPackets1_3_1.DISCONNECT, wrapper.user());
                    kick.write(Types1_6_4.STRING, "Failed to log in: Invalid session (Try restarting your game and the launcher)");
                    kick.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                    return;
                }
            }
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            PacketWrapper login = PacketWrapper.create(ServerboundPackets1_2_4.LOGIN, wrapper.user());
            login.write(Types.INT, info.serverProtocolVersion().getVersion());
            login.write(Types1_6_4.STRING, info.getUsername());
            login.write(Types1_6_4.STRING, "");
            login.write(Types.INT, 0);
            login.write(Types.INT, 0);
            login.write(Types.BYTE, (byte)0);
            login.write(Types.BYTE, (byte)0);
            login.write(Types.BYTE, (byte)0);
            login.sendToServer(Protocolr1_2_4_5Tor1_3_1_2.class);
            State currentState = wrapper.user().getProtocolInfo().getServerState();
            if (currentState != State.LOGIN) {
                wrapper.cancel();
            } else {
                wrapper.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
                wrapper.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
                wrapper.user().get(ProtocolMetadataStorage.class).skipEncryption = true;
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.read(Types1_6_4.STRING);
                this.map(Types1_6_4.STRING);
                this.map(Types.INT, Types.BYTE);
                this.map(Types.INT, Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    ((ClientWorld)wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class)).setEnvironment(wrapper.get(Types.BYTE, 1).byteValue());
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    entityTracker.setPlayerID(wrapper.get(Types.INT, 0));
                    entityTracker.getTrackedEntities().put(entityTracker.getPlayerID(), new TrackedLivingEntity(entityTracker.getPlayerID(), new Location(8.0, 64.0, 8.0), EntityTypes1_8.EntityType.PLAYER));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    short itemId = wrapper.read(Types.SHORT);
                    short itemDamage = wrapper.read(Types.SHORT);
                    wrapper.write(Types1_7_6.ITEM, itemId < 0 ? null : new DataItem(itemId, 1, itemDamage, null));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_6_4.STRING);
                this.handler(wrapper -> {
                    if (((ClientWorld)wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class)).setEnvironment(wrapper.get(Types.INT, 0))) {
                        wrapper.user().get(ChestStateTracker.class).clear();
                        EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                        entityTracker.getTrackedEntities().clear();
                        entityTracker.getTrackedEntities().put(entityTracker.getPlayerID(), new TrackedLivingEntity(entityTracker.getPlayerID(), new Location(8.0, 64.0, 8.0), EntityTypes1_8.EntityType.PLAYER));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.UNSIGNED_SHORT);
                this.handler(wrapper -> wrapper.write(Types1_3_1.ENTITY_DATA_LIST, Lists.newArrayList((Object[])new EntityData[]{new EntityData(0, EntityDataTypes1_3_1.BYTE, (byte)0)})));
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    double x = (double)wrapper.get(Types.INT, 1).intValue() / 32.0;
                    double y = (double)wrapper.get(Types.INT, 2).intValue() / 32.0;
                    double z = (double)wrapper.get(Types.INT, 3).intValue() / 32.0;
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    tracker.getTrackedEntities().put(entityId, new TrackedLivingEntity(entityId, new Location(x, y, z), EntityTypes1_8.EntityType.PLAYER));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.SPAWN_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_3_1.NBTLESS_ITEM);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    int entityId = wrapper.get(Types.INT, 0);
                    double x = (double)wrapper.get(Types.INT, 1).intValue() / 32.0;
                    double y = (double)wrapper.get(Types.INT, 2).intValue() / 32.0;
                    double z = (double)wrapper.get(Types.INT, 3).intValue() / 32.0;
                    tracker.getTrackedEntities().put(entityId, new TrackedEntity(entityId, new Location(x, y, z), EntityTypes1_8.ObjectType.ITEM.getType()));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.TAKE_ITEM_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.handler(wrapper -> wrapper.user().get(EntityTracker.class).getTrackedEntities().remove(wrapper.get(Types.INT, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    EntityTypes1_8.EntityType type;
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    int entityId = wrapper.get(Types.INT, 0);
                    byte typeId = wrapper.get(Types.BYTE, 0);
                    if (typeId == 70 || typeId == 71 || typeId == 74) {
                        type = EntityTypes1_8.ObjectType.FALLING_BLOCK.getType();
                        wrapper.set(Types.BYTE, 0, (byte)EntityTypes1_8.ObjectType.FALLING_BLOCK.getId());
                    } else {
                        type = typeId == 10 || typeId == 11 || typeId == 12 ? EntityTypes1_8.ObjectType.MINECART.getType() : EntityTypes1_8.getTypeFromId(typeId, true);
                    }
                    double x = (double)wrapper.get(Types.INT, 1).intValue() / 32.0;
                    double y = (double)wrapper.get(Types.INT, 2).intValue() / 32.0;
                    double z = (double)wrapper.get(Types.INT, 3).intValue() / 32.0;
                    Location location = new Location(x, y, z);
                    int throwerEntityId = wrapper.get(Types.INT, 4);
                    short speedX = 0;
                    short speedY = 0;
                    short speedZ = 0;
                    if (throwerEntityId > 0) {
                        speedX = wrapper.read(Types.SHORT);
                        speedY = wrapper.read(Types.SHORT);
                        speedZ = wrapper.read(Types.SHORT);
                    }
                    if (typeId == 70) {
                        throwerEntityId = 12;
                    }
                    if (typeId == 71) {
                        throwerEntityId = 13;
                    }
                    if (typeId == 74) {
                        throwerEntityId = 122;
                    }
                    if (typeId == EntityTypes1_8.ObjectType.FISHIHNG_HOOK.getId()) {
                        Optional<AbstractTrackedEntity> nearestEntity = entityTracker.getNearestEntity(location, 2.0, e -> e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.PLAYER));
                        throwerEntityId = nearestEntity.map(AbstractTrackedEntity::getEntityId).orElseGet(entityTracker::getPlayerID);
                    }
                    wrapper.set(Types.INT, 4, throwerEntityId);
                    if (throwerEntityId > 0) {
                        wrapper.write(Types.SHORT, speedX);
                        wrapper.write(Types.SHORT, speedY);
                        wrapper.write(Types.SHORT, speedZ);
                    }
                    entityTracker.getTrackedEntities().put(entityId, new TrackedEntity(entityId, location, type));
                    EntityTypes1_8.ObjectType objectType = EntityTypes1_8.ObjectType.findById(typeId);
                    if (objectType == null) {
                        return;
                    }
                    switch (objectType) {
                        case TNT_PRIMED: {
                            entityTracker.playSoundAt(location, Sound.RANDOM_FUSE, 1.0f, 1.0f);
                            break;
                        }
                        case TIPPED_ARROW: {
                            float pitch = 1.0f / (entityTracker.RND.nextFloat() * 0.4f + 1.2f) + 0.5f;
                            entityTracker.playSoundAt(location, Sound.RANDOM_BOW, 1.0f, pitch);
                            break;
                        }
                        case SNOWBALL: 
                        case EGG: 
                        case ENDER_PEARL: 
                        case EYE_OF_ENDER: 
                        case POTION: 
                        case EXPERIENCE_BOTTLE: 
                        case FISHIHNG_HOOK: {
                            float pitch = 0.4f / (entityTracker.RND.nextFloat() * 0.4f + 0.8f);
                            entityTracker.playSoundAt(location, Sound.RANDOM_BOW, 0.5f, pitch);
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.create(Types.SHORT, (short)0);
                this.create(Types.SHORT, (short)0);
                this.create(Types.SHORT, (short)0);
                this.map(Types1_3_1.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    short type = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    double x = (double)wrapper.get(Types.INT, 1).intValue() / 32.0;
                    double y = (double)wrapper.get(Types.INT, 2).intValue() / 32.0;
                    double z = (double)wrapper.get(Types.INT, 3).intValue() / 32.0;
                    List<EntityData> entityDataList = wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0);
                    EntityTypes1_8.EntityType entityType = EntityTypes1_8.getTypeFromId(type, false);
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    tracker.getTrackedEntities().put(entityId, new TrackedLivingEntity(entityId, new Location(x, y, z), entityType));
                    tracker.updateEntityDataList(entityId, entityDataList);
                    Protocolr1_2_4_5Tor1_3_1_2.this.handleEntityDataList(entityId, entityDataList, wrapper);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.REMOVE_ENTITIES, new PacketHandlers(){

            /*
             * Exception decompiling
             */
            @Override
            public void register() {
                /*
                 * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                 * 
                 * java.lang.UnsupportedOperationException
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:142)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:455)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:409)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:167)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:105)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
                 *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredExpressionStatement.rewriteExpressions(StructuredExpressionStatement.java:70)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:88)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1137)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:912)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                 *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                 *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
                 *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                 *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                 *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                 *     at org.benf.cfr.reader.Main.main(Main.java:54)
                 */
                throw new IllegalStateException("Decompilation failed");
            }

            static /* synthetic */ void lambda$register$1(PacketWrapper wrapper) throws InformativeException {
                EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                for (int entityId : wrapper.get(Types1_7_6.INT_ARRAY, 0)) {
                    tracker.getTrackedEntities().remove(entityId);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.MOVE_ENTITY_POS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    int entityId = wrapper.get(Types.INT, 0);
                    byte x = wrapper.get(Types.BYTE, 0);
                    byte y = wrapper.get(Types.BYTE, 1);
                    byte z = wrapper.get(Types.BYTE, 2);
                    tracker.updateEntityLocation(entityId, x, y, z, true);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.MOVE_ENTITY_POS_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    int entityId = wrapper.get(Types.INT, 0);
                    byte x = wrapper.get(Types.BYTE, 0);
                    byte y = wrapper.get(Types.BYTE, 1);
                    byte z = wrapper.get(Types.BYTE, 2);
                    tracker.updateEntityLocation(entityId, x, y, z, true);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    int entityId = wrapper.get(Types.INT, 0);
                    int x = wrapper.get(Types.INT, 1);
                    int y = wrapper.get(Types.INT, 2);
                    int z = wrapper.get(Types.INT, 3);
                    tracker.updateEntityLocation(entityId, x, y, z, false);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.ENTITY_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    int entityId = wrapper.get(Types.INT, 0);
                    byte status = wrapper.get(Types.BYTE, 0);
                    if (status == 2) {
                        entityTracker.playSound(entityId, SoundType.HURT);
                    } else if (status == 3) {
                        entityTracker.playSound(entityId, SoundType.DEATH);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_3_1.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    List<EntityData> entityDataList = wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0);
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    if (entityTracker.getTrackedEntities().containsKey(entityId)) {
                        entityTracker.updateEntityDataList(entityId, entityDataList);
                        Protocolr1_2_4_5Tor1_3_1_2.this.handleEntityDataList(entityId, entityDataList, wrapper);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.PRE_CHUNK, ClientboundPackets1_3_1.LEVEL_CHUNK, (PacketWrapper wrapper) -> {
            int chunkX = wrapper.read(Types.INT);
            int chunkZ = wrapper.read(Types.INT);
            boolean load = wrapper.read(Types.BOOLEAN);
            wrapper.user().get(ChestStateTracker.class).unload(chunkX, chunkZ);
            Chunk chunk = load ? ChunkUtil.createEmptyChunk(chunkX, chunkZ) : new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>());
            wrapper.write(Types1_7_6.getChunk(((ClientWorld)wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class)).getEnvironment()), chunk);
        });
        this.registerClientbound(ClientboundPackets1_2_4.LEVEL_CHUNK, wrapper -> {
            Environment dimension = ((ClientWorld)wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class)).getEnvironment();
            Chunk chunk = wrapper.read(Types1_2_4.CHUNK);
            wrapper.user().get(ChestStateTracker.class).unload(chunk.getX(), chunk.getZ());
            if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    ViaLegacy.getPlatform().getLogger().warning("Received empty 1.2.5 chunk packet");
                }
                chunk = ChunkUtil.createEmptyChunk(chunk.getX(), chunk.getZ());
                if (dimension == Environment.NORMAL) {
                    ChunkUtil.setDummySkylight(chunk, true);
                }
            }
            if (dimension != Environment.NORMAL) {
                for (ChunkSection section : chunk.getSections()) {
                    if (section == null) continue;
                    section.getLight().setSkyLight(null);
                }
            }
            wrapper.write(Types1_7_6.getChunk(dimension), chunk);
        });
        this.registerClientbound(ClientboundPackets1_2_4.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE, Types.UNSIGNED_SHORT);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    IdAndData block = wrapper.user().get(ChunkTracker.class).getBlockNotNull(wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0));
                    wrapper.write(Types.SHORT, (short)block.getId());
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    BlockPosition pos = wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0);
                    byte type = wrapper.get(Types.BYTE, 0);
                    short data = wrapper.get(Types.BYTE, 1).byteValue();
                    short blockId = wrapper.get(Types.SHORT, 0);
                    if (blockId <= 0) {
                        return;
                    }
                    float volume = 1.0f;
                    float pitch = 1.0f;
                    Sound sound = null;
                    if (block.getId() == BlockList1_6.music.blockId()) {
                        Sound sound2;
                        switch (type) {
                            default: {
                                sound2 = Sound.NOTE_HARP;
                                break;
                            }
                            case 1: {
                                sound2 = Sound.NOTE_CLICK;
                                break;
                            }
                            case 2: {
                                sound2 = Sound.NOTE_SNARE;
                                break;
                            }
                            case 3: {
                                sound2 = Sound.NOTE_HAT;
                                break;
                            }
                            case 4: {
                                sound2 = Sound.NOTE_BASS_ATTACK;
                            }
                        }
                        sound = sound2;
                        volume = 3.0f;
                        pitch = (float)Math.pow(2.0, (double)(data - 12) / 12.0);
                    } else if (block.getId() == BlockList1_6.chest.blockId()) {
                        if (type == 1) {
                            ChestStateTracker chestStateTracker = wrapper.user().get(ChestStateTracker.class);
                            if (chestStateTracker.isChestOpen(pos) && data <= 0) {
                                sound = Sound.CHEST_CLOSE;
                                chestStateTracker.closeChest(pos);
                            } else if (!chestStateTracker.isChestOpen(pos) && data > 0) {
                                sound = Sound.CHEST_OPEN;
                                chestStateTracker.openChest(pos);
                            }
                            volume = 0.5f;
                            pitch = entityTracker.RND.nextFloat() * 0.1f + 0.9f;
                        }
                    } else if (block.getId() == BlockList1_6.pistonBase.blockId() || block.getId() == BlockList1_6.pistonStickyBase.blockId()) {
                        if (type == 0) {
                            sound = Sound.PISTON_OUT;
                            volume = 0.5f;
                            pitch = entityTracker.RND.nextFloat() * 0.25f + 0.6f;
                        } else if (type == 1) {
                            sound = Sound.PISTON_IN;
                            volume = 0.5f;
                            pitch = entityTracker.RND.nextFloat() * 0.15f + 0.6f;
                        }
                    }
                    if (sound != null) {
                        entityTracker.playSoundAt(new Location(pos), sound, volume, pitch);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int count = wrapper.get(Types.INT, 0);
                    for (int i = 0; i < count * 3; ++i) {
                        wrapper.passthrough(Types.BYTE);
                    }
                });
                this.create(Types.FLOAT, Float.valueOf(0.0f));
                this.create(Types.FLOAT, Float.valueOf(0.0f));
                this.create(Types.FLOAT, Float.valueOf(0.0f));
                this.handler(wrapper -> {
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    Location loc = new Location(wrapper.get(Types.DOUBLE, 0), wrapper.get(Types.DOUBLE, 1), wrapper.get(Types.DOUBLE, 2));
                    entityTracker.playSoundAt(loc, Sound.RANDOM_EXPLODE, 4.0f, (1.0f + (entityTracker.RND.nextFloat() - entityTracker.RND.nextFloat()) * 0.2f) * 0.7f);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.CONTAINER_SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_2_4.NBT_ITEM, Types1_7_6.ITEM);
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.CONTAINER_SET_CONTENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types1_2_4.NBT_ITEM_ARRAY, Types1_7_6.ITEM_ARRAY);
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    int entityId = wrapper.read(Types.INT);
                    wrapper.read(Types.INT);
                    wrapper.read(Types.INT);
                    if (wrapper.get(Types.BYTE, 0) != 1) {
                        wrapper.cancel();
                        return;
                    }
                    BlockPosition pos = wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0);
                    CompoundTag tag = new CompoundTag();
                    tag.putString("EntityId", EntityList1_2_4.getEntityName(entityId));
                    tag.putShort("Delay", (short)20);
                    tag.putInt("x", pos.x());
                    tag.putInt("y", pos.y());
                    tag.putInt("z", pos.z());
                    wrapper.write(Types1_7_6.NBT, tag);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_2_4.PLAYER_ABILITIES, wrapper -> {
            boolean disableDamage = wrapper.read(Types.BOOLEAN);
            boolean flying = wrapper.read(Types.BOOLEAN);
            boolean allowFlying = wrapper.read(Types.BOOLEAN);
            boolean creativeMode = wrapper.read(Types.BOOLEAN);
            byte mask = 0;
            if (disableDamage) {
                mask = (byte)(mask | 1);
            }
            if (flying) {
                mask = (byte)(mask | 2);
            }
            if (allowFlying) {
                mask = (byte)(mask | 4);
            }
            if (creativeMode) {
                mask = (byte)(mask | 8);
            }
            wrapper.write(Types.BYTE, mask);
            wrapper.write(Types.BYTE, (byte)12);
            wrapper.write(Types.BYTE, (byte)25);
        });
        this.registerServerbound(ServerboundPackets1_3_1.CLIENT_PROTOCOL, ServerboundPackets1_2_4.HANDSHAKE, (PacketWrapper wrapper) -> {
            int port;
            wrapper.read(Types.UNSIGNED_BYTE);
            String userName = wrapper.read(Types1_6_4.STRING);
            String hostname = wrapper.read(Types1_6_4.STRING);
            int n = port = wrapper.read(Types.INT).intValue();
            String string = hostname;
            String string2 = userName;
            wrapper.write(Types1_6_4.STRING, string2 + ";" + string + ":" + n);
        });
        this.cancelServerbound(ServerboundPackets1_3_1.SHARED_KEY);
        this.registerServerbound(ServerboundPackets1_3_1.MOVE_PLAYER_POS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    AbstractTrackedEntity player = entityTracker.getTrackedEntities().get(entityTracker.getPlayerID());
                    if (wrapper.get(Types.DOUBLE, 1) == -999.0 && wrapper.get(Types.DOUBLE, 2) == -999.0) {
                        player.setRiding(true);
                    } else {
                        player.setRiding(false);
                        player.getLocation().setX(wrapper.get(Types.DOUBLE, 0));
                        player.getLocation().setY(wrapper.get(Types.DOUBLE, 1));
                        player.getLocation().setZ(wrapper.get(Types.DOUBLE, 3));
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_3_1.MOVE_PLAYER_POS_ROT, new PacketHandlers(){

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
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    AbstractTrackedEntity player = entityTracker.getTrackedEntities().get(entityTracker.getPlayerID());
                    if (wrapper.get(Types.DOUBLE, 1) == -999.0 && wrapper.get(Types.DOUBLE, 2) == -999.0) {
                        player.setRiding(true);
                    } else {
                        player.setRiding(false);
                        player.getLocation().setX(wrapper.get(Types.DOUBLE, 0));
                        player.getLocation().setY(wrapper.get(Types.DOUBLE, 1));
                        player.getLocation().setZ(wrapper.get(Types.DOUBLE, 3));
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_3_1.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_7_6.ITEM, Types1_2_4.NBT_ITEM);
                this.read(Types.UNSIGNED_BYTE);
                this.read(Types.UNSIGNED_BYTE);
                this.read(Types.UNSIGNED_BYTE);
            }
        });
        this.registerServerbound(ServerboundPackets1_3_1.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types1_7_6.ITEM, Types1_2_4.NBT_ITEM);
            }
        });
        this.registerServerbound(ServerboundPackets1_3_1.PLAYER_ABILITIES, wrapper -> {
            byte mask = wrapper.read(Types.BYTE);
            wrapper.read(Types.BYTE);
            wrapper.read(Types.BYTE);
            boolean disableDamage = (mask & 1) > 0;
            boolean flying = (mask & 2) > 0;
            boolean allowFlying = (mask & 4) > 0;
            boolean creativeMode = (mask & 8) > 0;
            wrapper.write(Types.BOOLEAN, disableDamage);
            wrapper.write(Types.BOOLEAN, flying);
            wrapper.write(Types.BOOLEAN, allowFlying);
            wrapper.write(Types.BOOLEAN, creativeMode);
        });
        this.registerServerbound(ServerboundPackets1_3_1.CLIENT_COMMAND, ServerboundPackets1_2_4.RESPAWN, (PacketWrapper wrapper) -> {
            byte action = wrapper.read(Types.BYTE);
            if (action != 1) {
                wrapper.cancel();
            }
            wrapper.write(Types.INT, 0);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.SHORT, (short)0);
            wrapper.write(Types1_6_4.STRING, "");
        });
        this.cancelServerbound(ServerboundPackets1_3_1.COMMAND_SUGGESTION);
        this.cancelServerbound(ServerboundPackets1_3_1.CLIENT_INFORMATION);
    }

    void handleEntityDataList(int entityId, List<EntityData> entityDataList, PacketWrapper wrapper) {
        EntityTracker tracker = wrapper.user().get(EntityTracker.class);
        if (entityId == tracker.getPlayerID()) {
            return;
        }
        AbstractTrackedEntity entity = tracker.getTrackedEntities().get(entityId);
        for (EntityData entityData : entityDataList) {
            if (EntityDataIndex1_5_2.searchIndex(entity.getEntityType(), entityData.id()) != null) continue;
            EntityDataIndex1_7_6 index2 = EntityDataIndex1_7_6.searchIndex(entity.getEntityType(), entityData.id());
            if (index2 == EntityDataIndex1_7_6.ENTITY_FLAGS) {
                if (((Byte)entityData.value() & 4) != 0) {
                    Optional<AbstractTrackedEntity> oNearbyEntity = tracker.getNearestEntity(entity.getLocation(), 1.0, e -> e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.MINECART) || e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.PIG) || e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.BOAT));
                    if (!oNearbyEntity.isPresent()) break;
                    entity.setRiding(true);
                    AbstractTrackedEntity nearbyEntity = oNearbyEntity.get();
                    PacketWrapper attachEntity = PacketWrapper.create(ClientboundPackets1_3_1.SET_ENTITY_LINK, wrapper.user());
                    attachEntity.write(Types.INT, entityId);
                    attachEntity.write(Types.INT, nearbyEntity.getEntityId());
                    wrapper.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                    attachEntity.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                    wrapper.cancel();
                    break;
                }
                if (((Byte)entityData.value() & 4) != 0 || !entity.isRiding()) break;
                entity.setRiding(false);
                PacketWrapper detachEntity = PacketWrapper.create(ClientboundPackets1_3_1.SET_ENTITY_LINK, wrapper.user());
                detachEntity.write(Types.INT, entityId);
                detachEntity.write(Types.INT, -1);
                detachEntity.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                wrapper.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                wrapper.cancel();
                break;
            }
            if (index2 != EntityDataIndex1_7_6.CREEPER_STATE || (Byte)entityData.value() <= 0) continue;
            tracker.playSoundAt(entity.getLocation(), Sound.RANDOM_FUSE, 1.0f, 0.5f);
        }
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(OldAuthProvider.class, new OldAuthProvider());
        if (ViaLegacy.getConfig().isSoundEmulation()) {
            Via.getPlatform().runRepeatingSync(new EntityTrackerTickTask(), 1L);
        }
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_2_4_5Tor1_3_1_2.class, ClientboundPackets1_2_4::getPacket));
        userConnection.addClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class, new ClientWorld());
        userConnection.put(new ChestStateTracker());
        userConnection.put(new EntityTracker(userConnection));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

