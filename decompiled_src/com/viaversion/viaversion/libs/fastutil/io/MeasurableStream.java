/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.io;

import java.io.IOException;

public interface MeasurableStream {
    public long length() throws IOException;

    public long position() throws IOException;
}

