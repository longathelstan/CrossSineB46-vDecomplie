/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/special/TYPE;", "", "(Ljava/lang/String;I)V", "SUCCESS", "INFO", "ERROR", "WARNING", "CrossSine"})
public final class TYPE
extends Enum<TYPE> {
    public static final /* enum */ TYPE SUCCESS = new TYPE();
    public static final /* enum */ TYPE INFO = new TYPE();
    public static final /* enum */ TYPE ERROR = new TYPE();
    public static final /* enum */ TYPE WARNING = new TYPE();
    private static final /* synthetic */ TYPE[] $VALUES;

    public static TYPE[] values() {
        return (TYPE[])$VALUES.clone();
    }

    public static TYPE valueOf(String value) {
        return Enum.valueOf(TYPE.class, value);
    }

    static {
        $VALUES = tYPEArray = new TYPE[]{TYPE.SUCCESS, TYPE.INFO, TYPE.ERROR, TYPE.WARNING};
    }
}

