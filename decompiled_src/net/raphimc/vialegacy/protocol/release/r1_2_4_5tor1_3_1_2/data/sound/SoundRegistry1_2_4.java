/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.Sound;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundType;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.ConfiguredSound;

public class SoundRegistry1_2_4 {
    static final Sound[][] ENTITY_SOUNDS = new Sound[256][];
    static final float[] VOL_ADJUST = new float[256];
    static final int[] INTERVAL_ADJUST = new int[256];

    public static ConfiguredSound getEntitySound(EntityTypes1_8.EntityType entityType, SoundType soundType) {
        ConfiguredSound configuredSound;
        Sound[] entitySounds;
        ConfiguredSound sound = new ConfiguredSound(Sound.NO_SOUND, 0.0f, 1.0f);
        int entityTypeID = entityType.getId();
        if (entityType.isOrHasParent(EntityTypes1_8.EntityType.PLAYER)) {
            entityTypeID = 48;
        }
        if ((entitySounds = ENTITY_SOUNDS[entityTypeID]) == null) {
            return sound;
        }
        switch (soundType) {
            default: {
                throw new IncompatibleClassChangeError();
            }
            case IDLE: {
                configuredSound = new ConfiguredSound(entitySounds[0], 1.0f, 1.0f);
                break;
            }
            case HURT: {
                configuredSound = new ConfiguredSound(entitySounds[1], 1.0f, 1.0f);
                break;
            }
            case DEATH: {
                configuredSound = new ConfiguredSound(entitySounds[2], 1.0f, 1.0f);
            }
        }
        sound = configuredSound;
        float correctedVolume = VOL_ADJUST[entityTypeID];
        if (correctedVolume != 0.0f) {
            sound.setVolume(correctedVolume);
        }
        return sound;
    }

    public static int getSoundDelayTime(EntityTypes1_8.EntityType entityType) {
        int entityTypeID = entityType.getId();
        if (entityType.isOrHasParent(EntityTypes1_8.EntityType.PLAYER)) {
            entityTypeID = 48;
        }
        int soundTime = -80;
        int ajustedSoundTime = INTERVAL_ADJUST[entityTypeID];
        if (ajustedSoundTime != 0) {
            soundTime = -ajustedSoundTime;
        }
        return soundTime;
    }

    static {
        SoundRegistry1_2_4.ENTITY_SOUNDS[48] = new Sound[]{Sound.NO_SOUND, Sound.MOB_HUMAN_HURT, Sound.MOB_HUMAN_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.CREEPER.getId()] = new Sound[]{Sound.NO_SOUND, Sound.MOB_CREEPER, Sound.MOB_CREEPER_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SKELETON.getId()] = new Sound[]{Sound.MOB_SKELETON, Sound.MOB_SKELETON_HURT, Sound.MOB_SKELETON_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SPIDER.getId()] = new Sound[]{Sound.MOB_SPIDER, Sound.MOB_SPIDER, Sound.MOB_SPIDER_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.GIANT.getId()] = new Sound[]{Sound.NO_SOUND, Sound.MOB_HUMAN_HURT, Sound.MOB_HUMAN_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.ZOMBIE.getId()] = new Sound[]{Sound.MOB_ZOMBIE, Sound.MOB_ZOMBIE_HURT, Sound.MOB_ZOMBIE_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SLIME.getId()] = new Sound[]{Sound.MOB_SLIME, Sound.MOB_SLIME, Sound.MOB_SLIME};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.GHAST.getId()] = new Sound[]{Sound.MOB_GHAST, Sound.MOB_GHAST_HURT, Sound.MOB_GHAST_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.ZOMBIE_PIGMEN.getId()] = new Sound[]{Sound.MOB_PIG_ZOMBIE, Sound.MOB_PIG_ZOMBIE_HURT, Sound.MOB_PIG_ZOMBIE_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.ENDERMAN.getId()] = new Sound[]{Sound.MOB_ENDERMEN, Sound.MOB_ENDERMEN_HURT, Sound.MOB_ENDERMEN_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.CAVE_SPIDER.getId()] = new Sound[]{Sound.MOB_SPIDER, Sound.MOB_SPIDER, Sound.MOB_SPIDER_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SILVERFISH.getId()] = new Sound[]{Sound.MOB_SILVERFISH, Sound.MOB_SILVERFISH_HURT, Sound.MOB_SILVERFISH_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.BLAZE.getId()] = new Sound[]{Sound.MOB_BLAZE, Sound.MOB_BLAZE_HURT, Sound.MOB_BLAZE_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.MAGMA_CUBE.getId()] = new Sound[]{Sound.MOB_MAGMACUBE_SMALL, Sound.MOB_SLIME, Sound.MOB_SLIME};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.ENDER_DRAGON.getId()] = new Sound[]{Sound.NO_SOUND, Sound.MOB_HUMAN_HURT, Sound.MOB_HUMAN_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.PIG.getId()] = new Sound[]{Sound.MOB_PIG, Sound.MOB_PIG, Sound.MOB_PIG_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SHEEP.getId()] = new Sound[]{Sound.MOB_SHEEP, Sound.MOB_SHEEP, Sound.MOB_SHEEP};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.COW.getId()] = new Sound[]{Sound.MOB_COW, Sound.MOB_COW_HURT, Sound.MOB_COW_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.CHICKEN.getId()] = new Sound[]{Sound.MOB_CHICKEN, Sound.MOB_CHICKEN_HURT, Sound.MOB_CHICKEN_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SQUID.getId()] = new Sound[]{Sound.NO_SOUND, Sound.NO_SOUND, Sound.NO_SOUND};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.WOLF.getId()] = new Sound[]{Sound.MOB_WOLF, Sound.MOB_WOLF_HURT, Sound.MOB_WOLF_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.MOOSHROOM.getId()] = new Sound[]{Sound.MOB_COW, Sound.MOB_COW_HURT, Sound.MOB_COW_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.SNOW_GOLEM.getId()] = new Sound[]{Sound.NO_SOUND, Sound.NO_SOUND, Sound.NO_SOUND};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.OCELOT.getId()] = new Sound[]{Sound.NO_SOUND, Sound.MOB_CAT_HURT, Sound.MOB_CAT_HURT};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.IRON_GOLEM.getId()] = new Sound[]{Sound.NO_SOUND, Sound.MOB_IRON_GOLEM_HURT, Sound.MOB_IRON_GOLEM_DEATH};
        SoundRegistry1_2_4.ENTITY_SOUNDS[EntityTypes1_8.EntityType.VILLAGER.getId()] = new Sound[]{Sound.MOB_VILLAGER, Sound.MOB_VILLAGER_HURT, Sound.MOB_VILLAGER_DEATH};
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.SLIME.getId()] = 1.6f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.MAGMA_CUBE.getId()] = 1.6f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.GHAST.getId()] = 10.0f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.COW.getId()] = 0.4f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.WOLF.getId()] = 0.4f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.SQUID.getId()] = 0.4f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.MOOSHROOM.getId()] = 0.4f;
        SoundRegistry1_2_4.VOL_ADJUST[EntityTypes1_8.EntityType.OCELOT.getId()] = 0.4f;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.PIG.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.SHEEP.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.WOLF.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.SNOW_GOLEM.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.IRON_GOLEM.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.MOOSHROOM.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.COW.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.CHICKEN.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.SQUID.getId()] = 120;
        SoundRegistry1_2_4.INTERVAL_ADJUST[EntityTypes1_8.EntityType.OCELOT.getId()] = 120;
    }
}

