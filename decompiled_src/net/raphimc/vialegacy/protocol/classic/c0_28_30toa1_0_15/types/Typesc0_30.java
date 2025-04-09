/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.Type;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.BlockPositionType;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.ByteArrayType;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.StringType;

public class Typesc0_30 {
    public static final Type<String> STRING = new StringType();
    public static final Type<byte[]> BYTE_ARRAY = new ByteArrayType();
    public static final Type<BlockPosition> BLOCK_POSITION = new BlockPositionType();
}

