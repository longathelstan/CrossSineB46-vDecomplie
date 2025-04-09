/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;

public final class SoundEventType
extends HolderType<SoundEvent> {
    @Override
    public SoundEvent readDirect(ByteBuf buffer) {
        String resourceLocation = (String)Types.STRING.read(buffer);
        Float fixedRange = (Float)Types.OPTIONAL_FLOAT.read(buffer);
        return new SoundEvent(resourceLocation, fixedRange);
    }

    @Override
    public void writeDirect(ByteBuf buffer, SoundEvent value) {
        Types.STRING.write(buffer, value.identifier());
        Types.OPTIONAL_FLOAT.write(buffer, value.fixedRange());
    }
}

