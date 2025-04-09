/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ParticleIdMappings1_12_2 {
    static final ParticleData[] particles;

    public static ParticleData getMapping(int id) {
        return particles[id];
    }

    static ParticleData rewrite(int replacementId) {
        return new ParticleData(replacementId);
    }

    static ParticleData rewrite(int replacementId, ParticleHandler handler) {
        return new ParticleData(replacementId, handler);
    }

    static {
        ParticleHandler blockHandler = new ParticleHandler(){

            @Override
            public int[] rewrite(Protocol1_13To1_12_2 protocol, PacketWrapper wrapper) {
                return this.rewrite(wrapper.read(Types.VAR_INT));
            }

            @Override
            public int[] rewrite(Protocol1_13To1_12_2 protocol, List<Particle.ParticleData<?>> data) {
                return this.rewrite((Integer)data.get(0).getValue());
            }

            int[] rewrite(int newType) {
                int blockType = Protocol1_13To1_12_2.MAPPINGS.getNewBlockStateId(newType);
                int type = blockType >> 4;
                int meta = blockType & 0xF;
                return new int[]{type + (meta << 12)};
            }

            @Override
            public boolean isBlockHandler() {
                return true;
            }
        };
        particles = new ParticleData[]{ParticleIdMappings1_12_2.rewrite(16), ParticleIdMappings1_12_2.rewrite(20), ParticleIdMappings1_12_2.rewrite(35), ParticleIdMappings1_12_2.rewrite(37, blockHandler), ParticleIdMappings1_12_2.rewrite(4), ParticleIdMappings1_12_2.rewrite(29), ParticleIdMappings1_12_2.rewrite(9), ParticleIdMappings1_12_2.rewrite(44), ParticleIdMappings1_12_2.rewrite(42), ParticleIdMappings1_12_2.rewrite(19), ParticleIdMappings1_12_2.rewrite(18), ParticleIdMappings1_12_2.rewrite(30, new ParticleHandler(){

            @Override
            public int[] rewrite(Protocol1_13To1_12_2 protocol, PacketWrapper wrapper) {
                float r = wrapper.read(Types.FLOAT).floatValue();
                float g = wrapper.read(Types.FLOAT).floatValue();
                float b = wrapper.read(Types.FLOAT).floatValue();
                float scale = wrapper.read(Types.FLOAT).floatValue();
                wrapper.set(Types.FLOAT, 3, Float.valueOf(r));
                wrapper.set(Types.FLOAT, 4, Float.valueOf(g));
                wrapper.set(Types.FLOAT, 5, Float.valueOf(b));
                wrapper.set(Types.FLOAT, 6, Float.valueOf(scale));
                wrapper.set(Types.INT, 1, 0);
                return null;
            }

            @Override
            public int[] rewrite(Protocol1_13To1_12_2 protocol, List<Particle.ParticleData<?>> data) {
                return null;
            }
        }), ParticleIdMappings1_12_2.rewrite(13), ParticleIdMappings1_12_2.rewrite(41), ParticleIdMappings1_12_2.rewrite(10), ParticleIdMappings1_12_2.rewrite(25), ParticleIdMappings1_12_2.rewrite(43), ParticleIdMappings1_12_2.rewrite(15), ParticleIdMappings1_12_2.rewrite(2), ParticleIdMappings1_12_2.rewrite(1), ParticleIdMappings1_12_2.rewrite(46, blockHandler), ParticleIdMappings1_12_2.rewrite(3), ParticleIdMappings1_12_2.rewrite(6), ParticleIdMappings1_12_2.rewrite(26), ParticleIdMappings1_12_2.rewrite(21), ParticleIdMappings1_12_2.rewrite(34), ParticleIdMappings1_12_2.rewrite(14), ParticleIdMappings1_12_2.rewrite(36, new ParticleHandler(){

            @Override
            public int[] rewrite(Protocol1_13To1_12_2 protocol, PacketWrapper wrapper) {
                return this.rewrite(protocol, wrapper.read(Types.ITEM1_13));
            }

            @Override
            public int[] rewrite(Protocol1_13To1_12_2 protocol, List<Particle.ParticleData<?>> data) {
                return this.rewrite(protocol, (Item)data.get(0).getValue());
            }

            int[] rewrite(Protocol1_13To1_12_2 protocol, Item newItem) {
                Item item = protocol.getItemRewriter().handleItemToClient(null, newItem);
                return new int[]{item.identifier(), item.data()};
            }
        }), ParticleIdMappings1_12_2.rewrite(33), ParticleIdMappings1_12_2.rewrite(31), ParticleIdMappings1_12_2.rewrite(12), ParticleIdMappings1_12_2.rewrite(27), ParticleIdMappings1_12_2.rewrite(22), ParticleIdMappings1_12_2.rewrite(23), ParticleIdMappings1_12_2.rewrite(0), ParticleIdMappings1_12_2.rewrite(24), ParticleIdMappings1_12_2.rewrite(39), ParticleIdMappings1_12_2.rewrite(11), ParticleIdMappings1_12_2.rewrite(48), ParticleIdMappings1_12_2.rewrite(12), ParticleIdMappings1_12_2.rewrite(45), ParticleIdMappings1_12_2.rewrite(47), ParticleIdMappings1_12_2.rewrite(7), ParticleIdMappings1_12_2.rewrite(5), ParticleIdMappings1_12_2.rewrite(17), ParticleIdMappings1_12_2.rewrite(4), ParticleIdMappings1_12_2.rewrite(4), ParticleIdMappings1_12_2.rewrite(4), ParticleIdMappings1_12_2.rewrite(18), ParticleIdMappings1_12_2.rewrite(18)};
    }

    public static final class ParticleData {
        final int historyId;
        final ParticleHandler handler;

        ParticleData(int historyId, ParticleHandler handler) {
            this.historyId = historyId;
            this.handler = handler;
        }

        ParticleData(int historyId) {
            this(historyId, null);
        }

        public int @Nullable [] rewriteData(Protocol1_13To1_12_2 protocol, PacketWrapper wrapper) {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol, wrapper);
        }

        public int @Nullable [] rewriteMeta(Protocol1_13To1_12_2 protocol, List<Particle.ParticleData<?>> data) {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol, data);
        }

        public int getHistoryId() {
            return this.historyId;
        }

        public ParticleHandler getHandler() {
            return this.handler;
        }
    }

    public static interface ParticleHandler {
        public int[] rewrite(Protocol1_13To1_12_2 var1, PacketWrapper var2);

        public int[] rewrite(Protocol1_13To1_12_2 var1, List<Particle.ParticleData<?>> var2);

        default public boolean isBlockHandler() {
            return false;
        }
    }
}

