/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.IStyleSerializer;

public interface ITextComponentSerializer<T>
extends ITypedSerializer<T, ATextComponent> {
    public IStyleSerializer<T> getStyleSerializer();
}

