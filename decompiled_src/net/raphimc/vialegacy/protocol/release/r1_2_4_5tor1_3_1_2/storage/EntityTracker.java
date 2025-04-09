/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.MathUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.model.Location;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.Protocolr1_2_4_5Tor1_3_1_2;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.Sound;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundRegistry1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundType;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.AbstractTrackedEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.ConfiguredSound;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.TrackedLivingEntity;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ClientboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;

public class EntityTracker
extends StoredObject {
    public final Random RND = new Random();
    private final Map<Integer, AbstractTrackedEntity> entityMap = new ConcurrentHashMap<Integer, AbstractTrackedEntity>();
    private int playerID;

    public EntityTracker(UserConnection user) {
        super(user);
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public Map<Integer, AbstractTrackedEntity> getTrackedEntities() {
        return this.entityMap;
    }

    public void updateEntityLocation(int entityId, int x, int y, int z, boolean relative) {
        AbstractTrackedEntity entity = this.entityMap.get(entityId);
        if (entity != null) {
            Location oldLoc = entity.getLocation();
            double xPos = (double)x / 32.0;
            double yPos = (double)y / 32.0;
            double zPos = (double)z / 32.0;
            Location newLoc = relative ? new Location(oldLoc.getX() + xPos, oldLoc.getY() + yPos, oldLoc.getZ() + zPos) : new Location(xPos, yPos, zPos);
            entity.setLocation(newLoc);
        }
    }

    public void updateEntityDataList(int entityId, List<EntityData> entityDataList) {
        AbstractTrackedEntity entity = this.entityMap.get(entityId);
        if (entity instanceof TrackedLivingEntity) {
            TrackedLivingEntity livingEntity = (TrackedLivingEntity)entity;
            livingEntity.updateEntityData(entityDataList);
        }
    }

    public Optional<AbstractTrackedEntity> getNearestEntity(Location location, double range, Predicate<AbstractTrackedEntity> entityPredicate) {
        return this.entityMap.values().stream().filter(entityPredicate).filter(e -> !e.getLocation().equals(location)).filter(e -> e.getLocation().distanceTo(location) <= range).min(Comparator.comparingDouble(o -> o.getLocation().distanceTo(location)));
    }

    public void tick() {
        for (AbstractTrackedEntity entity : this.entityMap.values()) {
            if (!(entity instanceof TrackedLivingEntity)) continue;
            TrackedLivingEntity livingEntity = (TrackedLivingEntity)entity;
            livingEntity.tick(this);
        }
    }

    public void playSound(int entityId, SoundType type) {
        if (this.playerID == entityId && type == SoundType.HURT) {
            return;
        }
        if (this.entityMap.get(entityId) != null) {
            AbstractTrackedEntity entity = this.entityMap.get(entityId);
            ConfiguredSound sound = SoundRegistry1_2_4.getEntitySound(entity.getEntityType(), type);
            Location entityLocation = entity.getLocation();
            Location playerLocation = this.entityMap.get(this.playerID).getLocation();
            if (entity instanceof TrackedLivingEntity) {
                TrackedLivingEntity livingEntity = (TrackedLivingEntity)entity;
                if (type == SoundType.IDLE) {
                    livingEntity.applyPitch(this, sound);
                    if (entity.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.WOLF)) {
                        if (livingEntity.wolfIsAngry) {
                            sound.setSound(Sound.MOB_WOLF_GROWL);
                        } else if (this.RND.nextInt(3) == 0) {
                            sound.setSound(livingEntity.isTamed && livingEntity.wolfHealth < 10 ? Sound.MOB_WOLF_WHINE : Sound.MOB_WOLF_PANTING);
                        }
                    } else if (entity.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.OCELOT) && livingEntity.isTamed) {
                        sound.setSound(this.RND.nextInt(4) == 0 ? Sound.MOB_CAT_PURREOW : Sound.MOB_CAT_MEOW);
                    }
                }
            }
            if (Sound.NO_SOUND.equals((Object)sound.getSound())) {
                return;
            }
            this.playSoundAt(entityLocation, playerLocation, sound);
        }
    }

    public void playSoundAt(Location entityLocation, Sound sound, float volume, float pitch) {
        Location playerLocation = this.entityMap.get(this.playerID).getLocation();
        this.playSoundAt(entityLocation, playerLocation, new ConfiguredSound(sound, volume, pitch));
    }

    public static short constrainToRange(short value, short min, short max) {
        return value < min ? min : (value < max ? value : max);
    }

    private void playSoundAt(Location sourceLocation, Location targetLocation, ConfiguredSound sound) {
        if (!ViaLegacy.getConfig().isSoundEmulation()) {
            return;
        }
        short correctedPitch = (short)MathUtil.clamp((int)(sound.getPitch() * 63.0f), 0, 255);
        float vol = sound.getVolume();
        float range = 16.0f;
        if (vol > 1.0f) {
            range *= vol;
        }
        if (targetLocation.distanceTo(sourceLocation) > (double)range) {
            return;
        }
        PacketWrapper entitySound = PacketWrapper.create(ClientboundPackets1_3_1.CUSTOM_SOUND, this.user());
        entitySound.write(Types1_6_4.STRING, sound.getSound().getSoundName());
        entitySound.write(Types.INT, (int)sourceLocation.getX() * 8);
        entitySound.write(Types.INT, (int)sourceLocation.getY() * 8);
        entitySound.write(Types.INT, (int)sourceLocation.getZ() * 8);
        entitySound.write(Types.FLOAT, Float.valueOf(vol));
        entitySound.write(Types.UNSIGNED_BYTE, correctedPitch);
        entitySound.send(Protocolr1_2_4_5Tor1_3_1_2.class);
    }
}

