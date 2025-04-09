/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="TrueSight", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/TrueSight;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "barriersValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getBarriersValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "entitiesValue", "getEntitiesValue", "CrossSine"})
public final class TrueSight
extends Module {
    @NotNull
    private final BoolValue barriersValue = new BoolValue("Barriers", true);
    @NotNull
    private final BoolValue entitiesValue = new BoolValue("Entities", true);

    @NotNull
    public final BoolValue getBarriersValue() {
        return this.barriersValue;
    }

    @NotNull
    public final BoolValue getEntitiesValue() {
        return this.entitiesValue;
    }
}

