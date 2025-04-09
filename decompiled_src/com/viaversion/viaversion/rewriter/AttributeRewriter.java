/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.Types;

public class AttributeRewriter<C extends ClientboundPacketType> {
    private final Protocol<C, ?, ?, ?> protocol;

    public AttributeRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public void register1_21(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size;
            wrapper.passthrough(Types.VAR_INT);
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                int j;
                int modifierSize;
                int attributeId = wrapper.read(Types.VAR_INT);
                int mappedId = this.protocol.getMappingData().getNewAttributeId(attributeId);
                if (mappedId == -1) {
                    --newSize;
                    wrapper.read(Types.DOUBLE);
                    modifierSize = wrapper.read(Types.VAR_INT);
                    for (j = 0; j < modifierSize; ++j) {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.DOUBLE);
                        wrapper.read(Types.BYTE);
                    }
                    continue;
                }
                wrapper.write(Types.VAR_INT, mappedId);
                wrapper.passthrough(Types.DOUBLE);
                modifierSize = wrapper.passthrough(Types.VAR_INT);
                for (j = 0; j < modifierSize; ++j) {
                    wrapper.passthrough(Types.STRING);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.BYTE);
                }
            }
            if (size != newSize) {
                wrapper.set(Types.VAR_INT, 1, newSize);
            }
        });
    }
}

