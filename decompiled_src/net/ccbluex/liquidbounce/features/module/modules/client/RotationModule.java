/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Rotation", category=ModuleCategory.CLIENT, loadConfig=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/RotationModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "smoothValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getSmoothValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "CrossSine"})
public final class RotationModule
extends Module {
    @NotNull
    public static final RotationModule INSTANCE = new RotationModule();
    @NotNull
    private static final BoolValue smoothValue = new BoolValue("Smooth", true);

    private RotationModule() {
    }

    @NotNull
    public final BoolValue getSmoothValue() {
        return smoothValue;
    }
}

