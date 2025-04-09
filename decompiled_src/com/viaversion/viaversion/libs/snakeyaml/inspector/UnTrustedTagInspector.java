/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.inspector;

import com.viaversion.viaversion.libs.snakeyaml.inspector.TagInspector;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;

public final class UnTrustedTagInspector
implements TagInspector {
    @Override
    public boolean isGlobalTagAllowed(Tag tag) {
        return false;
    }
}

