/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.snbt;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;

public interface ISNbtDeserializer<T extends Tag> {
    public T deserialize(String var1) throws SNbtDeserializeException;

    public Tag deserializeValue(String var1) throws SNbtDeserializeException;
}

