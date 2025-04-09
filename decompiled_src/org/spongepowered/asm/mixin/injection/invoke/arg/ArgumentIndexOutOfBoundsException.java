/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentIndexOutOfBoundsException
extends IndexOutOfBoundsException {
    private static final long serialVersionUID = 1L;

    public ArgumentIndexOutOfBoundsException(int index2) {
        super("Argument index is out of bounds: " + index2);
    }
}

