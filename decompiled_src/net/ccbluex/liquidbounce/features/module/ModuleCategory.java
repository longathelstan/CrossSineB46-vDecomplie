/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "", "(Ljava/lang/String;I)V", "CLIENT", "COMBAT", "PLAYER", "MOVEMENT", "VISUAL", "WORLD", "CONFIG", "INFO", "CrossSine"})
public final class ModuleCategory
extends Enum<ModuleCategory> {
    public static final /* enum */ ModuleCategory CLIENT = new ModuleCategory();
    public static final /* enum */ ModuleCategory COMBAT = new ModuleCategory();
    public static final /* enum */ ModuleCategory PLAYER = new ModuleCategory();
    public static final /* enum */ ModuleCategory MOVEMENT = new ModuleCategory();
    public static final /* enum */ ModuleCategory VISUAL = new ModuleCategory();
    public static final /* enum */ ModuleCategory WORLD = new ModuleCategory();
    public static final /* enum */ ModuleCategory CONFIG = new ModuleCategory();
    public static final /* enum */ ModuleCategory INFO = new ModuleCategory();
    private static final /* synthetic */ ModuleCategory[] $VALUES;

    public static ModuleCategory[] values() {
        return (ModuleCategory[])$VALUES.clone();
    }

    public static ModuleCategory valueOf(String value) {
        return Enum.valueOf(ModuleCategory.class, value);
    }

    static {
        $VALUES = moduleCategoryArray = new ModuleCategory[]{ModuleCategory.CLIENT, ModuleCategory.COMBAT, ModuleCategory.PLAYER, ModuleCategory.MOVEMENT, ModuleCategory.VISUAL, ModuleCategory.WORLD, ModuleCategory.CONFIG, ModuleCategory.INFO};
    }
}

