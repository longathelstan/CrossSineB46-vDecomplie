/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;

public final class MapColorRewriter {
    public static PacketHandler getRewriteHandler(IdRewriteFunction rewriter) {
        return wrapper -> {
            int iconCount = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < iconCount; ++i) {
                wrapper.passthrough(Types.VAR_INT);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.OPTIONAL_COMPONENT);
            }
            short columns = wrapper.passthrough(Types.UNSIGNED_BYTE);
            if (columns < 1) {
                return;
            }
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            byte[] data = wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
            for (int i = 0; i < data.length; ++i) {
                int color = data[i] & 0xFF;
                int mappedColor = rewriter.rewrite(color);
                if (mappedColor == -1) continue;
                data[i] = (byte)mappedColor;
            }
        };
    }
}

