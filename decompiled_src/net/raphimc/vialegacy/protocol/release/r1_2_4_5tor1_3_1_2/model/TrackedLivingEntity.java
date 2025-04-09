/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.List;
import net.raphimc.vialegacy.api.model.Location;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundRegistry1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundType;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.AbstractTrackedEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.ConfiguredSound;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.data.EntityDataIndex1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;

public class TrackedLivingEntity
extends AbstractTrackedEntity {
    private int soundTime;
    public int growingAge;
    public boolean isTamed;
    public int wolfHealth;
    public boolean wolfIsAngry;

    public TrackedLivingEntity(int entityId, Location location, EntityTypes1_8.EntityType entityType) {
        super(entityId, location, entityType);
    }

    public void tick(EntityTracker tracker) {
        if (tracker.RND.nextInt(1000) < this.soundTime++) {
            this.soundTime = SoundRegistry1_2_4.getSoundDelayTime(this.getEntityType());
            tracker.playSound(this.getEntityId(), SoundType.IDLE);
        }
        if (this.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.ABSTRACT_AGEABLE)) {
            if (this.growingAge < 0) {
                ++this.growingAge;
            } else if (this.growingAge > 0) {
                --this.growingAge;
            }
        }
    }

    public void updateEntityData(List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            EntityDataIndex1_5_2 index2 = EntityDataIndex1_5_2.searchIndex(this.getEntityType(), entityData.id());
            EntityDataIndex1_7_6 index22 = EntityDataIndex1_7_6.searchIndex(this.getEntityType(), entityData.id());
            if (index2 == EntityDataIndex1_5_2.WOLF_HEALTH) {
                this.wolfHealth = (Integer)entityData.value();
            } else if (index2 != null) continue;
            if (index22 == EntityDataIndex1_7_6.ENTITY_AGEABLE_AGE) {
                this.growingAge = (Integer)entityData.value();
                continue;
            }
            if (index22 != EntityDataIndex1_7_6.TAMEABLE_FLAGS) continue;
            this.isTamed = ((Byte)entityData.value() & 4) != 0;
            this.wolfIsAngry = ((Byte)entityData.value() & 2) != 0;
        }
    }

    public void applyPitch(EntityTracker tracker, ConfiguredSound sound) {
        float pitch = this.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.ABSTRACT_AGEABLE) && this.growingAge < 0 ? (tracker.RND.nextFloat() - tracker.RND.nextFloat()) * 0.2f + 1.5f : (tracker.RND.nextFloat() - tracker.RND.nextFloat()) * 0.2f + 1.0f;
        sound.setPitch(pitch);
    }
}

