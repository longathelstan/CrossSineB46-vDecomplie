/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Instrument {
    final Holder<SoundEvent> soundEvent;
    final int useDuration;
    final float range;
    public static final HolderType<Instrument> TYPE = new HolderType<Instrument>(){

        @Override
        public Instrument readDirect(ByteBuf buffer) {
            Object soundEvent = Types.SOUND_EVENT.read(buffer);
            int useDuration = Types.VAR_INT.readPrimitive(buffer);
            float range = buffer.readFloat();
            return new Instrument((Holder<SoundEvent>)soundEvent, useDuration, range);
        }

        @Override
        public void writeDirect(ByteBuf buffer, Instrument value) {
            Types.SOUND_EVENT.write(buffer, value.soundEvent());
            Types.VAR_INT.writePrimitive(buffer, value.useDuration());
            buffer.writeFloat(value.range());
        }
    };

    public Instrument(Holder<SoundEvent> soundEvent, int useDuration, float range) {
        this.soundEvent = soundEvent;
        this.useDuration = useDuration;
        this.range = range;
    }

    public Instrument rewrite(Int2IntFunction soundIdRewriteFunction) {
        Holder<SoundEvent> soundEvent = this.soundEvent.updateId(soundIdRewriteFunction);
        return soundEvent == this.soundEvent ? this : new Instrument(soundEvent, this.useDuration, this.range);
    }

    public Holder<SoundEvent> soundEvent() {
        return this.soundEvent;
    }

    public int useDuration() {
        return this.useDuration;
    }

    public float range() {
        return this.range;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Instrument)) {
            return false;
        }
        Instrument instrument = (Instrument)object;
        return Objects.equals(this.soundEvent, instrument.soundEvent) && this.useDuration == instrument.useDuration && Float.compare(this.range, instrument.range) == 0;
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.soundEvent)) * 31 + Integer.hashCode(this.useDuration)) * 31 + Float.hashCode(this.range);
    }

    public String toString() {
        return String.format("%s[soundEvent=%s, useDuration=%s, range=%s]", this.getClass().getSimpleName(), Objects.toString(this.soundEvent), Integer.toString(this.useDuration), Float.toString(this.range));
    }
}

