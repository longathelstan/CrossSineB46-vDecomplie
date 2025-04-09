/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_18_2to1_18.rewriter;

import com.viaversion.viabackwards.protocol.v1_18_2to1_18.Protocol1_18_2To1_18;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class CommandRewriter1_18_2
extends CommandRewriter<ClientboundPackets1_18> {
    public CommandRewriter1_18_2(Protocol1_18_2To1_18 protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:resource", wrapper -> {
            wrapper.read(Types.STRING);
            wrapper.write(Types.VAR_INT, 1);
        });
        this.parserHandlers.put("minecraft:resource_or_tag", wrapper -> {
            wrapper.read(Types.STRING);
            wrapper.write(Types.VAR_INT, 1);
        });
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:resource") || argumentType.equals("minecraft:resource_or_tag")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(argumentType);
    }
}

