/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model;

import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.Sound;

public class ConfiguredSound {
    private Sound sound;
    private float volume;
    private float pitch;

    public ConfiguredSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getSound() {
        return this.sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}

