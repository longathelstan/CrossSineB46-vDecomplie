/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data;

import com.viaversion.viarewind.api.minecraft.entitydata.EntityDataTypes1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;

public enum EntityDataIndex1_7_6_10 {
    ENTITY_FLAGS(EntityTypes1_8.EntityType.ENTITY, 0, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    ENTITY_AIR(EntityTypes1_8.EntityType.ENTITY, 1, EntityDataTypes1_7_6_10.SHORT, EntityDataTypes1_8.SHORT),
    ENTITY_NAME_TAG(EntityTypes1_8.EntityType.ENTITY, -1, null, 2, EntityDataTypes1_8.STRING),
    ENTITY_NAME_TAG_VISIBILITY(EntityTypes1_8.EntityType.ENTITY, -1, null, 3, EntityDataTypes1_8.BYTE),
    ENTITY_SILENT(EntityTypes1_8.EntityType.ENTITY, -1, null, 4, EntityDataTypes1_8.BYTE),
    LIVING_ENTITY_BASE_HEALTH(EntityTypes1_8.EntityType.LIVING_ENTITY_BASE, 6, EntityDataTypes1_7_6_10.FLOAT, EntityDataTypes1_8.FLOAT),
    LIVING_ENTITY_BASE_POTION_EFFECT_COLOR(EntityTypes1_8.EntityType.LIVING_ENTITY_BASE, 7, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    LIVING_ENTITY_BASE_IS_POTION_EFFECT_AMBIENT(EntityTypes1_8.EntityType.LIVING_ENTITY_BASE, 8, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    LIVING_ENTITY_BASE_ARROWS(EntityTypes1_8.EntityType.LIVING_ENTITY_BASE, 9, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    LIVING_ENTITY_BASE_NAME_TAG(EntityTypes1_8.EntityType.LIVING_ENTITY_BASE, 10, EntityDataTypes1_7_6_10.STRING, 2, EntityDataTypes1_8.STRING),
    LIVING_ENTITY_BASE_NAME_TAG_VISIBILITY(EntityTypes1_8.EntityType.LIVING_ENTITY_BASE, 11, EntityDataTypes1_7_6_10.BYTE, 3, EntityDataTypes1_8.BYTE),
    LIVING_ENTITY_AI(EntityTypes1_8.EntityType.LIVING_ENTITY, -1, null, 15, EntityDataTypes1_8.BYTE),
    ABSTRACT_AGEABLE_AGE(EntityTypes1_8.EntityType.ABSTRACT_AGEABLE, 12, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.BYTE),
    ARMOR_STAND_FLAGS(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 10, EntityDataTypes1_8.BYTE),
    ARMOR_STAND_HEAD_POSITION(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 11, EntityDataTypes1_8.ROTATIONS),
    ARMOR_STAND_BODY_POSITION(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 12, EntityDataTypes1_8.ROTATIONS),
    ARMOR_STAND_LEFT_ARM_POSITION(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 13, EntityDataTypes1_8.ROTATIONS),
    ARMOR_STAND_RIGHT_ARM_POSITION(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 14, EntityDataTypes1_8.ROTATIONS),
    ARMOR_STAND_LEFT_LEG_POSITION(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 15, EntityDataTypes1_8.ROTATIONS),
    ARMOR_STAND_RIGHT_LEG_POSITION(EntityTypes1_8.EntityType.ARMOR_STAND, -1, null, 16, EntityDataTypes1_8.ROTATIONS),
    PLAYER_SKIN_FLAGS(EntityTypes1_8.EntityType.PLAYER, 16, EntityDataTypes1_7_6_10.BYTE, 10, EntityDataTypes1_8.BYTE),
    PLAYER_UNUSED(EntityTypes1_8.EntityType.PLAYER, -1, null, 16, EntityDataTypes1_8.BYTE),
    PLAYER_ABSORPTION_HEATS(EntityTypes1_8.EntityType.PLAYER, 17, EntityDataTypes1_7_6_10.FLOAT, EntityDataTypes1_8.FLOAT),
    PLAYER_SCORE(EntityTypes1_8.EntityType.PLAYER, 18, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    HORSE_FLAGS(EntityTypes1_8.EntityType.HORSE, 16, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    HORSE_TYPE(EntityTypes1_8.EntityType.HORSE, 19, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    HORSE_COLOR(EntityTypes1_8.EntityType.HORSE, 20, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    HORSE_OWNER(EntityTypes1_8.EntityType.HORSE, 21, EntityDataTypes1_7_6_10.STRING, EntityDataTypes1_8.STRING),
    HORSE_ARMOR(EntityTypes1_8.EntityType.HORSE, 22, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    BAT_HANGING(EntityTypes1_8.EntityType.BAT, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    TAMABLE_ANIMAL_FLAGS(EntityTypes1_8.EntityType.TAMABLE_ANIMAL, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    TAMABLE_ANIMAL_OWNER(EntityTypes1_8.EntityType.TAMABLE_ANIMAL, 17, EntityDataTypes1_7_6_10.STRING, EntityDataTypes1_8.STRING),
    OCELOT_TYPE(EntityTypes1_8.EntityType.OCELOT, 18, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    WOLF_FLAGS(EntityTypes1_8.EntityType.WOLF, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    WOLF_HEALTH(EntityTypes1_8.EntityType.WOLF, 18, EntityDataTypes1_7_6_10.FLOAT, EntityDataTypes1_8.FLOAT),
    WOLF_BEGGING(EntityTypes1_8.EntityType.WOLF, 19, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    WOLF_COLLAR_COLOR(EntityTypes1_8.EntityType.WOLF, 20, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    PIG_SADDLE(EntityTypes1_8.EntityType.PIG, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    SHEEP_COLOR_OR_SHEARED(EntityTypes1_8.EntityType.SHEEP, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    VILLAGER_TYPE(EntityTypes1_8.EntityType.VILLAGER, 16, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    ENDERMAN_CARRIED_BLOCK(EntityTypes1_8.EntityType.ENDERMAN, 16, null, EntityDataTypes1_8.SHORT),
    ENDERMAN_CARRIED_BLOCK_DATA(EntityTypes1_8.EntityType.ENDERMAN, 17, null, EntityDataTypes1_8.BYTE),
    ENDERMAN_IS_SCREAMING(EntityTypes1_8.EntityType.ENDERMAN, 18, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    ZOMBIE_CHILD(EntityTypes1_8.EntityType.ZOMBIE, 12, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    ZOMBIE_VILLAGER(EntityTypes1_8.EntityType.ZOMBIE, 13, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    ZOMBIE_CONVERTING(EntityTypes1_8.EntityType.ZOMBIE, 14, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    BLAZE_ON_FIRE(EntityTypes1_8.EntityType.BLAZE, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    SPIDER_CLIMBING(EntityTypes1_8.EntityType.SPIDER, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    CREEPER_STATE(EntityTypes1_8.EntityType.CREEPER, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    CREEPER_POWERED(EntityTypes1_8.EntityType.CREEPER, 17, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    CREEPER_IGNITED(EntityTypes1_8.EntityType.CREEPER, 18, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    GHAST_STATE(EntityTypes1_8.EntityType.GHAST, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    GHAST_IS_POWERED(EntityTypes1_8.EntityType.GHAST, 17, null, EntityDataTypes1_8.BYTE),
    SLIME_SIZE(EntityTypes1_8.EntityType.SLIME, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    SKELETON_TYPE(EntityTypes1_8.EntityType.SKELETON, 13, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    WITCH_AGGRESSIVE(EntityTypes1_8.EntityType.WITCH, 21, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    IRON_GOLEM_IS_PLAYER_CREATED(EntityTypes1_8.EntityType.IRON_GOLEM, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    WITHER_WATCHED_TARGET_1(EntityTypes1_8.EntityType.WITHER, 17, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    WITHER_WATCHED_TARGET_2(EntityTypes1_8.EntityType.WITHER, 18, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    WITHER_WATCHED_TARGET_3(EntityTypes1_8.EntityType.WITHER, 19, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    WITHER_INVULNERABLE_TIME(EntityTypes1_8.EntityType.WITHER, 20, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    GUARDIAN_FLAGS(EntityTypes1_8.EntityType.GUARDIAN, 16, null, EntityDataTypes1_8.BYTE),
    GUARDIAN_TARGET(EntityTypes1_8.EntityType.GUARDIAN, 17, null, EntityDataTypes1_8.INT),
    BOAT_TIME_SINCE_HIT(EntityTypes1_8.EntityType.BOAT, 17, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    BOAT_FORWARD_DIRECTION(EntityTypes1_8.EntityType.BOAT, 18, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    BOAT_DAMAGE_TAKEN(EntityTypes1_8.EntityType.BOAT, 19, EntityDataTypes1_7_6_10.FLOAT, EntityDataTypes1_8.FLOAT),
    ABSTRACT_MINECART_SHAKING_POWER(EntityTypes1_8.EntityType.ABSTRACT_MINECART, 17, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    ABSTRACT_MINECART_SHAKING_DIRECTION(EntityTypes1_8.EntityType.ABSTRACT_MINECART, 18, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    ABSTRACT_MINECART_DAMAGE_TAKEN(EntityTypes1_8.EntityType.ABSTRACT_MINECART, 19, EntityDataTypes1_7_6_10.FLOAT, EntityDataTypes1_8.FLOAT),
    ABSTRACT_MINECART_BLOCK_INSIDE(EntityTypes1_8.EntityType.ABSTRACT_MINECART, 20, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    ABSTRACT_MINECART_BLOCK_Y(EntityTypes1_8.EntityType.ABSTRACT_MINECART, 21, EntityDataTypes1_7_6_10.INT, EntityDataTypes1_8.INT),
    ABSTRACT_MINECART_SHOW_BLOCK(EntityTypes1_8.EntityType.ABSTRACT_MINECART, 22, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    FURNACE_MINECART_IS_POWERED(EntityTypes1_8.EntityType.FURNACE_MINECART, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    ITEM_ITEM(EntityTypes1_8.EntityType.ITEM, 10, EntityDataTypes1_7_6_10.ITEM, EntityDataTypes1_8.ITEM),
    ARROW_IS_CRITICAL(EntityTypes1_8.EntityType.ARROW, 16, EntityDataTypes1_7_6_10.BYTE, EntityDataTypes1_8.BYTE),
    FIREWORK_ROCKET_INFO(EntityTypes1_8.EntityType.FIREWORK_ROCKET, 8, EntityDataTypes1_7_6_10.ITEM, EntityDataTypes1_8.ITEM),
    ITEM_FRAME_ITEM(EntityTypes1_8.EntityType.ITEM_FRAME, 2, EntityDataTypes1_7_6_10.ITEM, 8, EntityDataTypes1_8.ITEM),
    ITEM_FRAME_ROTATION(EntityTypes1_8.EntityType.ITEM_FRAME, 3, EntityDataTypes1_7_6_10.BYTE, 9, EntityDataTypes1_8.BYTE),
    END_CRYSTAL_HEALTH(EntityTypes1_8.EntityType.END_CRYSTAL, 8, EntityDataTypes1_7_6_10.INT, 9, EntityDataTypes1_8.INT);

    private static final HashMap<Pair<EntityTypes1_8.EntityType, Integer>, EntityDataIndex1_7_6_10> ENTITY_DATA_REWRITES;
    private final EntityTypes1_8.EntityType clazz;
    private final int newIndex;
    private final EntityDataTypes1_8 newType;
    private final EntityDataTypes1_7_6_10 oldType;
    private final int index;

    private EntityDataIndex1_7_6_10(EntityTypes1_8.EntityType type, int index2, EntityDataTypes1_7_6_10 oldType, EntityDataTypes1_8 newType) {
        this.clazz = type;
        this.index = index2;
        this.newIndex = index2;
        this.oldType = oldType;
        this.newType = newType;
    }

    private EntityDataIndex1_7_6_10(EntityTypes1_8.EntityType type, int index2, EntityDataTypes1_7_6_10 oldType, int newIndex, EntityDataTypes1_8 newType) {
        this.clazz = type;
        this.index = index2;
        this.oldType = oldType;
        this.newIndex = newIndex;
        this.newType = newType;
    }

    private static Optional<EntityDataIndex1_7_6_10> getIndex(EntityType type, int index2) {
        Pair<EntityType, Integer> pair = new Pair<EntityType, Integer>(type, index2);
        return Optional.ofNullable(ENTITY_DATA_REWRITES.get(pair));
    }

    public EntityTypes1_8.EntityType getClazz() {
        return this.clazz;
    }

    public int getNewIndex() {
        return this.newIndex;
    }

    public EntityDataTypes1_8 getNewType() {
        return this.newType;
    }

    public EntityDataTypes1_7_6_10 getOldType() {
        return this.oldType;
    }

    public int getIndex() {
        return this.index;
    }

    public static EntityDataIndex1_7_6_10 searchIndex(EntityType type, int index2) {
        EntityType currentType = type;
        do {
            Optional<EntityDataIndex1_7_6_10> optMeta;
            if (!(optMeta = EntityDataIndex1_7_6_10.getIndex(currentType, index2)).isPresent()) continue;
            return optMeta.get();
        } while ((currentType = currentType.getParent()) != null);
        return null;
    }

    static {
        ENTITY_DATA_REWRITES = new HashMap();
        for (EntityDataIndex1_7_6_10 index2 : EntityDataIndex1_7_6_10.values()) {
            ENTITY_DATA_REWRITES.put(new Pair<EntityTypes1_8.EntityType, Integer>(index2.getClazz(), index2.getNewIndex()), index2);
        }
    }
}

