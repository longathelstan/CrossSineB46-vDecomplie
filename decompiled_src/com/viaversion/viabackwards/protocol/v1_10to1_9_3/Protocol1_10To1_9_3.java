/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_10to1_9_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_10to1_9_3.rewriter.BlockItemPacketRewriter1_10;
import com.viaversion.viabackwards.protocol.v1_10to1_9_3.rewriter.EntityPacketRewriter1_10;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class Protocol1_10To1_9_3
extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.10", "1.9.4");
    static final ValueTransformer<Float, Short> TO_OLD_PITCH = new ValueTransformer<Float, Short>((Type)Types.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper packetWrapper, Float inputValue) {
            return (short)Math.round(inputValue.floatValue() * 63.5f);
        }
    };
    final EntityPacketRewriter1_10 entityRewriter = new EntityPacketRewriter1_10(this);
    final BlockItemPacketRewriter1_10 itemRewriter = new BlockItemPacketRewriter1_10(this);

    public Protocol1_10To1_9_3() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        final SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter<ClientboundPackets1_9_3>(this);
        this.registerClientbound(ClientboundPackets1_9_3.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT, TO_OLD_PITCH);
                this.handler(soundRewriter.getNamedSoundHandler());
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
                this.map(Types.FLOAT, TO_OLD_PITCH);
                this.handler(soundRewriter.getSoundHandler());
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.STRING);
                this.map(Types.VAR_INT);
            }
        });
        TranslatableRewriter<ClientboundPackets1_9_3> componentRewriter = new TranslatableRewriter<ClientboundPackets1_9_3>(this, ComponentRewriter.ReadType.JSON);
        componentRewriter.registerComponentPacket(ClientboundPackets1_9_3.CHAT);
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_10.EntityType.PLAYER));
        user.addClientWorld(this.getClass(), new ClientWorld());
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_10 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_10 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public boolean hasMappingDataToLoad() {
        return true;
    }
}

