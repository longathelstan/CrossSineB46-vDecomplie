/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.data;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum EntityDataIndex1_9 {
    ENTITY_STATUS(EntityTypes1_9.EntityType.ENTITY, 0, EntityDataTypes1_8.BYTE, EntityDataTypes1_9.BYTE),
    ENTITY_AIR(EntityTypes1_9.EntityType.ENTITY, 1, EntityDataTypes1_8.SHORT, EntityDataTypes1_9.VAR_INT),
    ENTITY_NAMETAG(EntityTypes1_9.EntityType.ENTITY, 2, EntityDataTypes1_8.STRING, EntityDataTypes1_9.STRING),
    ENTITY_ALWAYS_SHOW_NAMETAG(EntityTypes1_9.EntityType.ENTITY, 3, EntityDataTypes1_8.BYTE, EntityDataTypes1_9.BOOLEAN),
    ENTITY_SILENT(EntityTypes1_9.EntityType.ENTITY, 4, EntityDataTypes1_8.BYTE, EntityDataTypes1_9.BOOLEAN),
    LIVING_ENTITY_BASE_HEALTH(EntityTypes1_9.EntityType.LIVING_ENTITY_BASE, 6, EntityDataTypes1_8.FLOAT, EntityDataTypes1_9.FLOAT),
    LIVING_ENTITY_BASE_POTION_EFFECT_COLOR(EntityTypes1_9.EntityType.LIVING_ENTITY_BASE, 7, EntityDataTypes1_8.INT, EntityDataTypes1_9.VAR_INT),
    LIVING_ENTITY_BASE_IS_POTION_AMBIENT(EntityTypes1_9.EntityType.LIVING_ENTITY_BASE, 8, EntityDataTypes1_8.BYTE, EntityDataTypes1_9.BOOLEAN),
    LIVING_ENTITY_BASE_NUMBER_OF_ARROWS_IN(EntityTypes1_9.EntityType.LIVING_ENTITY_BASE, 9, EntityDataTypes1_8.BYTE, EntityDataTypes1_9.VAR_INT),
    LIVING_ENTITY_NO_AI(EntityTypes1_9.EntityType.LIVING_ENTITY, 15, EntityDataTypes1_8.BYTE, 10, EntityDataTypes1_9.BYTE),
    ABSTRACT_AGEABLE_AGE(EntityTypes1_9.EntityType.ABSTRACT_AGEABLE, 12, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BOOLEAN),
    ARMOR_STAND_INFO(EntityTypes1_9.EntityType.ARMOR_STAND, 10, EntityDataTypes1_8.BYTE, EntityDataTypes1_9.BYTE),
    ARMOR_STAND_HEAD_POS(EntityTypes1_9.EntityType.ARMOR_STAND, 11, EntityDataTypes1_8.ROTATIONS, EntityDataTypes1_9.ROTATIONS),
    ARMOR_STAND_BODY_POS(EntityTypes1_9.EntityType.ARMOR_STAND, 12, EntityDataTypes1_8.ROTATIONS, EntityDataTypes1_9.ROTATIONS),
    ARMOR_STAND_LA_POS(EntityTypes1_9.EntityType.ARMOR_STAND, 13, EntityDataTypes1_8.ROTATIONS, EntityDataTypes1_9.ROTATIONS),
    ARMOR_STAND_RA_POS(EntityTypes1_9.EntityType.ARMOR_STAND, 14, EntityDataTypes1_8.ROTATIONS, EntityDataTypes1_9.ROTATIONS),
    ARMOR_STAND_LL_POS(EntityTypes1_9.EntityType.ARMOR_STAND, 15, EntityDataTypes1_8.ROTATIONS, EntityDataTypes1_9.ROTATIONS),
    ARMOR_STAND_RL_POS(EntityTypes1_9.EntityType.ARMOR_STAND, 16, EntityDataTypes1_8.ROTATIONS, EntityDataTypes1_9.ROTATIONS),
    PLAYER_SKIN_FLAGS(EntityTypes1_9.EntityType.PLAYER, 10, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.BYTE),
    PLAYER_BYTE(EntityTypes1_9.EntityType.PLAYER, 16, EntityDataTypes1_8.BYTE, null),
    PLAYER_ADDITIONAL_HEARTS(EntityTypes1_9.EntityType.PLAYER, 17, EntityDataTypes1_8.FLOAT, 10, EntityDataTypes1_9.FLOAT),
    PLAYER_SCORE(EntityTypes1_9.EntityType.PLAYER, 18, EntityDataTypes1_8.INT, 11, EntityDataTypes1_9.VAR_INT),
    PLAYER_HAND(EntityTypes1_9.EntityType.PLAYER, 5, EntityDataTypes1_9.BYTE),
    HORSE_INFO(EntityTypes1_9.EntityType.HORSE, 16, EntityDataTypes1_8.INT, 12, EntityDataTypes1_9.BYTE),
    HORSE_TYPE(EntityTypes1_9.EntityType.HORSE, 19, EntityDataTypes1_8.BYTE, 13, EntityDataTypes1_9.VAR_INT),
    HORSE_SUBTYPE(EntityTypes1_9.EntityType.HORSE, 20, EntityDataTypes1_8.INT, 14, EntityDataTypes1_9.VAR_INT),
    HORSE_OWNER(EntityTypes1_9.EntityType.HORSE, 21, EntityDataTypes1_8.STRING, 15, EntityDataTypes1_9.OPTIONAL_UUID),
    HORSE_ARMOR(EntityTypes1_9.EntityType.HORSE, 22, EntityDataTypes1_8.INT, 16, EntityDataTypes1_9.VAR_INT),
    BAT_IS_HANGING(EntityTypes1_9.EntityType.BAT, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BYTE),
    TAMABLE_ANIMAL_ANIMAL_INFO(EntityTypes1_9.EntityType.TAMABLE_ANIMAL, 16, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.BYTE),
    TAMABLE_ANIMAL_ANIMAL_OWNER(EntityTypes1_9.EntityType.TAMABLE_ANIMAL, 17, EntityDataTypes1_8.STRING, 13, EntityDataTypes1_9.OPTIONAL_UUID),
    OCELOT_TYPE(EntityTypes1_9.EntityType.OCELOT, 18, EntityDataTypes1_8.BYTE, 14, EntityDataTypes1_9.VAR_INT),
    WOLF_HEALTH(EntityTypes1_9.EntityType.WOLF, 18, EntityDataTypes1_8.FLOAT, 14, EntityDataTypes1_9.FLOAT),
    WOLF_BEGGING(EntityTypes1_9.EntityType.WOLF, 19, EntityDataTypes1_8.BYTE, 15, EntityDataTypes1_9.BOOLEAN),
    WOLF_COLLAR(EntityTypes1_9.EntityType.WOLF, 20, EntityDataTypes1_8.BYTE, 16, EntityDataTypes1_9.VAR_INT),
    PIG_SADDLE(EntityTypes1_9.EntityType.PIG, 16, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.BOOLEAN),
    RABBIT_TYPE(EntityTypes1_9.EntityType.RABBIT, 18, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.VAR_INT),
    SHEEP_COLOR(EntityTypes1_9.EntityType.SHEEP, 16, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.BYTE),
    VILLAGER_PROFESSION(EntityTypes1_9.EntityType.VILLAGER, 16, EntityDataTypes1_8.INT, 12, EntityDataTypes1_9.VAR_INT),
    ENDERMAN_BLOCK_STATE(EntityTypes1_9.EntityType.ENDERMAN, 16, EntityDataTypes1_8.SHORT, 11, EntityDataTypes1_9.OPTIONAL_BLOCK_STATE),
    ENDERMAN_BLOCK_DATA(EntityTypes1_9.EntityType.ENDERMAN, 17, EntityDataTypes1_8.BYTE, null),
    ENDERMAN_IS_SCREAMING(EntityTypes1_9.EntityType.ENDERMAN, 18, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.BOOLEAN),
    ZOMBIE_IS_CHILD(EntityTypes1_9.EntityType.ZOMBIE, 12, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BOOLEAN),
    ZOMBIE_IS_VILLAGER(EntityTypes1_9.EntityType.ZOMBIE, 13, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.VAR_INT),
    ZOMBIE_IS_CONVERTING(EntityTypes1_9.EntityType.ZOMBIE, 14, EntityDataTypes1_8.BYTE, 13, EntityDataTypes1_9.BOOLEAN),
    BLAZE_ON_FIRE(EntityTypes1_9.EntityType.BLAZE, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BYTE),
    SPIDER_CLIMBING(EntityTypes1_9.EntityType.SPIDER, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BYTE),
    CREEPER_FUSE(EntityTypes1_9.EntityType.CREEPER, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.VAR_INT),
    CREEPER_IS_POWERED(EntityTypes1_9.EntityType.CREEPER, 17, EntityDataTypes1_8.BYTE, 12, EntityDataTypes1_9.BOOLEAN),
    CREEPER_IS_IGNITED(EntityTypes1_9.EntityType.CREEPER, 18, EntityDataTypes1_8.BYTE, 13, EntityDataTypes1_9.BOOLEAN),
    GHAST_IS_ATTACKING(EntityTypes1_9.EntityType.GHAST, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BOOLEAN),
    SLIME_SIZE(EntityTypes1_9.EntityType.SLIME, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.VAR_INT),
    SKELETON_TYPE(EntityTypes1_9.EntityType.SKELETON, 13, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.VAR_INT),
    WITCH_AGGRESSIVE(EntityTypes1_9.EntityType.WITCH, 21, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BOOLEAN),
    IRON_GOLEM_PLAYER_MADE(EntityTypes1_9.EntityType.IRON_GOLEM, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BYTE),
    WITHER_TARGET1(EntityTypes1_9.EntityType.WITHER, 17, EntityDataTypes1_8.INT, 11, EntityDataTypes1_9.VAR_INT),
    WITHER_TARGET2(EntityTypes1_9.EntityType.WITHER, 18, EntityDataTypes1_8.INT, 12, EntityDataTypes1_9.VAR_INT),
    WITHER_TARGET3(EntityTypes1_9.EntityType.WITHER, 19, EntityDataTypes1_8.INT, 13, EntityDataTypes1_9.VAR_INT),
    WITHER_INVULNERABILITY_TIME(EntityTypes1_9.EntityType.WITHER, 20, EntityDataTypes1_8.INT, 14, EntityDataTypes1_9.VAR_INT),
    WITHER_SKULL_INVULNERABILITY(EntityTypes1_9.EntityType.WITHER_SKULL, 10, EntityDataTypes1_8.BYTE, 5, EntityDataTypes1_9.BOOLEAN),
    GUARDIAN_INFO(EntityTypes1_9.EntityType.GUARDIAN, 16, EntityDataTypes1_8.INT, 11, EntityDataTypes1_9.BYTE),
    GUARDIAN_TARGET(EntityTypes1_9.EntityType.GUARDIAN, 17, EntityDataTypes1_8.INT, 12, EntityDataTypes1_9.VAR_INT),
    BOAT_SINCE_HIT(EntityTypes1_9.EntityType.BOAT, 17, EntityDataTypes1_8.INT, 5, EntityDataTypes1_9.VAR_INT),
    BOAT_FORWARD_DIRECTION(EntityTypes1_9.EntityType.BOAT, 18, EntityDataTypes1_8.INT, 6, EntityDataTypes1_9.VAR_INT),
    BOAT_DAMAGE_TAKEN(EntityTypes1_9.EntityType.BOAT, 19, EntityDataTypes1_8.FLOAT, 7, EntityDataTypes1_9.FLOAT),
    ABSTRACT_MINECART_SHAKING_POWER(EntityTypes1_9.EntityType.ABSTRACT_MINECART, 17, EntityDataTypes1_8.INT, 5, EntityDataTypes1_9.VAR_INT),
    ABSTRACT_MINECART_SHAKING_DIRECTION(EntityTypes1_9.EntityType.ABSTRACT_MINECART, 18, EntityDataTypes1_8.INT, 6, EntityDataTypes1_9.VAR_INT),
    ABSTRACT_MINECART_DAMAGE_TAKEN(EntityTypes1_9.EntityType.ABSTRACT_MINECART, 19, EntityDataTypes1_8.FLOAT, 7, EntityDataTypes1_9.FLOAT),
    ABSTRACT_MINECART_BLOCK(EntityTypes1_9.EntityType.ABSTRACT_MINECART, 20, EntityDataTypes1_8.INT, 8, EntityDataTypes1_9.VAR_INT),
    ABSTRACT_MINECART_BLOCK_Y(EntityTypes1_9.EntityType.ABSTRACT_MINECART, 21, EntityDataTypes1_8.INT, 9, EntityDataTypes1_9.VAR_INT),
    ABSTRACT_MINECART_SHOW_BLOCK(EntityTypes1_9.EntityType.ABSTRACT_MINECART, 22, EntityDataTypes1_8.BYTE, 10, EntityDataTypes1_9.BOOLEAN),
    COMMAND_BLOCK_MINECART_COMMAND(EntityTypes1_9.EntityType.COMMAND_BLOCK_MINECART, 23, EntityDataTypes1_8.STRING, 11, EntityDataTypes1_9.STRING),
    COMMAND_BLOCK_MINECART_OUTPUT(EntityTypes1_9.EntityType.COMMAND_BLOCK_MINECART, 24, EntityDataTypes1_8.STRING, 12, EntityDataTypes1_9.COMPONENT),
    FURNACE_MINECART_IS_POWERED(EntityTypes1_9.EntityType.FURNACE_MINECART, 16, EntityDataTypes1_8.BYTE, 11, EntityDataTypes1_9.BOOLEAN),
    ITEM_ITEM(EntityTypes1_9.EntityType.ITEM, 10, EntityDataTypes1_8.ITEM, 5, EntityDataTypes1_9.ITEM),
    ARROW_IS_CRIT(EntityTypes1_9.EntityType.ARROW, 16, EntityDataTypes1_8.BYTE, 5, EntityDataTypes1_9.BYTE),
    FIREWORK_ROCKET_INFO(EntityTypes1_9.EntityType.FIREWORK_ROCKET, 8, EntityDataTypes1_8.ITEM, 5, EntityDataTypes1_9.ITEM),
    ITEM_FRAME_ITEM(EntityTypes1_9.EntityType.ITEM_FRAME, 8, EntityDataTypes1_8.ITEM, 5, EntityDataTypes1_9.ITEM),
    ITEM_FRAME_ROTATION(EntityTypes1_9.EntityType.ITEM_FRAME, 9, EntityDataTypes1_8.BYTE, 6, EntityDataTypes1_9.VAR_INT),
    END_CRYSTAL_HEALTH(EntityTypes1_9.EntityType.END_CRYSTAL, 8, EntityDataTypes1_8.INT, null),
    ENDER_DRAGON_PHASE(EntityTypes1_9.EntityType.ENDER_DRAGON, 11, EntityDataTypes1_9.VAR_INT);

    private static final HashMap<Pair<EntityTypes1_9.EntityType, Integer>, EntityDataIndex1_9> entityDataRewriters;
    private final EntityTypes1_9.EntityType clazz;
    private final int newIndex;
    private final EntityDataTypes1_9 newType;
    private final EntityDataTypes1_8 oldType;
    private final int index;

    private EntityDataIndex1_9(EntityTypes1_9.EntityType type, @Nullable int index2, EntityDataTypes1_8 oldType, EntityDataTypes1_9 newType) {
        this.clazz = type;
        this.index = index2;
        this.newIndex = index2;
        this.oldType = oldType;
        this.newType = newType;
    }

    private EntityDataIndex1_9(@Nullable EntityTypes1_9.EntityType type, int newIndex, EntityDataTypes1_9 newType) {
        this.clazz = type;
        this.index = -1;
        this.oldType = null;
        this.newIndex = newIndex;
        this.newType = newType;
    }

    private EntityDataIndex1_9(EntityTypes1_9.EntityType type, int index2, @Nullable EntityDataTypes1_8 oldType, int newIndex, EntityDataTypes1_9 newType) {
        this.clazz = type;
        this.index = index2;
        this.oldType = oldType;
        this.newIndex = newIndex;
        this.newType = newType;
    }

    public EntityTypes1_9.EntityType getClazz() {
        return this.clazz;
    }

    public int getNewIndex() {
        return this.newIndex;
    }

    public @Nullable EntityDataTypes1_9 getNewType() {
        return this.newType;
    }

    public EntityDataTypes1_8 getOldType() {
        return this.oldType;
    }

    public int getIndex() {
        return this.index;
    }

    private static Optional<EntityDataIndex1_9> getIndex(EntityType type, int index2) {
        Pair<EntityType, Integer> pair = new Pair<EntityType, Integer>(type, index2);
        return Optional.ofNullable(entityDataRewriters.get(pair));
    }

    public static EntityDataIndex1_9 searchIndex(EntityType type, int index2) {
        EntityType currentType = type;
        do {
            Optional<EntityDataIndex1_9> optData;
            if (!(optData = EntityDataIndex1_9.getIndex(currentType, index2)).isPresent()) continue;
            return optData.get();
        } while ((currentType = currentType.getParent()) != null);
        return null;
    }

    static {
        entityDataRewriters = new HashMap();
        for (EntityDataIndex1_9 index2 : EntityDataIndex1_9.values()) {
            entityDataRewriters.put(new Pair<EntityTypes1_9.EntityType, Integer>(index2.clazz, index2.index), index2);
        }
    }
}

