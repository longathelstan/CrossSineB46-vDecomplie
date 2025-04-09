/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;

public final class WorldIdentifiers
implements StorableObject {
    private final String overworld;
    private final String nether;
    private final String end;
    public static final String OVERWORLD_DEFAULT = "minecraft:overworld";
    public static final String NETHER_DEFAULT = "minecraft:the_nether";
    public static final String END_DEFAULT = "minecraft:the_end";

    public WorldIdentifiers(String overworld) {
        this(overworld, NETHER_DEFAULT, END_DEFAULT);
    }

    public WorldIdentifiers(String overworld, String nether, String end) {
        this.overworld = overworld;
        this.nether = nether;
        this.end = end;
    }

    public String overworld() {
        return this.overworld;
    }

    public String nether() {
        return this.nether;
    }

    public String end() {
        return this.end;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WorldIdentifiers)) {
            return false;
        }
        WorldIdentifiers worldIdentifiers = (WorldIdentifiers)object;
        return Objects.equals(this.overworld, worldIdentifiers.overworld) && Objects.equals(this.nether, worldIdentifiers.nether) && Objects.equals(this.end, worldIdentifiers.end);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.overworld)) * 31 + Objects.hashCode(this.nether)) * 31 + Objects.hashCode(this.end);
    }

    public String toString() {
        return String.format("%s[overworld=%s, nether=%s, end=%s]", this.getClass().getSimpleName(), Objects.toString(this.overworld), Objects.toString(this.nether), Objects.toString(this.end));
    }
}

