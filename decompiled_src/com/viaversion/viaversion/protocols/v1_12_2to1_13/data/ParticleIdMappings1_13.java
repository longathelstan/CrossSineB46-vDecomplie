/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter.WorldPacketRewriter1_13;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ParticleIdMappings1_13 {
    static final List<NewParticle> particles = new ArrayList<NewParticle>();

    public static Particle rewriteParticle(int particleId, Integer[] data) {
        if (particleId >= particles.size()) {
            String string = Arrays.toString((Object[])data);
            int n = particleId;
            Protocol1_12_2To1_13.LOGGER.severe("Failed to transform particles with id " + n + " and data " + string);
            return null;
        }
        NewParticle rewrite = particles.get(particleId);
        return rewrite.handle(new Particle(rewrite.id()), data);
    }

    static void add(int newId) {
        particles.add(new NewParticle(newId, null));
    }

    static void add(int newId, ParticleDataHandler dataHandler) {
        particles.add(new NewParticle(newId, dataHandler));
    }

    static ParticleDataHandler reddustHandler() {
        return (particle, data) -> {
            particle.add(Types.FLOAT, Float.valueOf(ParticleIdMappings1_13.randomBool() ? 1.0f : 0.0f));
            particle.add(Types.FLOAT, Float.valueOf(0.0f));
            particle.add(Types.FLOAT, Float.valueOf(ParticleIdMappings1_13.randomBool() ? 1.0f : 0.0f));
            particle.add(Types.FLOAT, Float.valueOf(1.0f));
            return particle;
        };
    }

    static boolean randomBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    static ParticleDataHandler iconcrackHandler() {
        return (particle, data) -> {
            DataItem item;
            if (data.length == 1) {
                item = new DataItem(data[0], 1, 0, null);
            } else if (data.length == 2) {
                item = new DataItem(data[0], 1, data[1].shortValue(), null);
            } else {
                return particle;
            }
            Via.getManager().getProtocolManager().getProtocol(Protocol1_12_2To1_13.class).getItemRewriter().handleItemToClient(null, item);
            particle.add(Types.ITEM1_13, item);
            return particle;
        };
    }

    static ParticleDataHandler blockHandler() {
        return (particle, data) -> {
            int value = data[0];
            int combined = (value & 0xFFF) << 4 | value >> 12 & 0xF;
            int newId = WorldPacketRewriter1_13.toNewId(combined);
            particle.add(Types.VAR_INT, newId);
            return particle;
        };
    }

    static {
        ParticleIdMappings1_13.add(34);
        ParticleIdMappings1_13.add(19);
        ParticleIdMappings1_13.add(18);
        ParticleIdMappings1_13.add(21);
        ParticleIdMappings1_13.add(4);
        ParticleIdMappings1_13.add(43);
        ParticleIdMappings1_13.add(22);
        ParticleIdMappings1_13.add(42);
        ParticleIdMappings1_13.add(32);
        ParticleIdMappings1_13.add(6);
        ParticleIdMappings1_13.add(14);
        ParticleIdMappings1_13.add(37);
        ParticleIdMappings1_13.add(30);
        ParticleIdMappings1_13.add(12);
        ParticleIdMappings1_13.add(26);
        ParticleIdMappings1_13.add(17);
        ParticleIdMappings1_13.add(0);
        ParticleIdMappings1_13.add(44);
        ParticleIdMappings1_13.add(10);
        ParticleIdMappings1_13.add(9);
        ParticleIdMappings1_13.add(1);
        ParticleIdMappings1_13.add(24);
        ParticleIdMappings1_13.add(32);
        ParticleIdMappings1_13.add(33);
        ParticleIdMappings1_13.add(35);
        ParticleIdMappings1_13.add(15);
        ParticleIdMappings1_13.add(23);
        ParticleIdMappings1_13.add(31);
        ParticleIdMappings1_13.add(-1);
        ParticleIdMappings1_13.add(5);
        ParticleIdMappings1_13.add(11, ParticleIdMappings1_13.reddustHandler());
        ParticleIdMappings1_13.add(29);
        ParticleIdMappings1_13.add(34);
        ParticleIdMappings1_13.add(28);
        ParticleIdMappings1_13.add(25);
        ParticleIdMappings1_13.add(2);
        ParticleIdMappings1_13.add(27, ParticleIdMappings1_13.iconcrackHandler());
        ParticleIdMappings1_13.add(3, ParticleIdMappings1_13.blockHandler());
        ParticleIdMappings1_13.add(3, ParticleIdMappings1_13.blockHandler());
        ParticleIdMappings1_13.add(36);
        ParticleIdMappings1_13.add(-1);
        ParticleIdMappings1_13.add(13);
        ParticleIdMappings1_13.add(8);
        ParticleIdMappings1_13.add(16);
        ParticleIdMappings1_13.add(7);
        ParticleIdMappings1_13.add(40);
        ParticleIdMappings1_13.add(20, ParticleIdMappings1_13.blockHandler());
        ParticleIdMappings1_13.add(41);
        ParticleIdMappings1_13.add(38);
    }

    private static final class NewParticle {
        final int id;
        final @Nullable ParticleDataHandler handler;

        NewParticle(int id, @Nullable ParticleDataHandler handler) {
            this.id = id;
            this.handler = handler;
        }

        public Particle handle(Particle particle, Integer[] data) {
            if (this.handler != null) {
                return this.handler.handler(particle, data);
            }
            return particle;
        }

        public int id() {
            return this.id;
        }

        public @Nullable ParticleDataHandler handler() {
            return this.handler;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof NewParticle)) {
                return false;
            }
            NewParticle newParticle = (NewParticle)object;
            return this.id == newParticle.id && Objects.equals(this.handler, newParticle.handler);
        }

        public int hashCode() {
            return (0 * 31 + Integer.hashCode(this.id)) * 31 + Objects.hashCode(this.handler);
        }

        public String toString() {
            return String.format("%s[id=%s, handler=%s]", this.getClass().getSimpleName(), Integer.toString(this.id), Objects.toString(this.handler));
        }
    }

    @FunctionalInterface
    static interface ParticleDataHandler {
        public Particle handler(Particle var1, Integer[] var2);
    }
}

