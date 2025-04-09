/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lnet/ccbluex/liquidbounce/features/module/EnumTriggerType;", "", "(Ljava/lang/String;I)V", "TOGGLE", "PRESS", "CrossSine"})
public final class EnumTriggerType
extends Enum<EnumTriggerType> {
    public static final /* enum */ EnumTriggerType TOGGLE = new EnumTriggerType();
    public static final /* enum */ EnumTriggerType PRESS = new EnumTriggerType();
    private static final /* synthetic */ EnumTriggerType[] $VALUES;

    public static EnumTriggerType[] values() {
        return (EnumTriggerType[])$VALUES.clone();
    }

    public static EnumTriggerType valueOf(String value) {
        return Enum.valueOf(EnumTriggerType.class, value);
    }

    static {
        $VALUES = enumTriggerTypeArray = new EnumTriggerType[]{EnumTriggerType.TOGGLE, EnumTriggerType.PRESS};
    }
}

