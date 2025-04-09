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

@ModuleInfo(name="NoFOV", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/NoFov;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fov", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getFov", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "CrossSine"})
public final class NoFov
extends Module {
    @NotNull
    public static final NoFov INSTANCE = new NoFov();
    @NotNull
    private static final FloatValue fov = new FloatValue("FOV", 1.0f, 0.0f, 1.5f);

    private NoFov() {
    }

    @NotNull
    public final FloatValue getFov() {
        return fov;
    }
}

