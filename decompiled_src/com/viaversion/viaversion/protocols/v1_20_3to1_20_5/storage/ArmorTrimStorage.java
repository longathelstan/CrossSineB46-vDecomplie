/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.util.KeyMappings;

public final class ArmorTrimStorage
implements StorableObject {
    private static final KeyMappings MATERIALS = new KeyMappings("amethyst", "copper", "diamond", "emerald", "gold", "iron", "lapis", "netherite", "quartz", "redstone");
    private static final KeyMappings PATTERNS = new KeyMappings("coast", "dune", "eye", "host", "raiser", "rib", "sentry", "shaper", "silence", "snout", "spire", "tide", "vex", "ward", "wayfinder", "wild");
    private KeyMappings trimPatterns = PATTERNS;
    private KeyMappings trimMaterials = MATERIALS;

    public KeyMappings trimPatterns() {
        return this.trimPatterns;
    }

    public KeyMappings trimMaterials() {
        return this.trimMaterials;
    }

    public void setTrimPatterns(KeyMappings trimPatterns) {
        this.trimPatterns = trimPatterns;
    }

    public void setTrimMaterials(KeyMappings trimMaterials) {
        this.trimMaterials = trimMaterials;
    }
}

