/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.SimpleProtocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolPipeline
extends SimpleProtocol {
    public void add(Protocol var1);

    public void add(Collection<Protocol> var1);

    public boolean contains(Class<? extends Protocol> var1);

    @Deprecated
    public <P extends Protocol> @Nullable P getProtocol(Class<P> var1);

    public List<Protocol> pipes(@Nullable Class<? extends Protocol> var1, boolean var2, Direction var3);

    public List<Protocol> pipes();

    public List<Protocol> reversedPipes();

    public int baseProtocolCount();

    public boolean hasNonBaseProtocols();

    public void cleanPipes();
}

