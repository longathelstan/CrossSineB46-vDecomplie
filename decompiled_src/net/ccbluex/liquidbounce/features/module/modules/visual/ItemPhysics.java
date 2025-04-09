/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="ItemPhysics", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ItemPhysics;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rotationSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getRotationSpeed", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "CrossSine"})
public final class ItemPhysics
extends Module {
    @NotNull
    public static final ItemPhysics INSTANCE = new ItemPhysics();
    @NotNull
    private static final FloatValue rotationSpeed = new FloatValue("RotationSpeed", 1.0f, 0.01f, 3.0f);

    private ItemPhysics() {
    }

    @NotNull
    public final FloatValue getRotationSpeed() {
        return rotationSpeed;
    }

    @Override
    @Nullable
    public String getTag() {
        return String.valueOf(((Number)rotationSpeed.get()).floatValue());
    }
}

