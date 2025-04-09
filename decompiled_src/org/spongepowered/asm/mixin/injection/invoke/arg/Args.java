/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.invoke.arg;

public abstract class Args {
    protected final Object[] values;

    protected Args(Object[] values2) {
        this.values = values2;
    }

    public int size() {
        return this.values.length;
    }

    public <T> T get(int index2) {
        return (T)this.values[index2];
    }

    public abstract <T> void set(int var1, T var2);

    public abstract void setAll(Object ... var1);
}

