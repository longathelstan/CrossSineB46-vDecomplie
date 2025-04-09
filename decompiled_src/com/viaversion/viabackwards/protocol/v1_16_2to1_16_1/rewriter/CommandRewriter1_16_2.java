/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter;

import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16_2
extends CommandRewriter<ClientboundPackets1_16_2> {
    public CommandRewriter1_16_2(Protocol1_16_2To1_16_1 protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:angle", wrapper -> wrapper.write(Types.VAR_INT, 0));
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:angle")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(argumentType);
    }
}

