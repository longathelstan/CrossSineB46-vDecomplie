/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="KeepSprint", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/KeepSprint;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bothMotion", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "canKeepSprint", "", "getCanKeepSprint", "()Z", "forwardMotion", "onlyMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "strafeMotion", "tag", "", "getTag", "()Ljava/lang/String;", "getMotion", "", "()Ljava/lang/Float;", "CrossSine"})
public final class KeepSprint
extends Module {
    @NotNull
    public static final KeepSprint INSTANCE = new KeepSprint();
    @NotNull
    private static final FloatValue forwardMotion = new FloatValue("Forward", 1.0f, 0.0f, 1.0f);
    @NotNull
    private static final FloatValue strafeMotion = new FloatValue("Strafe", 1.0f, 0.0f, 1.0f);
    @NotNull
    private static final FloatValue bothMotion = new FloatValue("BothMotion", 1.0f, 0.0f, 1.0f);
    @NotNull
    private static final ListValue onlyMode;

    private KeepSprint() {
    }

    private final boolean getCanKeepSprint() {
        String string = ((String)onlyMode.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = string;
        return Intrinsics.areEqual(string2, "ground") ? MinecraftInstance.mc.field_71439_g.field_70122_E : (Intrinsics.areEqual(string2, "air") ? !MinecraftInstance.mc.field_71439_g.field_70122_E : true);
    }

    @Nullable
    public final Float getMotion() {
        return this.getCanKeepSprint() ? (!(MinecraftInstance.mc.field_71439_g.field_70701_bs == 0.0f) && MinecraftInstance.mc.field_71439_g.field_70702_br == 0.0f ? (Float)forwardMotion.get() : (!(MinecraftInstance.mc.field_71439_g.field_70702_br == 0.0f) && MinecraftInstance.mc.field_71439_g.field_70701_bs == 0.0f ? (Float)strafeMotion.get() : (!(MinecraftInstance.mc.field_71439_g.field_70701_bs == 0.0f) && !(MinecraftInstance.mc.field_71439_g.field_70702_br == 0.0f) ? (Float)bothMotion.get() : null))) : null;
    }

    @Override
    @NotNull
    public String getTag() {
        return !(MinecraftInstance.mc.field_71439_g.field_70701_bs == 0.0f) && MinecraftInstance.mc.field_71439_g.field_70702_br == 0.0f ? String.valueOf(((Number)forwardMotion.get()).floatValue()) : (!(MinecraftInstance.mc.field_71439_g.field_70702_br == 0.0f) && MinecraftInstance.mc.field_71439_g.field_70701_bs == 0.0f ? String.valueOf(((Number)strafeMotion.get()).floatValue()) : (!(MinecraftInstance.mc.field_71439_g.field_70701_bs == 0.0f) && !(MinecraftInstance.mc.field_71439_g.field_70702_br == 0.0f) ? String.valueOf(((Number)bothMotion.get()).floatValue()) : forwardMotion.get())).toString();
    }

    static {
        String[] stringArray = new String[]{"Ground", "Air", "Both"};
        onlyMode = new ListValue("Mode", stringArray, "Both");
    }
}

