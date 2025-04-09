/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.snbt.exceptions;

import com.viaversion.nbt.tag.Tag;

public class SNbtSerializeException
extends Exception {
    public SNbtSerializeException(Tag type) {
        super("Unable to serialize nbt type " + type.getClass().getSimpleName());
    }
}

