/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16
extends CommandRewriter<ClientboundPackets1_16> {
    public CommandRewriter1_16(Protocol1_16To1_15_2 protocol) {
        super(protocol);
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:uuid")) {
            return "minecraft:game_profile";
        }
        return super.handleArgumentType(argumentType);
    }
}

