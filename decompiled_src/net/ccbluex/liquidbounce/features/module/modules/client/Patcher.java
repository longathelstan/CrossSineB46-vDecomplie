/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Patcher", category=ModuleCategory.CLIENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u0004H\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/Patcher;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canHanlding", "", "guiClient", "Lnet/ccbluex/liquidbounce/features/value/Value;", "hitDelayFix", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getHitDelayFix", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "keyBindHandling", "noJumpDelay", "getClientScreen", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class Patcher
extends Module {
    @NotNull
    private final BoolValue hitDelayFix = new BoolValue("HitDelayFix", true);
    @NotNull
    private final BoolValue keyBindHandling = new BoolValue("SmartKeyBindHandling", true);
    @NotNull
    private final Value<Boolean> guiClient = new BoolValue("AllowedGuiClient", true).displayable(new Function0<Boolean>(this){
        final /* synthetic */ Patcher this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)Patcher.access$getKeyBindHandling$p(this.this$0).get();
        }
    });
    @NotNull
    private final BoolValue noJumpDelay = new BoolValue("NoJumpDelay", true);
    private boolean canHanlding;

    @NotNull
    public final BoolValue getHitDelayFix() {
        return this.hitDelayFix;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.guiClient.get().booleanValue() && this.getClientScreen()) {
            this.canHanlding = true;
        }
        if (MinecraftInstance.mc.field_71462_r != null) {
            this.canHanlding = true;
        }
        if (((Boolean)this.noJumpDelay.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70773_bE = 0;
        }
        if (((Boolean)this.keyBindHandling.get()).booleanValue() && this.canHanlding && (this.guiClient.get().booleanValue() && this.getClientScreen() || MinecraftInstance.mc.field_71462_r == null)) {
            MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74351_w);
            MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74370_x);
            MinecraftInstance.mc.field_71474_y.field_74368_y.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74368_y);
            MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74366_z);
            MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A);
            this.canHanlding = false;
        }
    }

    private final boolean getClientScreen() {
        return MinecraftInstance.mc.field_71462_r instanceof ClickGui;
    }

    public static final /* synthetic */ BoolValue access$getKeyBindHandling$p(Patcher $this) {
        return $this.keyBindHandling;
    }
}

