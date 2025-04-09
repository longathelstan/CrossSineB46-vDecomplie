/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entities;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.util.EntityTypeUtil;
import com.viaversion.viaversion.util.Key;
import java.util.Locale;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum EntityTypes1_19_3 implements EntityType
{
    ENTITY(null, null),
    AREA_EFFECT_CLOUD(ENTITY),
    END_CRYSTAL(ENTITY),
    EVOKER_FANGS(ENTITY),
    EXPERIENCE_ORB(ENTITY),
    EYE_OF_ENDER(ENTITY),
    FALLING_BLOCK(ENTITY),
    ITEM(ENTITY),
    TNT(ENTITY),
    MARKER(ENTITY),
    LIGHTNING_BOLT(ENTITY),
    HANGING_ENTITY(ENTITY, null),
    LEASH_KNOT(HANGING_ENTITY),
    PAINTING(HANGING_ENTITY),
    ITEM_FRAME(HANGING_ENTITY),
    GLOW_ITEM_FRAME(ITEM_FRAME),
    PROJECTILE(ENTITY, null),
    ITEM_PROJECTILE(PROJECTILE, null),
    SNOWBALL(ITEM_PROJECTILE),
    ENDER_PEARL(ITEM_PROJECTILE),
    EGG(ITEM_PROJECTILE),
    POTION(ITEM_PROJECTILE),
    EXPERIENCE_BOTTLE(ITEM_PROJECTILE),
    FIREWORK_ROCKET(PROJECTILE),
    LLAMA_SPIT(PROJECTILE),
    SHULKER_BULLET(PROJECTILE),
    FISHING_BOBBER(PROJECTILE),
    WITHER_SKULL(PROJECTILE),
    DRAGON_FIREBALL(PROJECTILE),
    ABSTRACT_ARROW(PROJECTILE, null),
    ARROW(ABSTRACT_ARROW),
    SPECTRAL_ARROW(ABSTRACT_ARROW),
    TRIDENT(ABSTRACT_ARROW),
    ABSTRACT_FIREBALL(ENTITY, null),
    FIREBALL(ABSTRACT_FIREBALL),
    SMALL_FIREBALL(ABSTRACT_FIREBALL),
    VEHICLE(ENTITY, null),
    BOAT(VEHICLE),
    ABSTRACT_MINECART(VEHICLE, null),
    MINECART(ABSTRACT_MINECART),
    FURNACE_MINECART(ABSTRACT_MINECART),
    COMMAND_BLOCK_MINECART(ABSTRACT_MINECART),
    TNT_MINECART(ABSTRACT_MINECART),
    SPAWNER_MINECART(ABSTRACT_MINECART),
    CHEST_BOAT(BOAT),
    ABSTRACT_MINECART_CONTAINER(ABSTRACT_MINECART, null),
    CHEST_MINECART(ABSTRACT_MINECART_CONTAINER),
    HOPPER_MINECART(ABSTRACT_MINECART_CONTAINER),
    LIVING_ENTITY(ENTITY, null),
    ARMOR_STAND(LIVING_ENTITY),
    PLAYER(LIVING_ENTITY),
    MOB(LIVING_ENTITY, null),
    ENDER_DRAGON(MOB),
    SLIME(MOB),
    MAGMA_CUBE(SLIME),
    AMBIENT_CREATURE(MOB, null),
    BAT(AMBIENT_CREATURE),
    FLYING_MOB(MOB, null),
    GHAST(FLYING_MOB),
    PHANTOM(FLYING_MOB),
    PATHFINDER_MOB(MOB, null),
    ALLAY(PATHFINDER_MOB),
    ABSTRACT_GOLEM(PATHFINDER_MOB, null),
    SNOW_GOLEM(ABSTRACT_GOLEM),
    IRON_GOLEM(ABSTRACT_GOLEM),
    SHULKER(ABSTRACT_GOLEM),
    WATER_ANIMAL(PATHFINDER_MOB, null),
    DOLPHIN(WATER_ANIMAL),
    SQUID(WATER_ANIMAL),
    GLOW_SQUID(SQUID),
    ABSTRACT_FISH(WATER_ANIMAL, null),
    PUFFERFISH(ABSTRACT_FISH),
    TADPOLE(ABSTRACT_FISH),
    ABSTRACT_SCHOOLING_FISH(ABSTRACT_FISH, null),
    COD(ABSTRACT_SCHOOLING_FISH),
    SALMON(ABSTRACT_SCHOOLING_FISH),
    TROPICAL_FISH(ABSTRACT_SCHOOLING_FISH),
    ABSTRACT_AGEABLE(PATHFINDER_MOB, null),
    ABSTRACT_VILLAGER(ABSTRACT_AGEABLE, null),
    VILLAGER(ABSTRACT_VILLAGER),
    WANDERING_TRADER(ABSTRACT_VILLAGER),
    ABSTRACT_ANIMAL(ABSTRACT_AGEABLE, null),
    AXOLOTL(ABSTRACT_ANIMAL),
    CHICKEN(ABSTRACT_ANIMAL),
    PANDA(ABSTRACT_ANIMAL),
    PIG(ABSTRACT_ANIMAL),
    POLAR_BEAR(ABSTRACT_ANIMAL),
    RABBIT(ABSTRACT_ANIMAL),
    SHEEP(ABSTRACT_ANIMAL),
    BEE(ABSTRACT_ANIMAL),
    TURTLE(ABSTRACT_ANIMAL),
    FOX(ABSTRACT_ANIMAL),
    FROG(ABSTRACT_ANIMAL),
    GOAT(ABSTRACT_ANIMAL),
    HOGLIN(ABSTRACT_ANIMAL),
    STRIDER(ABSTRACT_ANIMAL),
    COW(ABSTRACT_ANIMAL),
    MOOSHROOM(COW),
    TAMABLE_ANIMAL(ABSTRACT_ANIMAL, null),
    CAT(TAMABLE_ANIMAL),
    OCELOT(TAMABLE_ANIMAL),
    WOLF(TAMABLE_ANIMAL),
    PARROT(TAMABLE_ANIMAL),
    ABSTRACT_HORSE(ABSTRACT_ANIMAL, null),
    HORSE(ABSTRACT_HORSE),
    SKELETON_HORSE(ABSTRACT_HORSE),
    ZOMBIE_HORSE(ABSTRACT_HORSE),
    CAMEL(ABSTRACT_HORSE),
    ABSTRACT_CHESTED_HORSE(ABSTRACT_HORSE, null),
    DONKEY(ABSTRACT_CHESTED_HORSE),
    MULE(ABSTRACT_CHESTED_HORSE),
    LLAMA(ABSTRACT_CHESTED_HORSE),
    TRADER_LLAMA(LLAMA),
    ABSTRACT_MONSTER(PATHFINDER_MOB, null),
    BLAZE(ABSTRACT_MONSTER),
    CREEPER(ABSTRACT_MONSTER),
    ENDERMITE(ABSTRACT_MONSTER),
    ENDERMAN(ABSTRACT_MONSTER),
    GIANT(ABSTRACT_MONSTER),
    SILVERFISH(ABSTRACT_MONSTER),
    VEX(ABSTRACT_MONSTER),
    WITHER(ABSTRACT_MONSTER),
    ZOGLIN(ABSTRACT_MONSTER),
    WARDEN(ABSTRACT_MONSTER),
    ABSTRACT_SKELETON(ABSTRACT_MONSTER, null),
    SKELETON(ABSTRACT_SKELETON),
    STRAY(ABSTRACT_SKELETON),
    WITHER_SKELETON(ABSTRACT_SKELETON),
    ZOMBIE(ABSTRACT_MONSTER),
    DROWNED(ZOMBIE),
    HUSK(ZOMBIE),
    ZOMBIFIED_PIGLIN(ZOMBIE),
    ZOMBIE_VILLAGER(ZOMBIE),
    GUARDIAN(ABSTRACT_MONSTER),
    ELDER_GUARDIAN(GUARDIAN),
    SPIDER(ABSTRACT_MONSTER),
    CAVE_SPIDER(SPIDER),
    ABSTRACT_PIGLIN(ABSTRACT_MONSTER, null),
    PIGLIN(ABSTRACT_PIGLIN),
    PIGLIN_BRUTE(ABSTRACT_PIGLIN),
    ABSTRACT_RAIDER(ABSTRACT_MONSTER, null),
    WITCH(ABSTRACT_RAIDER),
    RAVAGER(ABSTRACT_RAIDER),
    ABSTRACT_ILLAGER(ABSTRACT_RAIDER, null),
    SPELLCASTER_ILLAGER(ABSTRACT_ILLAGER, null),
    VINDICATOR(ABSTRACT_ILLAGER),
    PILLAGER(ABSTRACT_ILLAGER),
    EVOKER(SPELLCASTER_ILLAGER),
    ILLUSIONER(SPELLCASTER_ILLAGER);

    private static final EntityType[] TYPES;
    private final EntityType parent;
    private final String identifier;
    private int id = -1;

    private EntityTypes1_19_3(EntityType parent) {
        this.parent = parent;
        this.identifier = Key.namespaced(this.name().toLowerCase(Locale.ROOT));
    }

    private EntityTypes1_19_3(EntityType parent, String identifier) {
        this.parent = parent;
        this.identifier = identifier;
    }

    @Override
    public int getId() {
        if (this.id == -1) {
            String string = this.name();
            throw new IllegalStateException("Ids have not been initialized yet (type " + string + ")");
        }
        return this.id;
    }

    @Override
    public String identifier() {
        Preconditions.checkArgument((this.identifier != null ? 1 : 0) != 0, (Object)"Called identifier method on abstract type");
        return this.identifier;
    }

    @Override
    public @Nullable EntityType getParent() {
        return this.parent;
    }

    @Override
    public boolean isAbstractType() {
        return this.identifier == null;
    }

    public static EntityType getTypeFromId(int typeId) {
        return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
    }

    public static void initialize(Protocol<?, ?, ?, ?> protocol) {
        EntityTypeUtil.initialize((EntityType[])EntityTypes1_19_3.values(), (EntityType[])TYPES, protocol, (type, id) -> {
            type.id = id;
        });
    }

    static {
        TYPES = EntityTypeUtil.createSizedArray(EntityTypes1_19_3.values());
    }
}

