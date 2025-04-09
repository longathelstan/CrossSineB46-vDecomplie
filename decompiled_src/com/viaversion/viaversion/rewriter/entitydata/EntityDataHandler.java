/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;

@FunctionalInterface
public interface EntityDataHandler {
    public void handle(EntityDataHandlerEvent var1, EntityData var2);
}

