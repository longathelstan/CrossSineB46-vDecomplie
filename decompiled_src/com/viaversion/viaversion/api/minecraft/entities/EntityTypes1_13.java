/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.Via;
import java.util.HashMap;
import java.util.Map;

public class EntityTypes1_13 {
    public static EntityType getTypeFromId(int typeId, boolean object) {
        EntityType type = object ? ObjectType.getEntityType(typeId) : EntityType.findById(typeId);
        if (type == null) {
            boolean bl = object;
            int n = typeId;
            Via.getPlatform().getLogger().severe("Could not find 1.13 type id " + n + " objectType=" + bl);
            return EntityType.ENTITY;
        }
        return type;
    }

    public static enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType
    {
        BOAT(1, EntityType.BOAT),
        ITEM(2, EntityType.ITEM),
        AREA_EFFECT_CLOUD(3, EntityType.AREA_EFFECT_CLOUD),
        MINECART(10, EntityType.MINECART),
        TNT_PRIMED(50, EntityType.TNT),
        ENDER_CRYSTAL(51, EntityType.END_CRYSTAL),
        TIPPED_ARROW(60, EntityType.ARROW),
        SNOWBALL(61, EntityType.SNOWBALL),
        EGG(62, EntityType.EGG),
        FIREBALL(63, EntityType.FIREBALL),
        SMALL_FIREBALL(64, EntityType.SMALL_FIREBALL),
        ENDER_PEARL(65, EntityType.ENDER_PEARL),
        WITHER_SKULL(66, EntityType.WITHER_SKULL),
        SHULKER_BULLET(67, EntityType.SHULKER_BULLET),
        LLAMA_SPIT(68, EntityType.LLAMA_SPIT),
        FALLING_BLOCK(70, EntityType.FALLING_BLOCK),
        ITEM_FRAME(71, EntityType.ITEM_FRAME),
        EYE_OF_ENDER(72, EntityType.EYE_OF_ENDER),
        POTION(73, EntityType.POTION),
        EXPERIENCE_BOTTLE(75, EntityType.EXPERIENCE_BOTTLE),
        FIREWORK_ROCKET(76, EntityType.FIREWORK_ROCKET),
        LEASH(77, EntityType.LEASH_KNOT),
        ARMOR_STAND(78, EntityType.ARMOR_STAND),
        EVOKER_FANGS(79, EntityType.EVOKER_FANGS),
        FISHIHNG_HOOK(90, EntityType.FISHING_BOBBER),
        SPECTRAL_ARROW(91, EntityType.SPECTRAL_ARROW),
        DRAGON_FIREBALL(93, EntityType.DRAGON_FIREBALL),
        TRIDENT(94, EntityType.TRIDENT);

        static final Map<Integer, ObjectType> TYPES;
        final int id;
        final EntityType type;

        ObjectType(int id, EntityType type) {
            this.id = id;
            this.type = type;
        }

        @Override
        public int getId() {
            return this.id;
        }

        @Override
        public EntityType getType() {
            return this.type;
        }

        public static ObjectType findById(int id) {
            if (id == -1) {
                return null;
            }
            return TYPES.get(id);
        }

        public static EntityType getEntityType(int id) {
            ObjectType objectType = ObjectType.findById(id);
            return objectType != null ? objectType.type : null;
        }

        static {
            TYPES = new HashMap<Integer, ObjectType>();
            for (ObjectType type : ObjectType.values()) {
                TYPES.put(type.id, type);
            }
        }
    }

