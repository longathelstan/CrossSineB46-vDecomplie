/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.rewriter;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viarewind.api.data.RewindMappingDataLoader;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class VRBlockItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
extends LegacyBlockItemRewriter<C, S, T> {
    protected VRBlockItemRewriter(T protocol, String name) {
        super(protocol, name);
    }

    @Override
    protected JsonObject readMappingsFile(String name) {
        return RewindMappingDataLoader.INSTANCE.loadFromDataDir(name);
    }

    @Override
    public String nbtTagName() {
        String string = ((BackwardsProtocol)this.protocol).getClass().getSimpleName();
        return "VR|" + string;
    }
}

