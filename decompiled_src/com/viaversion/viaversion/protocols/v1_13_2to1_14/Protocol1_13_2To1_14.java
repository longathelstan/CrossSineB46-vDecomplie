/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_13_2to1_14;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.data.MappingData1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter.ComponentRewriter1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter.EntityPacketRewriter1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter.ItemPacketRewriter1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter.PlayerPacketRewriter1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter.WorldPacketRewriter1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Protocol1_13_2To1_14
extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_14, ServerboundPackets1_13, ServerboundPackets1_14> {
    public static final MappingData1_14 MAPPINGS = new MappingData1_14();
    final EntityPacketRewriter1_14 entityRewriter = new EntityPacketRewriter1_14(this);
    final ItemPacketRewriter1_14 itemRewriter = new ItemPacketRewriter1_14(this);
    final TagRewriter<ClientboundPackets1_13> tagRewriter = new TagRewriter<ClientboundPackets1_13>(this);

    public Protocol1_13_2To1_14() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        WorldPacketRewriter1_14.register(this);
        PlayerPacketRewriter1_14.register(this);
        new SoundRewriter<ClientboundPackets1_13>(this).registerSound(ClientboundPackets1_13.SOUND);
        new StatisticsRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.AWARD_STATS);
        ComponentRewriter1_14<ClientboundPackets1_13> componentRewriter = new ComponentRewriter1_14<ClientboundPackets1_13>(this);
        componentRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT);
        CommandRewriter<ClientboundPackets1_13> commandRewriter = new CommandRewriter<ClientboundPackets1_13>((Protocol)this){

            @Override
            public @Nullable String handleArgumentType(String argumentType) {
                if (argumentType.equals("minecraft:nbt")) {
                    return "minecraft:nbt_compound_tag";
                }
                return super.handleArgumentType(argumentType);
            }
        };
        commandRewriter.registerDeclareCommands(ClientboundPackets1_13.COMMANDS);
        this.registerClientbound(ClientboundPackets1_13.UPDATE_TAGS, new PacketHandlers(){

            @Override
            protected void register() {
                this.handler(Protocol1_13_2To1_14.this.tagRewriter.getHandler(RegistryType.FLUID));
                this.handler(wrapper -> Protocol1_13_2To1_14.this.tagRewriter.appendNewTags(wrapper, RegistryType.ENTITY));
            }
        });
        this.cancelServerbound(ServerboundPackets1_14.CHANGE_DIFFICULTY);
        this.cancelServerbound(ServerboundPackets1_14.LOCK_DIFFICULTY);
        this.cancelServerbound(ServerboundPackets1_14.SET_JIGSAW_BLOCK);
    }

    @Override
    protected void onMappingDataLoaded() {
        WorldPacketRewriter1_14.air = MAPPINGS.getBlockStateMappings().getNewId(0);
        WorldPacketRewriter1_14.voidAir = MAPPINGS.getBlockStateMappings().getNewId(8591);
        WorldPacketRewriter1_14.caveAir = MAPPINGS.getBlockStateMappings().getNewId(8592);
        EntityTypes1_14.initialize(this);
        Types1_13_2.PARTICLE.filler(this, false).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.ITEM1_13_2);
        Types1_14.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.ITEM1_13_2);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "bamboo_plantable_on");
        super.onMappingDataLoaded();
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_14(userConnection));
        userConnection.addClientWorld(this.getClass(), new ClientWorld());
    }

    @Override
    public MappingData1_14 getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_14 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_14 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_13> getTagRewriter() {
        return this.tagRewriter;
    }
}

