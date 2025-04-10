/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_13;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.SNbtDeserializer_v1_12;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.StringReader_v1_12;

public class SNbtDeserializer_v1_13
extends SNbtDeserializer_v1_12 {
    @Override
    protected Tag readListOrArray(StringReader_v1_12 reader) throws SNbtDeserializeException {
        if (reader.canRead(3) && !this.isQuote(reader.charAt(1)) && reader.charAt(2) == ';') {
            return this.readArray(reader);
        }
        return this.readList(reader);
    }
}

