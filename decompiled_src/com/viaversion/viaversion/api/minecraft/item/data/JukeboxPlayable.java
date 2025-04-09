/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.util.Either;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class JukeboxPlayable {
    final Either<Holder<JukeboxSong>, String> song;
    final boolean showInTooltip;
    public static final Type<JukeboxPlayable> TYPE = new Type<JukeboxPlayable>(JukeboxPlayable.class){

        @Override
        public JukeboxPlayable read(ByteBuf buffer) {
            Either<JukeboxSong, String> position = Type.readEither(buffer, JukeboxSong.TYPE, Types.STRING);
            boolean showInTooltip = buffer.readBoolean();
            return new JukeboxPlayable(position, showInTooltip);
        }

        @Override
        public void write(ByteBuf buffer, JukeboxPlayable value) {
            Type.writeEither(buffer, value.song, JukeboxSong.TYPE, Types.STRING);
            buffer.writeBoolean(value.showInTooltip);
        }
    };

    public JukeboxPlayable(Holder<JukeboxSong> song, boolean showInTooltip) {
        this(Either.left(song), showInTooltip);
    }

    public JukeboxPlayable(String resourceKey, boolean showInTooltip) {
        this(Either.right(resourceKey), showInTooltip);
    }

    public JukeboxPlayable(Either<Holder<JukeboxSong>, String> song, boolean showInTooltip) {
        this.song = song;
        this.showInTooltip = showInTooltip;
    }

    public JukeboxPlayable rewrite(Int2IntFunction soundIdRewriteFunction) {
        if (this.song.isRight()) {
            return this;
        }
        Holder<JukeboxSong> songHolder = this.song.left();
        if (songHolder.hasId()) {
            return this;
        }
        JukeboxSong rewrittenSong = songHolder.value().rewrite(soundIdRewriteFunction);
        return rewrittenSong == songHolder.value() ? this : new JukeboxPlayable(Holder.of(rewrittenSong), this.showInTooltip);
    }

    public Either<Holder<JukeboxSong>, String> song() {
        return this.song;
    }

    public boolean showInTooltip() {
        return this.showInTooltip;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JukeboxPlayable)) {
            return false;
        }
        JukeboxPlayable jukeboxPlayable = (JukeboxPlayable)object;
        return Objects.equals(this.song, jukeboxPlayable.song) && this.showInTooltip == jukeboxPlayable.showInTooltip;
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.song)) * 31 + Boolean.hashCode(this.showInTooltip);
    }

    public String toString() {
        return String.format("%s[song=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.song), Boolean.toString(this.showInTooltip));
    }

    public static final class JukeboxSong {
        final Holder<SoundEvent> soundEvent;
        final Tag description;
        final float lengthInSeconds;
        final int comparatorOutput;
        public static final HolderType<JukeboxSong> TYPE = new HolderType<JukeboxSong>(){

            @Override
            public JukeboxSong readDirect(ByteBuf buffer) {
                Object soundEvent = Types.SOUND_EVENT.read(buffer);
                Tag description = (Tag)Types.TAG.read(buffer);
                float lengthInSeconds = buffer.readFloat();
                int useDuration = Types.VAR_INT.readPrimitive(buffer);
                return new JukeboxSong((Holder<SoundEvent>)soundEvent, description, lengthInSeconds, useDuration);
            }

            @Override
            public void writeDirect(ByteBuf buffer, JukeboxSong value) {
                Types.SOUND_EVENT.write(buffer, value.soundEvent);
                Types.TAG.write(buffer, value.description);
                buffer.writeFloat(value.lengthInSeconds);
                Types.VAR_INT.writePrimitive(buffer, value.comparatorOutput);
            }
        };

        public JukeboxSong(Holder<SoundEvent> soundEvent, Tag description, float lengthInSeconds, int comparatorOutput) {
            this.soundEvent = soundEvent;
            this.description = description;
            this.lengthInSeconds = lengthInSeconds;
            this.comparatorOutput = comparatorOutput;
        }

        public JukeboxSong rewrite(Int2IntFunction soundIdRewriteFunction) {
            Holder<SoundEvent> soundEvent = this.soundEvent.updateId(soundIdRewriteFunction);
            return soundEvent == this.soundEvent ? this : new JukeboxSong(soundEvent, this.description, this.lengthInSeconds, this.comparatorOutput);
        }

        public Holder<SoundEvent> soundEvent() {
            return this.soundEvent;
        }

        public Tag description() {
            return this.description;
        }

        public float lengthInSeconds() {
            return this.lengthInSeconds;
        }

        public int comparatorOutput() {
            return this.comparatorOutput;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof JukeboxSong)) {
                return false;
            }
            JukeboxSong jukeboxSong = (JukeboxSong)object;
            return Objects.equals(this.soundEvent, jukeboxSong.soundEvent) && Objects.equals(this.description, jukeboxSong.description) && Float.compare(this.lengthInSeconds, jukeboxSong.lengthInSeconds) == 0 && this.comparatorOutput == jukeboxSong.comparatorOutput;
        }

        public int hashCode() {
            return (((0 * 31 + Objects.hashCode(this.soundEvent)) * 31 + Objects.hashCode(this.description)) * 31 + Float.hashCode(this.lengthInSeconds)) * 31 + Integer.hashCode(this.comparatorOutput);
        }

        public String toString() {
            return String.format("%s[soundEvent=%s, description=%s, lengthInSeconds=%s, comparatorOutput=%s]", this.getClass().getSimpleName(), Objects.toString(this.soundEvent), Objects.toString(this.description), Float.toString(this.lengthInSeconds), Integer.toString(this.comparatorOutput));
        }
    }
}

