/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="HitBox", category=ModuleCategory.COMBAT)
public class HitBox
extends Module {
    public static FloatValue HitboxMax = new FloatValue("Max", 3.5f, 3.0f, 7.0f);
    public static FloatValue HitboxMin = new FloatValue("Min", 3.5f, 3.0f, 7.0f);

    public static double getSize() {
        double min = Math.min(((Float)HitboxMin.getValue()).floatValue(), ((Float)HitboxMax.getValue()).floatValue());
        double max = Math.max(((Float)HitboxMin.getValue()).floatValue(), ((Float)HitboxMax.getValue()).floatValue());
        return Math.random() * (max - min) + min;
    }

    @Override
    @Nullable
    public String getTag() {
        return HitboxMax.get() + "-" + HitboxMin.get();
    }
}

