/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.AbstractStempConnectionHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;

public class MelonConnectionHandler
extends AbstractStempConnectionHandler {
    public MelonConnectionHandler(String baseStateId) {
        super(baseStateId);
    }

    static ConnectionData.ConnectorInitAction init() {
        return new MelonConnectionHandler("minecraft:melon_stem[age=7]").getInitAction("minecraft:melon", "minecraft:attached_melon_stem");
    }
}

