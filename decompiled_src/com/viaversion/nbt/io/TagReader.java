/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.io;

import com.viaversion.nbt.io.NBTIO;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.fastutil.io.FastBufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import org.jetbrains.annotations.Nullable;

public final class TagReader<T extends Tag> {
    private final Class<T> expectedTagType;
    private TagLimiter tagLimiter = TagLimiter.noop();
    private boolean named;

    TagReader(@Nullable Class<T> expectedTagType) {
        this.expectedTagType = expectedTagType;
    }

    public TagReader<T> tagLimiter(TagLimiter tagLimiter) {
        this.tagLimiter = tagLimiter;
        return this;
    }

    public TagReader<T> named() {
        this.named = true;
        return this;
    }

    public T read(DataInput in) throws IOException {
        this.tagLimiter.reset();
        return NBTIO.readTag(in, this.tagLimiter, this.named, this.expectedTagType);
    }

    public T read(InputStream in) throws IOException {
        DataInputStream dataInput = new DataInputStream(in);
        return this.read(dataInput);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public T read(Path path, boolean compressed) throws IOException {
        try (InputStream in = new FastBufferedInputStream(Files.newInputStream(path, new OpenOption[0]));){
            if (compressed) {
                in = new GZIPInputStream(in);
            }
            T t = this.read(in);
            return t;
        }
    }
}