    public static enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType
    {
        ENTITY,
        AREA_EFFECT_CLOUD(0, ENTITY),
        END_CRYSTAL(16, ENTITY),
        EVOKER_FANGS(20, ENTITY),
        EXPERIENCE_ORB(22, ENTITY),
        EYE_OF_ENDER(23, ENTITY),
        FALLING_BLOCK(24, ENTITY),
        ITEM(32, ENTITY),
        TNT(55, ENTITY),
        LIGHTNING_BOLT(91, ENTITY),
        HANGING_ENTITY(ENTITY),
        LEASH_KNOT(35, HANGING_ENTITY),
        ITEM_FRAME(33, HANGING_ENTITY),
        PAINTING(49, HANGING_ENTITY),
        PROJECTILE(ENTITY),
        FIREWORK_ROCKET(25, ENTITY),
        LLAMA_SPIT(37, ENTITY),
        SHULKER_BULLET(60, ENTITY),
        SNOWBALL(67, PROJECTILE),
        ENDER_PEARL(75, PROJECTILE),
        EGG(74, PROJECTILE),
        EXPERIENCE_BOTTLE(76, PROJECTILE),
        POTION(77, PROJECTILE),
        FISHING_BOBBER(93, ENTITY),
        ABSTRACT_ARROW(ENTITY),
        ARROW(2, ABSTRACT_ARROW),
        SPECTRAL_ARROW(68, ABSTRACT_ARROW),
        TRIDENT(94, ABSTRACT_ARROW),
        HURTING_PROJECTILE(ENTITY),
        DRAGON_FIREBALL(13, HURTING_PROJECTILE),
        FIREBALL(34, HURTING_PROJECTILE),
        SMALL_FIREBALL(65, HURTING_PROJECTILE),
        WITHER_SKULL(85, HURTING_PROJECTILE),
        BOAT(5, ENTITY),
        ABSTRACT_MINECART(ENTITY),
        MINECART(39, ABSTRACT_MINECART),
        FURNACE_MINECART(42, ABSTRACT_MINECART),
        COMMAND_BLOCK_MINECART(41, ABSTRACT_MINECART),
        TNT_MINECART(45, ABSTRACT_MINECART),
        SPAWNER_MINECART(44, ABSTRACT_MINECART),
        ABSTRACT_MINECART_CONTAINER(ABSTRACT_MINECART),
        CHEST_MINECART(40, ABSTRACT_MINECART_CONTAINER),
        HOPPER_MINECART(43, ABSTRACT_MINECART_CONTAINER),
        LIVING_ENTITY_BASE(ENTITY),
        ARMOR_STAND(1, LIVING_ENTITY_BASE),
        PLAYER(92, LIVING_ENTITY_BASE),
        LIVING_ENTITY(LIVING_ENTITY_BASE),
        ENDER_DRAGON(17, LIVING_ENTITY),
        ABSTRACT_CREATURE(LIVING_ENTITY),
        SLIME(64, LIVING_ENTITY),
        MAGMA_CUBE(38, SLIME),
        FLYING_MOB(ABSTRACT_CREATURE),
        GHAST(26, FLYING_MOB),
        PHANTOM(90, FLYING_MOB),
        AMBIENT_CREATURE(-1, LIVING_ENTITY),
        BAT(3, AMBIENT_CREATURE),
        ABSTRACT_GOLEM(ABSTRACT_CREATURE),
        SNOW_GOLEM(66, ABSTRACT_GOLEM),
        IRON_GOLEM(80, ABSTRACT_GOLEM),
        SHULKER(59, ABSTRACT_GOLEM),
        WATER_ANIMAL(ABSTRACT_CREATURE),
        SQUID(70, WATER_ANIMAL),
        DOLPHIN(12, WATER_ANIMAL),
        ABSTRACT_FISH(WATER_ANIMAL),
        COD(8, ABSTRACT_FISH),
        PUFFERFISH(52, ABSTRACT_FISH),
        SALMON(57, ABSTRACT_FISH),
        TROPICAL_FISH(72, ABSTRACT_FISH),
        ABSTRACT_AGEABLE(ABSTRACT_CREATURE),
        VILLAGER(79, ABSTRACT_AGEABLE),
        ABSTRACT_ANIMAL(ABSTRACT_AGEABLE),
        CHICKEN(7, ABSTRACT_ANIMAL),
        COW(9, ABSTRACT_ANIMAL),
        MOOSHROOM(47, COW),
        PIG(51, ABSTRACT_ANIMAL),
        POLAR_BEAR(54, ABSTRACT_ANIMAL),
        RABBIT(56, ABSTRACT_ANIMAL),
        SHEEP(58, ABSTRACT_ANIMAL),
        TURTLE(73, ABSTRACT_ANIMAL),
        TAMABLE_ANIMAL(ABSTRACT_ANIMAL),
        OCELOT(48, TAMABLE_ANIMAL),
        WOLF(86, TAMABLE_ANIMAL),
        ABSTRACT_SHOULDER_RIDING(TAMABLE_ANIMAL),
        PARROT(50, ABSTRACT_SHOULDER_RIDING),
        ABSTRACT_HORSE(ABSTRACT_ANIMAL),
        HORSE(29, ABSTRACT_HORSE),
        SKELETON_HORSE(63, ABSTRACT_HORSE),
        ZOMBIE_HORSE(88, ABSTRACT_HORSE),
        CHESTED_HORSE(ABSTRACT_HORSE),
        DONKEY(11, CHESTED_HORSE),
        MULE(46, CHESTED_HORSE),
        LLAMA(36, CHESTED_HORSE),
        ABSTRACT_MONSTER(ABSTRACT_CREATURE),
        BLAZE(4, ABSTRACT_MONSTER),
        CREEPER(10, ABSTRACT_MONSTER),
        ENDERMITE(19, ABSTRACT_MONSTER),
        ENDERMAN(18, ABSTRACT_MONSTER),
        GIANT(27, ABSTRACT_MONSTER),
        SILVERFISH(61, ABSTRACT_MONSTER),
        VEX(78, ABSTRACT_MONSTER),
        WITCH(82, ABSTRACT_MONSTER),
        WITHER(83, ABSTRACT_MONSTER),
        ABSTRACT_SKELETON(ABSTRACT_MONSTER),
        SKELETON(62, ABSTRACT_SKELETON),
        STRAY(71, ABSTRACT_SKELETON),
        WITHER_SKELETON(84, ABSTRACT_SKELETON),
        ZOMBIE(87, ABSTRACT_MONSTER),
        DROWNED(14, ZOMBIE),
        HUSK(30, ZOMBIE),
        ZOMBIE_PIGMAN(53, ZOMBIE),
        ZOMBIE_VILLAGER(89, ZOMBIE),
        GUARDIAN(28, ABSTRACT_MONSTER),
        ELDER_GUARDIAN(15, GUARDIAN),
        SPIDER(69, ABSTRACT_MONSTER),
        CAVE_SPIDER(6, SPIDER),
        ABSTRACT_ILLAGER(ABSTRACT_MONSTER),
        SPELLCASTER_ILLAGER(ABSTRACT_ILLAGER),
        VINDICATOR(81, ABSTRACT_ILLAGER),
        EVOKER(21, SPELLCASTER_ILLAGER),
        ILLUSIONER(31, SPELLCASTER_ILLAGER);

        static final Map<Integer, EntityType> TYPES;
        final int id;
        final EntityType parent;

        EntityType() {
            this.id = -1;
            this.parent = null;
        }

        EntityType(EntityType parent) {
            this.id = -1;
            this.parent = parent;
        }

        EntityType(int id, EntityType parent) {
            this.id = id;
            this.parent = parent;
        }

        @Override
        public int getId() {
            return this.id;
        }

        @Override
        public EntityType getParent() {
            return this.parent;
        }

        @Override
        public String identifier() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAbstractType() {
            return this.id != -1;
        }

        public static EntityType findById(int id) {
            if (id == -1) {
                return null;
            }
            return TYPES.get(id);
        }

        static {
            TYPES = new HashMap<Integer, EntityType>();
            for (EntityType type : EntityType.values()) {
                TYPES.put(type.id, type);
            }
        }
    }
}

