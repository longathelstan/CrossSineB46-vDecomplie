/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.rewriter.MappingDataListener;

public interface Rewriter<T extends Protocol<?, ?, ?, ?>>
extends MappingDataListener {
    public void register();

    public T protocol();
}

