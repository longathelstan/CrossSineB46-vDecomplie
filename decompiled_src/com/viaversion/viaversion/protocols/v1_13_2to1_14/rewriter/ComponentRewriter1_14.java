/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter.ComponentRewriter1_13;

public class ComponentRewriter1_14<C extends ClientboundPacketType>
extends ComponentRewriter1_13<C> {
    public ComponentRewriter1_14(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
    }

    @Override
    protected void handleTranslate(JsonObject object, String translate) {
    }
}

