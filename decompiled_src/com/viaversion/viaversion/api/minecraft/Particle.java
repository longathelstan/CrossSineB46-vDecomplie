/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdHolder;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public final class Particle
implements IdHolder {
    final List<ParticleData<?>> arguments = new ArrayList(4);
    int id;

    public Particle(int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public <T> ParticleData<T> getArgument(int index2) {
        return this.arguments.get(index2);
    }

    public <T> ParticleData<T> removeArgument(int index2) {
        return this.arguments.remove(index2);
    }

    public List<ParticleData<?>> getArguments() {
        return this.arguments;
    }

    public <T> void add(Type<T> type, T value) {
        this.arguments.add(new ParticleData<T>(type, value));
    }

    public <T> void add(int index2, Type<T> type, T value) {
        this.arguments.add(index2, new ParticleData<T>(type, value));
    }

    public <T> void set(int index2, Type<T> type, T value) {
        this.arguments.set(index2, new ParticleData<T>(type, value));
    }

    public Particle copy() {
        Particle particle = new Particle(this.id);
        for (ParticleData<?> argument : this.arguments) {
            particle.arguments.add(argument.copy());
        }
        return particle;
    }

    public String toString() {
        int n = this.id;
        List<ParticleData<?>> list = this.arguments;
        return "Particle{arguments=" + list + ", id=" + n + "}";
    }

    public static final class ParticleData<T> {
        final Type<T> type;
        T value;

        public ParticleData(Type<T> type, T value) {
            this.type = type;
            this.value = value;
        }

        public Type<T> getType() {
            return this.type;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void write(ByteBuf buf) {
            this.type.write(buf, this.value);
        }

        public void write(PacketWrapper wrapper) {
            wrapper.write(this.type, this.value);
        }

        public ParticleData<T> copy() {
            T t = this.value;
            if (t instanceof Item) {
                Item item = (Item)t;
                return new ParticleData<Item>(this.type, item.copy());
            }
            return new ParticleData<T>(this.type, this.value);
        }

        public String toString() {
            T t = this.value;
            Type<T> type = this.type;
            return "ParticleData{type=" + type + ", value=" + t + "}";
        }
    }
}

