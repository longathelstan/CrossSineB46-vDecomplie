/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/EnumAutoDisableType;", "", "(Ljava/lang/String;I)V", "NONE", "RESPAWN", "FLAG", "GAME_END", "CrossSine"})
public final class EnumAutoDisableType
extends Enum<EnumAutoDisableType> {
    public static final /* enum */ EnumAutoDisableType NONE = new EnumAutoDisableType();
    public static final /* enum */ EnumAutoDisableType RESPAWN = new EnumAutoDisableType();
    public static final /* enum */ EnumAutoDisableType FLAG = new EnumAutoDisableType();
    public static final /* enum */ EnumAutoDisableType GAME_END = new EnumAutoDisableType();
    private static final /* synthetic */ EnumAutoDisableType[] $VALUES;

    public static EnumAutoDisableType[] values() {
        return (EnumAutoDisableType[])$VALUES.clone();
    }

    public static EnumAutoDisableType valueOf(String value) {
        return Enum.valueOf(EnumAutoDisableType.class, value);
    }

    static {
        $VALUES = enumAutoDisableTypeArray = new EnumAutoDisableType[]{EnumAutoDisableType.NONE, EnumAutoDisableType.RESPAWN, EnumAutoDisableType.FLAG, EnumAutoDisableType.GAME_END};
    }
}

