/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data;

public interface Mappings {
    public int getNewId(int var1);

    default public int getNewIdOrDefault(int id, int def) {
        int mappedId = this.getNewId(id);
        return mappedId != -1 ? mappedId : def;
    }

    default public boolean contains(int id) {
        return this.getNewId(id) != -1;
    }

    public void setNewId(int var1, int var2);

    public int size();

    public int mappedSize();

    public Mappings inverse();
}

