/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.DynamicType;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;

public class ParticleType
extends DynamicType<Particle> {
    public ParticleType() {
        super(Particle.class);
    }

    @Override
    public void write(ByteBuf buffer, Particle object) {
        Types.VAR_INT.writePrimitive(buffer, object.id());
        for (Particle.ParticleData<?> data : object.getArguments()) {
            data.write(buffer);
        }
    }

    @Override
    public Particle read(ByteBuf buffer) {
        int type = Types.VAR_INT.readPrimitive(buffer);
        Particle particle = new Particle(type);
        this.readData(buffer, particle);
        return particle;
    }

    @Override
    protected FullMappings mappings(Protocol<?, ?, ?, ?> protocol) {
        return protocol.getMappingData().getParticleMappings();
    }

    public static DynamicType.DataReader<Particle> itemHandler(Type<Item> itemType) {
        return (buf, particle) -> particle.add(itemType, (Item)itemType.read(buf));
    }

    public static final class Readers {
        public static final DynamicType.DataReader<Particle> BLOCK = (buf, particle) -> particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
        public static final DynamicType.DataReader<Particle> ITEM1_13 = ParticleType.itemHandler(Types.ITEM1_13);
        public static final DynamicType.DataReader<Particle> ITEM1_13_2 = ParticleType.itemHandler(Types.ITEM1_13_2);
        public static final DynamicType.DataReader<Particle> DUST = (buf, particle) -> {
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
        };
        public static final DynamicType.DataReader<Particle> DUST_TRANSITION = (buf, particle) -> {
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
        };
        public static final DynamicType.DataReader<Particle> VIBRATION = (buf, particle) -> {
            particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            String resourceLocation = (String)Types.STRING.read(buf);
            particle.add(Types.STRING, resourceLocation);
            resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
            if (resourceLocation.equals("block")) {
                particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
                particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
            } else {
                String string = resourceLocation;
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + string);
            }
            particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
        };
        public static final DynamicType.DataReader<Particle> VIBRATION1_19 = (buf, particle) -> {
            String resourceLocation = (String)Types.STRING.read(buf);
            particle.add(Types.STRING, resourceLocation);
            resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
            if (resourceLocation.equals("block")) {
                particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
                particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
                particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            } else {
                String string = resourceLocation;
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + string);
            }
            particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
        };
        public static final DynamicType.DataReader<Particle> VIBRATION1_20_3 = (buf, particle) -> {
            int sourceTypeId = Types.VAR_INT.readPrimitive(buf);
            particle.add(Types.VAR_INT, sourceTypeId);
            if (sourceTypeId == 0) {
                particle.add(Types.BLOCK_POSITION1_14, (BlockPosition)Types.BLOCK_POSITION1_14.read(buf));
            } else if (sourceTypeId == 1) {
                particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
                particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
            } else {
                int n = sourceTypeId;
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + n);
            }
            particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
        };
        public static final DynamicType.DataReader<Particle> SCULK_CHARGE = (buf, particle) -> particle.add(Types.FLOAT, Float.valueOf(Types.FLOAT.readPrimitive(buf)));
        public static final DynamicType.DataReader<Particle> SHRIEK = (buf, particle) -> particle.add(Types.VAR_INT, Types.VAR_INT.readPrimitive(buf));
        public static final DynamicType.DataReader<Particle> COLOR = (buf, particle) -> particle.add(Types.INT, buf.readInt());

        public static DynamicType.DataReader<Particle> item(Type<Item> item) {
            return (buf, particle) -> particle.add(item, (Item)item.read(buf));
        }
    }
}

