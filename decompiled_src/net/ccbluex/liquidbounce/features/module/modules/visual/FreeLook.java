/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;

@ModuleInfo(name="FreeLook", category=ModuleCategory.VISUAL, triggerType=EnumTriggerType.PRESS)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\tH\u0016J\u0006\u0010\u000f\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/FreeLook;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "reverse", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getReverse", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "thirdPerson", "disable", "", "enable", "move", "", "onDisable", "onEnable", "setRotations", "Companion", "CrossSine"})
public final class FreeLook
extends Module {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BoolValue thirdPerson = new BoolValue("ThirdPerson", true);
    @NotNull
    private final BoolValue reverse = new BoolValue("Reverse", false);
    private static final Minecraft mc = MinecraftInstance.mc;
    @JvmField
    public static boolean isReverse;
    @JvmField
    public static boolean isEnabled;
    @JvmField
    public static boolean moveCorrect;
    @JvmField
    public static boolean perspectiveToggled;
    @JvmField
    public static float cameraYaw;
    @JvmField
    public static float cameraPitch;
    @JvmField
    public static float cameraYaw2;
    @JvmField
    public static float cameraPitch2;
    private static int previousPerspective;

    @NotNull
    public final BoolValue getReverse() {
        return this.reverse;
    }

    @Override
    public void onEnable() {
        if (!isEnabled) {
            this.setRotations();
            isEnabled = true;
        }
        cameraYaw2 = cameraYaw;
        cameraPitch2 = cameraPitch;
        isReverse = (Boolean)this.reverse.get();
        perspectiveToggled = true;
        previousPerspective = FreeLook.mc.field_71474_y.field_74320_O;
        if (((Boolean)this.thirdPerson.get()).booleanValue()) {
            FreeLook.mc.field_71474_y.field_74320_O = 1;
        }
    }

    @Override
    public void onDisable() {
        if (RotationUtils.freeLookRotation == null) {
            isEnabled = false;
            Companion.resetPerspective();
        } else {
            FreeLook.mc.field_71474_y.field_74320_O = previousPerspective;
            cameraYaw2 = cameraYaw;
            cameraPitch2 = cameraPitch;
        }
    }

    public final void setRotations() {
        cameraYaw = FreeLook.mc.field_71439_g.field_70177_z;
        cameraPitch = FreeLook.mc.field_71439_g.field_70125_A;
    }

    public final void enable(boolean move) {
        isEnabled = true;
        isReverse = false;
        perspectiveToggled = true;
        moveCorrect = move;
        this.setRotations();
        previousPerspective = FreeLook.mc.field_71474_y.field_74320_O;
    }

    public final void disable() {
        if (isEnabled) {
            isEnabled = false;
            perspectiveToggled = false;
            FreeLook.mc.field_71439_g.field_70177_z = cameraYaw;
            FreeLook.mc.field_71439_g.field_70125_A = cameraPitch;
        }
    }

    @JvmStatic
    public static final boolean overrideMouse() {
        return Companion.overrideMouse();
    }

    @JvmStatic
    public static final void resetPerspective() {
        Companion.resetPerspective();
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\tH\u0007J\b\u0010\u0013\u001a\u00020\u0014H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/FreeLook$Companion;", "", "()V", "cameraPitch", "", "cameraPitch2", "cameraYaw", "cameraYaw2", "isEnabled", "", "isReverse", "mc", "Lnet/minecraft/client/Minecraft;", "kotlin.jvm.PlatformType", "moveCorrect", "perspectiveToggled", "previousPerspective", "", "overrideMouse", "resetPerspective", "", "CrossSine"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        public final boolean overrideMouse() {
            block8: {
                float f4;
                float f3;
                block10: {
                    block9: {
                        if (!mc.field_71415_G || !Display.isActive()) break block8;
                        if (!perspectiveToggled) {
                            return true;
                        }
                        mc.field_71417_B.func_74374_c();
                        float f1 = mc.field_71474_y.field_74341_c * 0.6f + 0.2f;
                        float f2 = f1 * f1 * f1 * 8.0f;
                        f3 = (float)mc.field_71417_B.field_74377_a * f2;
                        f4 = (float)mc.field_71417_B.field_74375_b * f2;
                        if (RotationUtils.freeLookRotation == null) break block9;
                        FreeLook freeLook = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
                        Intrinsics.checkNotNull(freeLook);
                        if (freeLook.getState()) break block10;
                    }
                    cameraYaw += f3 * 0.15f;
                    if ((cameraPitch -= f4 * 0.15f) > 90.0f) {
                        cameraPitch = 90.0f;
                    }
                    if (cameraPitch < -90.0f) {
                        cameraPitch = -90.0f;
                    }
                }
                cameraYaw2 += f3 * 0.15f;
                if ((cameraPitch2 -= f4 * 0.15f) > 90.0f) {
                    cameraPitch2 = 90.0f;
                }
                if (cameraPitch2 < -90.0f) {
                    cameraPitch2 = -90.0f;
                }
            }
            return false;
        }

        @JvmStatic
        public final void resetPerspective() {
            perspectiveToggled = false;
            mc.field_71474_y.field_74320_O = previousPerspective;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

