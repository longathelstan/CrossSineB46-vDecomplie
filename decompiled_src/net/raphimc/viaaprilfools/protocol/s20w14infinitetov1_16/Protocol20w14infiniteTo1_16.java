/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.provider.PlayerAbilitiesProvider;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.UUID;
import net.raphimc.viaaprilfools.api.data.AprilFoolsMappingData;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.packet.ClientboundPackets20w14infinite;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.packet.ServerboundPackets20w14infinite;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.rewriter.BlockItemPacketRewriter20w14infinite;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.rewriter.EntityPacketRewriter20w14infinite;

public class Protocol20w14infiniteTo1_16
extends BackwardsProtocol<ClientboundPackets20w14infinite, ClientboundPackets1_16, ServerboundPackets20w14infinite, ServerboundPackets1_16> {
    public static final BackwardsMappingData MAPPINGS = new AprilFoolsMappingData("20w14infinite", "1.16", Protocol1_15_2To1_16.class);
    static final UUID ZERO_UUID = new UUID(0L, 0L);
    final BlockItemPacketRewriter20w14infinite itemRewriter = new BlockItemPacketRewriter20w14infinite(this);
    final EntityPacketRewriter20w14infinite entityRewriter = new EntityPacketRewriter20w14infinite(this);
    final TagRewriter<ClientboundPackets20w14infinite> tagRewriter = new TagRewriter<ClientboundPackets20w14infinite>(this);

    public Protocol20w14infiniteTo1_16() {
        super(ClientboundPackets20w14infinite.class, ClientboundPackets1_16.class, ServerboundPackets20w14infinite.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.tagRewriter.register(ClientboundPackets20w14infinite.UPDATE_TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets20w14infinite>(this).register(ClientboundPackets20w14infinite.AWARD_STATS);
        SoundRewriter<ClientboundPackets20w14infinite> soundRewriter = new SoundRewriter<ClientboundPackets20w14infinite>(this);
        soundRewriter.registerSound(ClientboundPackets20w14infinite.SOUND);
        soundRewriter.registerSound(ClientboundPackets20w14infinite.SOUND_ENTITY);
        soundRewriter.registerNamedSound(ClientboundPackets20w14infinite.CUSTOM_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets20w14infinite.STOP_SOUND);
        new RecipeRewriter<ClientboundPackets20w14infinite>(this).register(ClientboundPackets20w14infinite.UPDATE_RECIPES);
        this.registerClientbound(ClientboundPackets20w14infinite.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.COMPONENT);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.write(Types.UUID, ZERO_UUID));
            }
        });
        this.cancelServerbound(ServerboundPackets1_16.JIGSAW_GENERATE);
        this.registerServerbound(ServerboundPackets1_16.INTERACT, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Types.VAR_INT);
                    int action = wrapper.passthrough(Types.VAR_INT);
                    if (action == 0 || action == 2) {
                        if (action == 2) {
                            wrapper.passthrough(Types.FLOAT);
                            wrapper.passthrough(Types.FLOAT);
                            wrapper.passthrough(Types.FLOAT);
                        }
                        wrapper.passthrough(Types.VAR_INT);
                    }
                    wrapper.read(Types.BOOLEAN);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    PlayerAbilitiesProvider playerAbilities = Via.getManager().getProviders().get(PlayerAbilitiesProvider.class);
                    wrapper.write(Types.FLOAT, Float.valueOf(playerAbilities.getFlyingSpeed(wrapper.user())));
                    wrapper.write(Types.FLOAT, Float.valueOf(playerAbilities.getWalkingSpeed(wrapper.user())));
                });
            }
        });
    }

    @Override
    protected void onMappingDataLoaded() {
        int[] wallPostOverrideTag = new int[47];
        int arrayIndex = 0;
        wallPostOverrideTag[arrayIndex++] = 140;
        wallPostOverrideTag[arrayIndex++] = 179;
        wallPostOverrideTag[arrayIndex++] = 264;
        int i = 153;
        while (i <= 158) {
            wallPostOverrideTag[arrayIndex++] = i++;
        }
        i = 163;
        while (i <= 168) {
            wallPostOverrideTag[arrayIndex++] = i++;
        }
        i = 408;
        while (i <= 439) {
            wallPostOverrideTag[arrayIndex++] = i++;
        }
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", wallPostOverrideTag);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:beacon_base_blocks", 133, 134, 148, 265);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:climbable", 160, 241, 658);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fire", 142);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:campfires", 679);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fence_gates", 242, 467, 468, 469, 470, 471);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:unstable_bottom_center", 242, 467, 468, 469, 470, 471);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wooden_trapdoors", 193, 194, 195, 196, 197, 198);
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:wooden_trapdoors", 215, 216, 217, 218, 219, 220);
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:beacon_payment_items", 529, 530, 531, 760);
        this.tagRewriter.addTag(RegistryType.ENTITY, "minecraft:impact_projectiles", 2, 72, 71, 37, 69, 79, 83, 15, 93);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:guarded_by_piglins");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_speed_blocks");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_fire_base_blocks");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:non_flammable_wood");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables", "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers", "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors");
        this.tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons");
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers", "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences");
        super.onMappingDataLoaded();
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_16.PLAYER));
    }

    public BlockItemPacketRewriter20w14infinite getItemRewriter() {
        return this.itemRewriter;
    }

    public EntityPacketRewriter20w14infinite getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }
}

