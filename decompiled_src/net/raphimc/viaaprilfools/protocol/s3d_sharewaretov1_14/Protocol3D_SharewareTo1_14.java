/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import net.raphimc.viaaprilfools.api.data.AprilFoolsMappingData;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ServerboundPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter.BlockItemPacketRewriter3D_Shareware;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter.EntityPacketRewriter3D_Shareware;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.storage.ChunkCenterTracker3D_Shareware;

public class Protocol3D_SharewareTo1_14
extends BackwardsProtocol<ClientboundPackets3D_Shareware, ClientboundPackets1_14, ServerboundPackets3D_Shareware, ServerboundPackets1_14> {
    public static final BackwardsMappingData MAPPINGS = new AprilFoolsMappingData("3D_Shareware", "1.14", Protocol1_13_2To1_14.class);
    private static final int SERVERSIDE_VIEW_DISTANCE = 64;
    private final BlockItemPacketRewriter3D_Shareware blockItemPackets = new BlockItemPacketRewriter3D_Shareware(this);

    public Protocol3D_SharewareTo1_14() {
        super(ClientboundPackets3D_Shareware.class, ClientboundPackets1_14.class, ServerboundPackets3D_Shareware.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        new EntityPacketRewriter3D_Shareware(this).registerPackets();
        SoundRewriter<ClientboundPackets3D_Shareware> soundRewriter = new SoundRewriter<ClientboundPackets3D_Shareware>(this);
        soundRewriter.registerSound(ClientboundPackets3D_Shareware.SOUND);
        soundRewriter.registerSound(ClientboundPackets3D_Shareware.SOUND_ENTITY);
        soundRewriter.registerNamedSound(ClientboundPackets3D_Shareware.CUSTOM_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets3D_Shareware.STOP_SOUND);
        this.registerClientbound(ClientboundPackets3D_Shareware.LEVEL_CHUNK, wrapper -> {
            ChunkCenterTracker3D_Shareware entityTracker = wrapper.user().get(ChunkCenterTracker3D_Shareware.class);
            Chunk chunk = wrapper.passthrough(ChunkType1_14.TYPE);
            int diffX = Math.abs(entityTracker.getChunkCenterX() - chunk.getX());
            int diffZ = Math.abs(entityTracker.getChunkCenterZ() - chunk.getZ());
            if (entityTracker.isForceSendCenterChunk() || diffX >= 64 || diffZ >= 64) {
                PacketWrapper fakePosLook = wrapper.create(ClientboundPackets1_14.SET_CHUNK_CACHE_CENTER);
                fakePosLook.write(Types.VAR_INT, chunk.getX());
                fakePosLook.write(Types.VAR_INT, chunk.getZ());
                fakePosLook.send(Protocol3D_SharewareTo1_14.class);
                entityTracker.setChunkCenterX(chunk.getX());
                entityTracker.setChunkCenterZ(chunk.getZ());
            }
        });
        this.registerClientbound(ClientboundPackets3D_Shareware.RESPAWN, wrapper -> wrapper.user().get(ChunkCenterTracker3D_Shareware.class).setForceSendCenterChunk(true));
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ChunkCenterTracker3D_Shareware());
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public BlockItemPacketRewriter3D_Shareware getItemRewriter() {
        return this.blockItemPackets;
    }
}

