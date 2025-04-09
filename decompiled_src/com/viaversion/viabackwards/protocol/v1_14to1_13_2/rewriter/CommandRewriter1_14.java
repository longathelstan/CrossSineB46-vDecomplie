/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_14
extends CommandRewriter<ClientboundPackets1_14> {
    public CommandRewriter1_14(Protocol1_14To1_13_2 protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:nbt_tag", wrapper -> wrapper.write(Types.VAR_INT, 2));
        this.parserHandlers.put("minecraft:time", wrapper -> {
            wrapper.write(Types.BYTE, (byte)1);
            wrapper.write(Types.INT, 0);
        });
    }

    @Override
    public @Nullable String handleArgumentType(String argumentType) {
        String string;
        switch (argumentType) {
            case "minecraft:nbt_compound_tag": {
                string = "minecraft:nbt";
                break;
            }
            case "minecraft:nbt_tag": {
                string = "brigadier:string";
                break;
            }
            case "minecraft:time": {
                string = "brigadier:integer";
                break;
            }
            default: {
                string = super.handleArgumentType(argumentType);
            }
        }
        return string;
    }
}

