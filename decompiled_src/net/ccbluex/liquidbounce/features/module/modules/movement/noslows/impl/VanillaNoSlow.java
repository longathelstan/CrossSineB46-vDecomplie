/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.noslows.impl;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.noslows.NoSlowMode;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0005"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/impl/VanillaNoSlow;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "()V", "slow", "", "CrossSine"})
public final class VanillaNoSlow
extends NoSlowMode {
    public VanillaNoSlow() {
        super("Vanilla");
    }

    @Override
    public float slow() {
        return 1.0f;
    }
}

