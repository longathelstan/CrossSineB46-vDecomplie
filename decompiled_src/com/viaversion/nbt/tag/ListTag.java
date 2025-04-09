/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.tag;

import com.viaversion.nbt.io.TagRegistry;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import com.viaversion.nbt.tag.Tag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public final class ListTag<T extends Tag>
implements Tag,
Iterable<T> {
    public static final int ID = 9;
    private Class<T> type;
    private List<T> value;

    @Deprecated
    public ListTag() {
        this.value = new ArrayList<T>();
    }

    public ListTag(Class<T> type) {
        this.type = type;
        this.value = new ArrayList<T>();
    }

    public ListTag(List<T> value) {
        this.setValue(value);
    }

    public static ListTag<?> read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
        tagLimiter.checkLevel(nestingLevel);
        tagLimiter.countBytes(5);
        byte id = in.readByte();
        Class<? extends Tag> type = null;
        if (id != 0 && (type = TagRegistry.getClassFor(id)) == null) {
            throw new IOException("Unknown tag ID in ListTag: " + id);
        }
        return ListTag.read(in, id, type, tagLimiter, nestingLevel);
    }

    private static <T extends Tag> ListTag<T> read(DataInput in, int id, Class<T> type, TagLimiter tagLimiter, int nestingLevel) throws IOException {
        ListTag<Tag> listTag = new ListTag<Tag>(type);
        int count = in.readInt();
        int newNestingLevel = nestingLevel + 1;
        for (int index2 = 0; index2 < count; ++index2) {
            Tag tag;
            try {
                tag = TagRegistry.read(id, in, tagLimiter, newNestingLevel);
            }
            catch (IllegalArgumentException e) {
                throw new IOException("Failed to create tag.", e);
            }
            listTag.add(tag);
        }
        return listTag;
    }

    @Override
    public List<T> getValue() {
        return this.value;
    }

    @Override
    public String asRawString() {
        return this.value.toString();
    }

    public void setValue(List<T> value) {
        this.value = new ArrayList<T>(value);
        if (!value.isEmpty()) {
            if (this.type == null) {
                this.type = ((Tag)value.get(0)).getClass();
            }
            for (Tag t : value) {
                this.checkType(t);
            }
        }
    }

    @Nullable
    public Class<? extends Tag> getElementType() {
        return this.type;
    }

    public boolean add(T tag) throws IllegalArgumentException {
        this.checkAddedTag(tag);
        return this.value.add(tag);
    }

    private void checkAddedTag(T tag) {
        if (this.type == null) {
            this.type = tag.getClass();
        } else {
            this.checkType((Tag)tag);
        }
    }

    private void checkType(Tag tag) {
        if (tag.getClass() != this.type) {
            throw new IllegalArgumentException("Tag type " + tag.getClass().getSimpleName() + " differs from list type " + this.type.getSimpleName());
        }
    }

    public boolean remove(T tag) {
        return this.value.remove(tag);
    }

    public T get(int index2) {
        return (T)((Tag)this.value.get(index2));
    }

    public T set(int index2, T tag) {
        this.checkAddedTag(tag);
        return (T)((Tag)this.value.set(index2, tag));
    }

    public T remove(int index2) {
        return (T)((Tag)this.value.remove(index2));
    }

    public int size() {
        return this.value.size();
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public Stream<T> stream() {
        return this.value.stream();
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        if (this.value.isEmpty()) {
            out.writeByte(0);
        } else {
            int id = TagRegistry.getIdFor(this.type);
            if (id == -1) {
                throw new IOException("ListTag contains unregistered tag class.");
            }
            out.writeByte(id);
        }
        out.writeInt(this.value.size());
        for (Tag tag : this.value) {
            tag.write(out);
        }
    }

    @Override
    public ListTag<T> copy() {
        ListTag<Tag> copy = new ListTag<Tag>(this.type);
        copy.value = new ArrayList<T>(this.value.size());
        for (Tag value : this.value) {
            copy.add(value.copy());
        }
        return copy;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ListTag tags = (ListTag)o;
        if (!Objects.equals(this.type, tags.type)) {
            return false;
        }
        return this.value.equals(tags.value);
    }

    public int hashCode() {
        int result = this.type != null ? this.type.hashCode() : 0;
        result = 31 * result + this.value.hashCode();
        return result;
    }

    @Override
    public int getTagId() {
        return 9;
    }

    public String toString() {
        return SNBT.serialize(this);
    }
}

